package com.xiaodu.listener;


import lombok.extern.log4j.Log4j2;
import org.testng.*;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * TestListener
 */
@Log4j2
public class TestListener extends TestListenerAdapter implements IInvokedMethodListener, IAnnotationTransformer {
//    Logger logger = new Logger(MethodHandles.lookup().lookupClass().getName());
    private boolean hasFailures = false;


    @Override
    public void onTestStart(ITestResult iTestResult) {
        super.onTestStart(iTestResult);
        log.info(getTestCaseNameAndMothedName(iTestResult) + " start.");
//        Core core = (Core) iTestResult.getInstance();
//        core.getRetryOption().setMaxRetryCount(0);
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        log.info(getTestCaseNameAndMothedName(iTestResult) + " run completed");
        if (iTestResult.getMethod().getCurrentInvocationCount() == 1) {
            super.onTestSuccess(iTestResult);
            return;
        }
        processSkipResult(iTestResult);
//        Core core = (Core) iTestResult.getInstance();
//        core.getRetryOption().setCount(0);
//        iTestResult.getTestContext().setAttribute(FrameWorkConStants.IGNORE_ERROR, "false");


        super.onTestSuccess(iTestResult);

    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        super.onTestSkipped(iTestResult);
        log.info(getTestCaseNameAndMothedName(iTestResult) + " skipped.");
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        log.info(getTestCaseNameAndMothedName(iTestResult) + " run failed");

        log.warn("@onTestFailure instanceof=======TestException");
        ITestContext iTestContext = iTestResult.getTestContext();
//        if (TestNGHelper.isWebTest()) {
//            try {
//                logger.warn("Try to make screenshot for failed runShellRetry steps");
//                WebDriver driver = Browser.getWebDriver();
//                if (driver == null) {
//                    logger.error("No screenshot made, since driver is null");
//                } else {
//                    ScreenShot.getInstance().getScreenShot("Make screenshot successfully: ");
//                }
//            } catch (Exception e) {
//                logger.error(iTestResult.getName() + " Not able to get screenshot for failed steps, please observe the failure stack" + e.toString());
//            }
//        }

//        Core core = (Core) iTestResult.getInstance();


        if (iTestResult.getMethod().getCurrentInvocationCount() == 1) {
            super.onTestFailure(iTestResult);
            return;
        }


        processSkipResult(iTestResult);
//        Core core = (Core) iTestResult.getInstance();
//        core.getRetryOption().setCount(0);
//        iTestResult.getTestContext().setAttribute(FrameWorkConStants.IGNORE_ERROR, "false");

        super.onTestFailure(iTestResult);
    }

    private String getTestCaseNameAndMothedName(ITestResult iTestResult) {
        //iTestResult.getTestContext().getName() :场景名
        //iTestResult.getName()  :方法名
        return "runShellRetry case " + iTestResult.getTestContext().getName() + ":" + iTestResult.getName();
    }


    HashMap<String, Boolean> map = new HashMap<>();

    @Override
    public void afterInvocation(IInvokedMethod invokedMethod, ITestResult testResult) {
        if (invokedMethod.isTestMethod()) {
//            if (TestNG.ContainsUnwantedSkip.get()) {
//                testResult.setStatus(ITestResult.SKIP);
//            }
//            if (TestNG.ContainsCheckPointError.get()) {
//                testResult.setStatus(ITestResult.FAILURE);
//            }
        }

        if (!invokedMethod.isTestMethod()) {
            return;
        }


        //存在依赖关系的方法，不走下面的方法
        String[] methodsDependedUpon = invokedMethod.getTestMethod().getMethodsDependedUpon();
        if (methodsDependedUpon.length > 0) {
            return;
        }


//        Core core = (Core) testResult.getInstance();
//
//        //step失败，下边所有方法都失败。
//        //check失败，可以忽略。
//        if (!testResult.isSuccess() && !testResult.getName().startsWith("check")    //check点忽略
//                && core.getRetryOption().getMaxRetryCount() == core.getRetryOption().getCount() //兼容retry
//        ) {
//            hasFailures = true;
//        }
//
//        //统计不是check点的方法状态，用于排除check报错引起的方法失败。
//        if (!testResult.getName().startsWith("check")) {
//            map.put(testResult.getTestContext().getName(), testResult.isSuccess());
//        }


    }

    @Override
    public void beforeInvocation(IInvokedMethod invokedMethod, ITestResult testResult) {
//        if (invokedMethod.isTestMethod()) {
//            TestNG.ContainsCheckPointError.set(false);
//            TestNG.ContainsUnwantedSkip.set(false);
//        }

        if (!invokedMethod.isTestMethod()) {
            return;
        }

//            ITestNGMethod testNgMethod = testResult.getMethod();
//    ConstructorOrMethod contructorOrMethod = testNgMethod.getConstructorOrMethod();
//    Method method = contructorOrMethod.getMethod();
//        if (method == null || !method.isAnnotationPresent(Assumption.class)) {
//        return;
//    }

        //存在依赖关系的方法，不走下面的方法
        String[] methodsDependedUpon = invokedMethod.getTestMethod().getMethodsDependedUpon();
        if (methodsDependedUpon.length > 0) {
            return;
        }


        Boolean aBoolean = map.get(testResult.getTestContext().getName());
        if (hasFailures && aBoolean != null && !aBoolean) {
            throw new SkipException(" skip");
        }


    }


//    ITestNGMethod testNgMethod = testResult.getMethod();
//    ConstructorOrMethod contructorOrMethod = testNgMethod.getConstructorOrMethod();
//    Method method = contructorOrMethod.getMethod();
//        if (method == null || !method.isAnnotationPresent(Assumption.class)) {
//        return;
//    }
//
//    List<String> failedAssumptions = checkAssumptions(method, testResult);
//        if (!failedAssumptions.isEmpty()) {
//        throw new SkipException(String.format("Skipping [%s] because the %s assumption(s) do not hold.", contructorOrMethod.getName(), failedAssumptions));
//    }



    private List<String> checkAssumptions(Method method, ITestResult result) {
//        Assumption annotation = method.getAnnotation(Assumption.class);
//        String[] assumptionMethods = annotation.methods();
//        List<String> failedAssumptions = new ArrayList<String>();
//        Class clazz = result.getMethod().getTestClass().getRealClass();
//        for (String assumptionMethod : assumptionMethods) {
//            boolean assume = checkAssumption(result, clazz, assumptionMethod);
//            if (!assume) {
//                failedAssumptions.add(assumptionMethod);
//            }
//        }

//        return failedAssumptions;
        return null;
    }

    private boolean checkAssumption(ITestResult result, Class clazz, String assumptionMethod) {
        try {
            Method assumption = clazz.getMethod(assumptionMethod);
            if (assumption.getReturnType() != boolean.class) {
                throw new RuntimeException(String.format("Assumption method [%s] should return a boolean", assumptionMethod));
            }
            return (Boolean) assumption.invoke(result.getInstance());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return false;
    }

    @Override
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
//        annotation.setRetryAnalyzer(RetryHolder.class);
    }

    @Override
    public void onFinish(ITestContext context) {
        Iterator<ITestResult> failedTestCases = context.getFailedTests().getAllResults().iterator();
        while (failedTestCases.hasNext()) {
            ITestResult failedTestCase = failedTestCases.next();
            ITestNGMethod method = failedTestCase.getMethod();
            if (context.getFailedTests().getResults(method).size() > 1) {
                log.info("failed runShellRetry case remove as dup:" + failedTestCase.getTestClass().toString());
                failedTestCases.remove();
            } else if (context.getSkippedTests().getResults(method).size() > 1) {
                log.info("skip runShellRetry case remove as dup:" + failedTestCase.getTestClass().toString());

            } else {
                if (context.getPassedTests().getResults(method).size() > 0) {
                    log.info("failed runShellRetry case remove as pass retry:" + failedTestCase.getTestClass().toString());
                    failedTestCases.remove();
                }
            }
        }
    }

    // Remove all the dup Skipped results
    private void processSkipResult(ITestResult iTestResult) {
        ITestContext iTestContext = iTestResult.getTestContext();
        Iterator<ITestResult> processResults = iTestContext.getSkippedTests().getAllResults().iterator();
        //忽略异常
//        String ignoreError = (String) iTestContext.getAttribute(FrameWorkConStants.IGNORE_ERROR);
//        if (null == ignoreError) {
//            ignoreError = "false";
//        }
//        while (processResults.hasNext()) {
//            ITestResult skippedTest = processResults.next();
//            if (skippedTest.getMethod().getMethodName().equalsIgnoreCase(iTestResult.getMethod().getMethodName()) &&
//                    ignoreError.equalsIgnoreCase("false")) {
//                processResults.remove();
//            }
//        }
    }


}

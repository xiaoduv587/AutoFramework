package com.xiaodu.retry;

import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.core.Core;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

@Log4j2
public class RetryHolder implements IRetryAnalyzer {

    private int count = 0;
    private int maxRetryCount = 0;


    public static final String RETRY = "RETRY";
    public static final String NO = "NO";
    public static final String YES = "YES";


//    @Override
//    public boolean retry(ITestResult iTestResult) {
//        String attribute = (String) iTestResult.getAttribute(RETRY);
//
//        Core core = (Core) iTestResult.getInstance();
//        maxRetryCount = core.getRetryOption().getMaxRetryCount();
//
//        log.info(String.format("@retry(ITestResult) %s [%d/%d]", iTestResult.getName(), count, maxRetryCount));
//        if (NO.equalsIgnoreCase(attribute)) {
//            return false;
//        } else if (YES.equalsIgnoreCase(attribute) && count < maxRetryCount) {
//            count++;
//            core.getRetryOption().setCount(count);
//            return true;
//        }
//        return false;
//    }


	@Override
	public boolean retry(ITestResult result) {

        boolean retry = false;

//		Core core = (Core) result.getInstance();
//		maxRetryCount = core.getRetryOption().getMaxRetryCount();
//
//		log.info(String.format("@retry(ITestResult) %s [%d/%d]", result.getName(), count, maxRetryCount));
//		if (count < maxRetryCount) {
//			count++;
//			core.getRetryOption().setCount(count);
//            retry = true;
//		}

		return retry;

	}

}

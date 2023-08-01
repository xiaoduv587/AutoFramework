import com.xiaodu.FrameWorkConfig;
import com.xiaodu.listener.TestListener;
import com.xiaodu.utils.FileHelper;
import lombok.extern.log4j.Log4j2;
import org.testng.ITestNGListener;
import org.testng.TestNG;
import org.testng.collections.Lists;
import org.testng.xml.Parser;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;


@Log4j2
@SuppressWarnings("unused")
public class RunTestNG {

    //    public static SDET_Dashboard SYNC_DASHBOARD = null;
    public static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss");
    public static Date START_TIME = new Date();
//    static Logger logger = new Logger(MethodHandles.lookup().lookupClass().getName());

    public static void main(String[] args) throws Exception {


        /**
         * Test preparation phase
         */
        // String userdir = System.getProperty("user.dir");
        delTestOutputFolder();
        // get input arguments
        Map<String, String> arguments = getInArguments(args);


        String test_job_runtime_info_id = "";

//        NOTE: Enable the below line in case of local debug
//        arguments = getLocalConfig(arguments);

        FrameWorkConfig.instance().put(arguments);
        FrameWorkConfig.instance().check();


//        Configurator.setRootLevel(Level.toLevel((String) FrameWorkConfig.instance().LOGLEVEL.getValue()));

//        log.info("Config.instance().JENKINS_BUILD_URL.getValue() = " + FrameWorkConfig.instance().JENKINS_BUILD_URL.getValue());
        String projectName = FrameWorkConfig.instance().PROJECT_NAME.getValue();



        // create TestNG object
        TestNG testNG = new TestNG();


//        FrameWorkConfig.instance().THREAD_COUNT.setValue(jobInfo.getParallelThread());

//            List<String> xmlFile;
//            xmlFile = TestNGHelper.generateLocalXml(arguments);
//            String testngXml = FrameWorkConfig.instance().TESTNG_XML.getValue();

//        Parser parser = new Parser(FileHelper.getResourcesFilePath(FrameWorkConfig.instance().TESTNG_XML.getValue().trim()));
        Parser parser = new Parser(FileHelper.getResourcesFilePath(""));
        XmlSuite xmlFilePath = new XmlSuite();
        List<XmlSuite> suites = null;
        try {
            suites = parser.parseToList();
        } catch (
                IOException e) {
            e.printStackTrace();
        }

        XmlSuite xmlsuite = suites.get(0);
        List<XmlTest> tests = xmlsuite.getTests();

        Map<String, String> parameters = xmlsuite.getParameters();
        parameters.putAll(arguments);
        parameters.put("useIncognito", "false");

        xmlsuite.setParameters(parameters);


        log.info(xmlsuite.toXml());

        printTestNgXML(xmlsuite);
        testNG.setXmlSuites(suites);


// specify the runShellRetry output files
//        testNG.setOutputDirectory(FileHelper.getTestngReportsRootDir());

        // force close all browser before execution starts
//        TestNGHelper.clearBrowserProcess(driverType);

        // compose runShellRetry listener
        List<Class<? extends ITestNGListener>> listenerClasses = Lists.newArrayList();
        listenerClasses.add(TestListener.class);
        testNG.setListenerClasses(listenerClasses);

        /**
         * Test preparation phase: start with the testNG execution processes: {beforeSuite ()} --> {parallel threads} --> {afterSuite()}
         */

        testNG.run();

        /**
         * Post execution phase
         */

        //If needed, force close all browsers in the end (NOTE: Set this to false in case of local debug)
//        log.info("TEST_END_CLOSE_BROWSER:" + FrameWorkConfig.instance().TEST_END_CLOSE_BROWSER.getValue());
//        if (FrameWorkConfig.instance().TEST_END_CLOSE_BROWSER.getValue()) {
//            TestNGHelper.clearBrowserProcess(driverType);
//        }

//        TestNGHelper.generateReport();

//        SVGHelper.generatePngOverviewChart();

//        OverView overView = new OverView();
//        overView.addDescriptionToOverViewPage(projectName);


//        //If needed, upload passrate to prepared sheet in FTP
//        if (FrameWorkConfig.instance().SAVE_PASSRATE_TO_FTP.getValue()) {
//            FTPHelper.updatePassRateToFTP();
//        }
//
//        //If needed, generate a sequenced scorecard sheet, and upload to FTP
//        log.info("SAVE_SCORECARD_SHEET_TO_FTP -->" +FrameWorkConfig.instance().SAVE_SCORECARD_SHEET_TO_FTP.getValue());
//        if (FrameWorkConfig.instance().SAVE_SCORECARD_SHEET_TO_FTP.getValue()) {
//            FTPHelper.uploadScorecardSheetToFTP();
//        }
//
//        //If needed, upload the whole runShellRetry-output folder to FTP
//        log.info("SAVE_REPORT_TO_FTP -->" + FrameWorkConfig.instance().SAVE_REPORT_TO_FTP.getValue());
//        if (FrameWorkConfig.instance().SAVE_REPORT_TO_FTP.getValue()) {
//            FTPHelper.uploadTestReportDataToFTP();
//        }
//        log.info("Over All Total Pass Rate: " + EmailBody.totalPassRate);

//        generateReport();

        System.exit(0);
    }


    private static void delTestOutputFolder() {
        log.info("***************************  del runShellRetry-output folder start ***************************");
        File file = new File(System.getProperty("user.dir") + File.separator + "runShellRetry-output");
        log.info("  runShellRetry-output is exists-->" + file.exists());
        if (file.exists()) {
            log.info(" runShellRetry-output paht -->" + file.getPath());
            FileHelper.delFolder(file.getPath());
        }
        log.info("***************************  del runShellRetry-output folder end  ***************************");
    }

    private static Map<String, String> getInArguments(String[] args) {
        Map<String, String> arguments = new HashMap<String, String>();
        for (int i = 0; i < args.length; i++) {
            String strTemp = args[ i ];
            String key = "";
            String value = "";
            try {
                key = strTemp.split("=")[ 0 ];
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                value = strTemp.split("=")[ 1 ];
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (!key.isEmpty()) {
                log.info("Get The InComing Arguments key=[" + key + "],value=[" + value + "]");
                arguments.put(key, value);
            }
        }
        return arguments;
    }

    /**
     * 打印XmlSuite
     *
     * @param suite XmlSuite
     */
    private static void printTestNgXML(XmlSuite suite) throws IOException {
        log.info("XmlSuite name:" + suite.getName());
        //FileUtils.writeByteArrayToFile(new File("testng.xml"), suite.toXml().toString().getBytes());
    }


}

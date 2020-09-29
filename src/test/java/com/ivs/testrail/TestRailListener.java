package com.ivs.testrail;

import org.testng.*;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.logging.Logger;


public class TestRailListener implements ITestListener, IConfigurationListener, ISuiteListener {

    private Logger logger = Logger.getLogger(TestRailListener.class.getName());
    private TestRailReporter reporter;
    List<Integer> testRailIdsList;
    Map<String, Object> props;
    List<Map<String, Object>> testResultsList;
    String platform;
    String language;
    String testRailEnabled;

    /**
     * Store the result associated with a failed configuration here. This can
     * then be used when reporting the result of a skipped test to provide
     * additional information in TestRail
     */
    private ThreadLocal<ITestResult> testSkipResult = new ThreadLocal<ITestResult>();

    public TestRailListener() {
        try {
            reporter = new TestRailReporter();
            testRailIdsList = new ArrayList<Integer>();
            props = new HashMap<String, Object>();
            testResultsList = new ArrayList<Map<String,Object>>();
            //enabled = reporter.isEnabled();
        } catch (Throwable ex) {
            logger.severe("Ran into exception initializing reporter: " + ex.getMessage());
            ex.printStackTrace();
        }
    }


    private void reportResult(ITestResult result) {
        try {

            Method method = result.getMethod().getConstructorOrMethod().getMethod();
            int status = result.getStatus();
            Throwable throwable = result.getThrowable();

            TestRailCase trCase = method.getAnnotation(TestRailCase.class);
            int trID = trCase.testRailId();
            testRailIdsList.add(trID);

            Test test = method.getAnnotation(Test.class);

            Map<String, Object> props = new HashMap<String, Object>();
            long elapsed = (result.getEndMillis() - result.getStartMillis()) / 1000;
            elapsed = elapsed == 0 ? 1 : elapsed; //we can only track 1 second as the smallest unit

            props.put("trID",  trID);
            props.put("elapsed",  elapsed + "s");
            props.put("status", getStatus(status));
            props.put("throwable", throwable);

            //override if needed
            if (status == ITestResult.SKIP) {
                ITestResult skipResult = testSkipResult.get();
                if (skipResult != null) {
                    props.put("throwable", skipResult.getThrowable());
                }
            }

            Map<String, String> moreInfo = new LinkedHashMap<String, String>();
            moreInfo.put("class", result.getMethod().getRealClass().getCanonicalName());
            moreInfo.put("method", result.getMethod().getMethodName());
            //Object[] obj = (Object[]) result.getParameters()[0];
            String obj =  result.getParameters()[2].toString();

            platform =  (String) result.getTestContext().getAttribute("platform");
            language = (String) result.getTestContext().getAttribute("language");
            testRailEnabled = (String) result.getTestContext().getAttribute("testRailEnabled");

            props.put("platform", platform);
            props.put("language", language);


            if (result.getParameters() != null) {
                //moreInfo.put("parameters", Arrays.toString(obj));
                moreInfo.put("parameters", obj);
            }
            props.put("moreInfo", moreInfo);
            //reporter.reportResult(testRailIdsList, props);
            testResultsList.add(props);


        } catch (Exception ex) {
            // only log and do nothing else
            //logger.severe("Ran into exception " + ex.getMessage());
            //logger.severe("Ran into exception " + ex.printStackTrace());
            ex.printStackTrace();
        }
    }

    @Override
    public void onStart(ITestContext result)
    {
    }

    @Override
    //on test class finish
    public void onFinish(ITestContext result)
    {
        if (testRailEnabled.equals("true")) {
            try {
                reporter.reportResult(platform, language, testRailIdsList, testResultsList);
                testRailIdsList.clear();
                testResultsList.clear();
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            } catch (APIException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onStart(ISuite suite)
    {
    }

    @Override
    public void onFinish(ISuite suite)
    {
        try {
            reporter.closeCurrentPlan();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (APIException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result)
    {
    }

    // When Test case get failed, this method is called.
    @Override
    public void onTestFailure(ITestResult result)
    {
        //System.out.println("The name of the testcase failed is :"+result.getName());
        reportResult(result);
    }

    // When Test case get Skipped, this method is called.
    @Override
    public void onTestSkipped(ITestResult result)
    {
        //System.out.println("The name of the testcase Skipped is :"+result.getName());
        reportResult(result);
    }

    // When Test case get Started, this method is called.
    @Override
    public void onTestStart(ITestResult result)
    {
        //System.out.println(result.getName()+" test case started");

    }

    // When Test case get passed, this method is called.
    @Override
    public void onTestSuccess(ITestResult result)
    {
        reportResult(result);
    }

    public void onConfigurationSuccess(ITestResult iTestResult) {
        testSkipResult.remove();
    }

    public void onConfigurationFailure(ITestResult iTestResult) {
        testSkipResult.set(iTestResult);
    }

    public void onConfigurationSkip(ITestResult iTestResult) {
        //nothing here
    }



    private TestRailStatus getStatus(int status) {
        switch (status) {
            case ITestResult.SUCCESS:
                return TestRailStatus.PASS;
            case ITestResult.FAILURE:
                return TestRailStatus.FAIL;
            case ITestResult.SUCCESS_PERCENTAGE_FAILURE:
                return TestRailStatus.FAIL;
            case ITestResult.SKIP:
                return TestRailStatus.SKIP;
            default:
                return TestRailStatus.UNTESTED;
        }
    }

}


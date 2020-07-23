package com.ivs.testrail;

import com.google.common.collect.Lists;
import com.ivs.testrail.dto.Plan;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;

/**
 * This class is responsible with communicating with TestRail and reporting results to it.
 *
 * It's invoked by {@link TRListener} and has methods that can be
 * directly invoked from the test code as well.
 *
 */
public class TestRailReporter {

    private Logger logger = Logger.getLogger(TestRailReporter.class.getName());
    private TestRailClient client;
    private Boolean enabled;
    //keys for the properties map that is used to pass test information into this reporter
    public static final String TEST_RAIL_CASE_ID = "trID";
    public static final String KEY_MORE_INFO = "moreInfo";
    public static final String KEY_SCREENSHOT_URL = "screenshotUrl";
    public static final String KEY_STATUS = "status";
    public static final String KEY_ELAPSED = "elapsed";
    public static final String KEY_THROWABLE = "throwable";
    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final String planName = String.format("Plan - '%s'", SIMPLE_DATE_FORMAT.format(new Date()));
    public static final String runName = String.format("Run - '%s'", SIMPLE_DATE_FORMAT.format(new Date()));
    int runId = 0;
    public static Properties trProperties;

    public TestRailReporter() {

        try {

            trProperties = loadProperties();
            String enabled = trProperties.getProperty("enabled");
            Boolean trnabled = enabled == null ? false : true;

            if (!trnabled) {
                logger.info("TestRail listener is not enabled. Results will not be reported to TestRail.");
                return;
            }

        } catch(Exception ex) {
            //wrap in a Runtime and throw again because we don't want to handle it and we want to stop the execution asap.
            throw new RuntimeException(ex);
        }
    }


    public int createPlanAndRun(List<Integer> caseIds){

        try {

        String url = trProperties.getProperty("testrail_url");
        String username = trProperties.getProperty("testrail_username");
        String password = trProperties.getProperty("testrail_pwd");
        String suiteId = trProperties.getProperty("testrail_suite_id");
        String projectId = trProperties.getProperty("testrail_projectId");

        client = new TestRailClient(url,username,password);

        Map<String, Object> runProps = new HashMap<String, Object>();
                runProps.put("include_all",false);
                runProps.put("name",runName);
                runProps.put("suite_id",suiteId);
                runProps.put("case_ids", caseIds);

        Map<String, Object> planEntryProps = new HashMap<String, Object>();
                planEntryProps.put("name",runName);
                planEntryProps.put("suite_id",suiteId);
                planEntryProps.put("include_all",false);
                planEntryProps.put("case_ids", caseIds);
                planEntryProps.put("runs", Lists.newArrayList(runProps));

        Map<String, Object> planprops = new HashMap<String, Object>();
                planprops.put("name",planName);
                planprops.put("entries",Lists.newArrayList(planEntryProps));
        Plan plan = client.addPlan(projectId, planName,null, planprops);

        runId = plan.entries.get(0).runs.get(0).id;
        System.out.println("Generirani run: " + runId);


        }   catch(Exception e)  {

            e.printStackTrace();

        }
        return runId;
        }


    /**
     * Reports results to testrail
     * @param caseIds  - list of test rail  ids
     * @param testResultsList - list of test results
     */

    public void reportResult(List<Integer> caseIds, List<Map<String, Object>> testResultsList) {


        createPlanAndRun(caseIds);


        for (Map<String, Object> testResult : testResultsList) {


        int trID = (Integer)testResult.get(TEST_RAIL_CASE_ID);
        TestRailStatus resultStatus = (TestRailStatus)testResult.get(KEY_STATUS);
        Throwable throwable = (Throwable)testResult.get(KEY_THROWABLE);
        String elapsed = (String)testResult.get(KEY_ELAPSED);
        String screenshotUrl = (String)testResult.get(KEY_SCREENSHOT_URL);
        Map<String, String> moreInfo = (Map<String, String>)testResult.get(KEY_MORE_INFO);

        try {

            StringBuilder comment = new StringBuilder("More info:\n");
            if (moreInfo != null && !moreInfo.isEmpty()) {
                for (Map.Entry<String, String> entry: moreInfo.entrySet()) {
                    comment.append("- ").append(entry.getKey()).append(" : ")
                            .append('`').append(entry.getValue()).append("`\n");
                }
            } else {
                comment.append("- `none`\n");
            }
            comment.append("\n");
            if (screenshotUrl != null && !screenshotUrl.isEmpty()) {
                comment.append("![](").append(screenshotUrl).append(")\n\n");
            }
            if (resultStatus.equals(TestRailStatus.SKIP)) {
                comment.append("Test skipped because of configuration method failure. " +
                        "Related config error (if captured): \n\n");
                comment.append(getStackTraceAsString(throwable));
            }
            if (resultStatus.equals(TestRailStatus.FAIL)) {
                comment.append("Test failed with following exception (if captured): \n\n");
                comment.append(getStackTraceAsString(throwable));
            }

            //add the result
            Map<String, Object> body = new HashMap<String, Object>();
            body.put("status_id", getStatus(resultStatus));
            body.put("comment", new String(comment.toString().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
            body.put("elapsed", elapsed);

            client.addResultForCase(runId, trID, body);
            } catch(Exception ex) {
                //only log and do nothing else
                logger.severe("Ran into exception " + ex.getMessage());
            }
        }
    }




    private String getStackTraceAsString(Throwable throwable) throws UnsupportedEncodingException {
        if (throwable == null) {
            return "";
        }
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        throwable.printStackTrace(new PrintStream(os));
        String str = new String(os.toByteArray(), "UTF-8");
        str = "    " + str.replace("\n", "\n    ").replace("\t", "    "); //better printing
        return str;
    }

    /**
     * @param status TestNG specific status code
     * @return TestRail specific status IDs
     */

    private int getStatus(TestRailStatus status) {
        switch (status) {
            case PASS:
                return 1; //Passed
            case FAIL:
                return 5; //Failed
            case SKIP:
                return 2; //Blocked
            default:
                return 3; //Untested
        }
    }

    public Properties loadProperties(){
        String env = System.getProperty("testrail");
        trProperties = new Properties();
        final ClassLoader loader = getClass().getClassLoader();
        InputStream config = loader.getResourceAsStream("testrail.properties");
        try {
            trProperties.load(config);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return trProperties;
    }
}



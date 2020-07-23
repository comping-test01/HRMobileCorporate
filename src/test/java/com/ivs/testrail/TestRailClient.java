package com.ivs.testrail;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ivs.testrail.dto.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TestRailClient {

    //underlying api client
    public APIClient client;
    //(de)-serializes objects to/from json
    private ObjectMapper objectMapper;

    /**
     * Creates an instance of the client and setups up required state
     *
     * @param url
     * @param username
     * @param password
     */
    public TestRailClient(String url, String username, String password) {
        client = new APIClient(url);
        client.setUser(username);
        client.setPassword(password);
        objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }



    //Plans

    public Plan getPlan(int planId) throws IOException, APIException {
        return objectMapper.readValue(client.sendGet("get_plan/" + planId).toString(), Plan.class);
    }

    public List<Plan> getPlans(int projectId, Map<String, String> filters)
               throws IOException, APIException {
           String url = "get_plans/" + projectId;
           if (filters != null) {
               for (Map.Entry<String, String> entry : filters.entrySet()) {
                   url += "&" + entry.getKey() + "=" + entry.getValue();
               }
           }
           return objectMapper.readValue(client.sendGet(url).toString(), new TypeReference<List<Plan>>(){});
       }

    public Plan addPlan(String projectId, String name, Integer milestoneId, Map<String, Object> body)
            throws IOException, APIException {

        return objectMapper.readValue(client.sendPost("add_plan/" + projectId, body).toString(), Plan.class);
    }

    public PlanEntry addPlanEntry(int planId, int suiteId, Map<String, Object> properties) throws IOException, APIException {
        Map<String, String> body = new HashMap<String, String>();
        //body.put("suite_id", String.valueOf(suiteId));
        return objectMapper.readValue(client.sendPost("add_plan_entry/" + planId, properties).toString(), PlanEntry.class);
    }

    public Plan closePlan(int planId) throws IOException, APIException {
        return objectMapper.readValue(client.sendPost("close_plan/" + planId, "").toString(), Plan.class);
    }

    public void deletePlan(int planId) throws IOException, APIException {
        client.sendPost("delete_plan/" + planId, "");
    }


    //Results

    public List<Result> getResults(int testId) throws IOException, APIException {
        String url = "get_results/" + testId;
        return objectMapper.readValue(client.sendGet(url).toString(), new  TypeReference<List<Result>>(){});
    }

    public List<Result> getResultsForRun(int runId, Map<String, String> filters) throws IOException, APIException {
        String url = "get_results_for_run/" + runId;
        if (filters != null) {
            for (Map.Entry<String, String> entry : filters.entrySet()) {
                url += "&" + entry.getKey() + "=" + entry.getValue();
            }
        }
        return objectMapper.readValue(client.sendGet(url).toString(), new  TypeReference<List<Result>>(){});
    }

    public List<Result> getResultsForCase(int runId, int caseId) throws IOException, APIException {
        String url = "get_results_for_case/" + runId + "/" + caseId + "&limit=10";
        return objectMapper.readValue(client.sendGet(url).toString(), new  TypeReference<List<Result>>(){});
    }

    public Result addResultForCase(int runId, int caseId, int statusId, String comment)
            throws IOException, APIException {
        String url = "add_result_for_case/" + runId + "/" + caseId;
        Map<String, String> body = new HashMap<String, String>();
        body.put("status_id", String.valueOf(statusId));
        body.put("comment", comment);
        return objectMapper.readValue(client.sendPost(url, body).toString(), Result.class);
    }

    public Result addResultForCase(int runId, int caseId, Map<String, Object> properties)
            throws IOException, APIException {
        String url = "add_result_for_case/" + runId + "/" + caseId;
        return objectMapper.readValue(client.sendPost(url, properties).toString(), Result.class);
    }



    //Tests

    public Test getTest(int testId) throws IOException, APIException {
        return objectMapper.readValue(client.sendGet("get_test/" + testId).toString(), Test.class);
    }

    public List<Test> getTests(int runId) throws IOException, APIException {
        return objectMapper.readValue(client.sendGet("get_tests/" + runId).toString(), new TypeReference<List<Test>>(){});
    }


    //Cases

    public Case addCase(int sectionId, String title, Map<String, String> fields)
            throws IOException, APIException {
        Map<String, String> body = new HashMap<String, String>();
        body.put("title", title);
        if (fields != null) {
            body.putAll(fields);
        }
        return objectMapper.readValue(
                client.sendPost("add_case/" + sectionId, body).toString(),
                Case.class);
    }

    public Case getCase(int caseId) throws IOException, APIException {
        return objectMapper.readValue(client.sendGet("get_case/" + caseId).toString(), Case.class);
    }

    public List<Case> getCases(int projectId, int suiteId, int sectionId, Map<String, String> filters)
            throws IOException, APIException {
        String url = "get_cases/" + projectId;
        if (suiteId > 0) {
            url += "&suite_id=" + suiteId;
        }
        if (sectionId > 0) {
            url += "&section_id=" + sectionId;
        }
        if (filters != null) {
            for (Map.Entry<String, String> entry : filters.entrySet()) {
                url += "&" + entry.getKey() + "=" + entry.getValue();
            }
        }
        return objectMapper.readValue(client.sendGet(url).toString(), new TypeReference<List<Case>>(){});
    }

    /**
     * When you need to work with custom fields that are not part of the {@link Case} class
     * @param projectId
     * @param suiteId
     * @param sectionId
     * @param filters
     * @return
     * @throws IOException
     * @throws APIException
     */
    public List<Map<String, Object>> getCasesAsMap(int projectId, int suiteId, int sectionId, Map<String, String> filters)
                throws IOException, APIException {
        String url = "get_cases/" + projectId;
        if (suiteId > 0) {
            url += "&suite_id=" + suiteId;
        }
        if (sectionId > 0) {
            url += "&section_id=" + sectionId;
        }
        if (filters != null) {
            for (Map.Entry<String, String> entry : filters.entrySet()) {
                url += "&" + entry.getKey() + "=" + entry.getValue();
            }
        }
        return objectMapper.readValue(client.sendGet(url).toString(), new TypeReference<List<Map<String, Object>>>(){});
    }

    public Case updateCase(int caseId, Map<String, Object> fields) throws IOException, APIException {
        return objectMapper.readValue(client.sendPost("update_case/" + caseId, fields).toString(), Case.class);
    }


   // Sections

    public Section addSection(int projectId, String name, int parentId, int suiteId)
            throws IOException, APIException {
        Map<String, String> body = new HashMap<String, String>();
        if (suiteId > 0) {
            body.put("suite_id", String.valueOf(suiteId));
        }
        if (parentId > 0) {
            body.put("parent_id", String.valueOf(parentId));
        }
        body.put("name", name);
        return objectMapper.readValue(
                client.sendPost("add_section/" + projectId, body).toString(), Section.class);
    }


    //Suites

    public Suite addSuite(int projectId, String name) throws IOException, APIException {
        Map<String, String> body = new HashMap<String, String>();
        body.put("name", name);
        return objectMapper.readValue(
                client.sendPost("add_suite/" + projectId, body).toString(), Suite.class);
    }

    public Suite getSuite(int suiteId) throws IOException, APIException {
        return objectMapper.readValue(client.sendGet("get_suite/" + suiteId).toString(), Suite.class);
    }

    public List<Suite> getSuites(int projectId) throws IOException, APIException {
        return objectMapper.readValue(client.sendGet("get_suites/" + projectId).toString(),
                new TypeReference<List<Suite>>(){});
    }


    //Milestones

    public Milestone getMilestone(int milestoneId) throws IOException, APIException {
        return objectMapper.readValue(client.sendGet("get_milestone/" + milestoneId).toString(), Milestone.class);
    }

    public List<Milestone> getMilestones(int projectId) throws IOException, APIException {
        return objectMapper.readValue(client.sendGet("get_milestones/" + projectId).toString(),
                new TypeReference<List<Milestone>>(){});
    }

    public Milestone addMilestone(int projectId, String name, String description) throws IOException, APIException {
        Map<String, String> body = new HashMap<String, String>();
        body.put("name", name);
        if (description != null) {
            body.put("description", description);
        }
        return objectMapper.readValue(
                client.sendPost("add_milestone/" + projectId, body).toString(), Milestone.class);
    }


    //Runs

    public Run getRun(int runId) throws IOException, APIException {
        return objectMapper.readValue(client.sendGet("get_run/" + runId).toString(), Run.class);
    }

    public Run addRun(int projectId, Map<String, Object> fields) throws IOException, APIException {
        return objectMapper.readValue(client.sendPost("add_run/" + projectId, fields).toString(), Run.class);
    }

    public Run updateRun(int runId, Map<String, Object> fields) throws IOException, APIException {
        return objectMapper.readValue(client.sendPost("update_run/" + runId, fields).toString(), Run.class);
    }


    public PlanEntry updatePlanEntry(int planId, String entryId, Map<String, Object> properties) throws IOException, APIException {
        String url = "update_plan_entry/" + planId + "/" + entryId;
        return objectMapper.readValue(client.sendPost(url, properties).toString(), PlanEntry.class);
    }


}

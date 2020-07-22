package com.ivs.util;
import java.lang.reflect.Method;
import org.testng.annotations.DataProvider;


public class DataProviderSource {

    @DataProvider(name="testData")
    public static Object[][] getTestData(Method method) {

        int intColumns = 0;

        Object[][] arrInputParams;
        String inputFileName;
        String testName = method.getName();

        if (method.getName().toLowerCase().contains("login")){
            intColumns = 3;
        }


        inputFileName = "HRWebCorporate" + testName.substring(0, 1).toUpperCase() + testName.substring(1) + "InputParameters.xlsx";
        ExcelUtil objData = new ExcelUtil();
        arrInputParams = objData.GetParameters(inputFileName,"Input1",intColumns);
        return arrInputParams;
    }

    @DataProvider(name="loginData")
    public static Object[][] getLoginData(Method method) {

        int intColumns = 2;
        Object[][] arrInputParams;
        String inputFileName;
        String testName = method.getName();
        //inputFileName = "HRWebCorporate" + testName.substring(0, 1).toUpperCase() + testName.substring(1) + "InputParameters.xlsx";
        inputFileName = testName.substring(0, 1).toUpperCase() + testName.substring(1)+ "InputParameters.xlsx";
        System.out.println(inputFileName);
        ExcelUtil objData = new ExcelUtil();
        arrInputParams = objData.GetParameters(inputFileName,"Input1",intColumns);
        return arrInputParams;
    }

}
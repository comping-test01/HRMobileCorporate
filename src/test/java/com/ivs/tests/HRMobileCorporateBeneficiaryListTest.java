package com.ivs.tests;


import com.ivs.pages.*;
import com.ivs.util.DataProviderSource;
import com.ivs.util.ExcelUtil;
import com.ivs.util.Utils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.lang.reflect.Method;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;


public class HRMobileCorporateBeneficiaryListTest extends BaseTest {


    private Object [][] arrInputParams;
    private int intBrojac = 0, columns = 6;
    String text;

    int intColumns = 6;
    String inputFileName;
    Object[][] outputParams = new Object [1][intColumns];


    /*@DataProvider(name = "testData")
    public Object[][] testData() {

        ExcelUtil objData = new ExcelUtil();
        arrInputParams = objData.GetParameters("BeneficiaryListMobileInputParameters.xlsx","Input1",columns);
        return arrInputParams;
    }

     */

    @Test (dataProvider = "testData", dataProviderClass = DataProviderSource.class)
    public void BeneficiaryListTest(Object [] objInput) throws InterruptedException {


        SoftAssert soft = new SoftAssert();
        driver = pageGen.getDriver();
        String companyName = objInput[0].toString();
        String IBAN = objInput[1].toString();
        String identifierName = objInput[2].toString();
        String beneficiaryName = objInput[3].toString();


        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        LoginPage loginPage = new LoginPage(driver);
        //ResourceBundle resourceBundle = ResourceBundle.getBundle("messages", Locale.getDefault());

        try {
            loginPage.loginAndEnterPIN(applicationPIN,language);
            ClientSelectPage clientSelectPage = new ClientSelectPage(driver);
            HomePage homePage = new HomePage(driver);

            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

            homePage.doSelectPaymentAndBeneficiaryList();
            BeneficiaryListPage beneficiaryListPage = new BeneficiaryListPage(driver);

            beneficiaryListPage.addBeneficiary(IBAN, identifierName, beneficiaryName);
            //beneficiaryListPage.checkAddedBeneficiary(beneficiaryName, applicationPIN);
            homePage.doSelectPaymentAndPaySomeone();
            PaySomeonePage paySomeonePage = new PaySomeonePage(driver);
            paySomeonePage.checkBeneficiary(beneficiaryName, IBAN, identifierName);

            boolean found = beneficiaryListPage.checkIfSearchButtonExists(20,2);
            outputParams[0][4] = "OK";
            outputParams[0][5] = "";
            intBrojac++;
            soft.assertTrue(true);
            soft.assertAll();


        } catch (Exception e) {

            outputParams[0][4] = "NOK";
            outputParams[0][5] = e.toString();
            e.printStackTrace();

            HomePage homePage = new HomePage(driver);
            homePage.doLogOut();

            intBrojac++;
            soft.assertTrue(false);
            soft.assertAll();
        }

    }


    @AfterMethod
    public void saveOutput(Method method) {

        ExcelUtil objData = new ExcelUtil();
        String testName = method.getName();
        inputFileName = "HRMobileCorporate" + testName.substring(0, 1).toUpperCase() + testName.substring(1)+ "InputParameters.xlsx";
        objData.SaveParameters(inputFileName,"Input1",outputParams,intColumns,2,"android");
    }

}

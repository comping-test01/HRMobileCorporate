package com.ivs.tests;

import com.ivs.pages.*;
import com.ivs.testrail.TestRailCase;
import com.ivs.testrail.TestRailListener;
import com.ivs.util.DataProviderSource;
import com.ivs.util.ExcelUtil;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import java.lang.reflect.Method;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

@Listeners(TestRailListener.class)
public class HRMobileCorporateBeneficiaryListTest extends BaseTest {

    int intColumns = 6;
    String inputFileName;
    Object[][] outputParams = new Object [1][intColumns];

    @TestRailCase(testRailId = 3240)
    @Test (dataProvider = "testData", dataProviderClass= DataProviderSource.class)
    public void beneficiaryListNewBeneficiaryTest(Object [] objInput) throws InterruptedException {

        SoftAssert soft = new SoftAssert();
        driver = pageGen.getDriver();
        String companyName = objInput[0].toString();
        String IBAN = objInput[1].toString();
        String identifierName = objInput[2].toString();
        String beneficiaryName = objInput[3].toString();

        WebDriverWait wait = new WebDriverWait(driver, 10);

        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        LoginPage loginPage = new LoginPage(driver);
        ResourceBundle resourceBundle = ResourceBundle.getBundle("messages", Locale.getDefault());


        try {
            loginPage.loginAndEnterPIN(applicationPIN,language);
            ClientSelectPage clientSelectPage = new ClientSelectPage(driver);
            HomePage homePage = new HomePage(driver);

            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

            homePage.doSelectPaymentAndBeneficiaryList();
            BeneficiaryListPage beneficiaryListPage = new BeneficiaryListPage(driver);

            //DEBUG pinNumber exists for deletion purposes, to be removed
            String pinNumber = "789987";
            beneficiaryListPage.deleteAddedBeneficiary(beneficiaryName,pinNumber, wait);
            identifierName = beneficiaryListPage.addBeneficiary(IBAN, beneficiaryName, wait);
            homePage.doSelectPaymentAndPaySomeone(wait);
            PaySomeonePage paySomeonePage = new PaySomeonePage(driver);
            paySomeonePage.checkBeneficiary(beneficiaryName, IBAN, identifierName, wait);
            outputParams[0][4] = "OK";
            outputParams[0][5] = "";

            soft.assertTrue(true);
            soft.assertAll();


        } catch (Exception e) {

            outputParams[0][4] = "NOK";
            outputParams[0][5] = e.toString();
            e.printStackTrace();

            HomePage homePage = new HomePage(driver);
            homePage.doLogOut();

            soft.assertTrue(false);
            soft.assertAll();
        }


    }


    @AfterMethod
    public void saveOutput (Method method) throws InterruptedException {

        HomePage homePage = new HomePage(driver);
        homePage.doLogOut();

        String testName = method.getName();
        inputFileName = "HRMobileCorporate" + testName.substring(0, 1).toUpperCase() + testName.substring(1)+ "InputParameters.xlsx";
        ExcelUtil objData = new ExcelUtil();
        objData.SaveParameters(inputFileName, "Input1", outputParams,intColumns,2, platform);
    }

}

package com.ivs.tests;

import com.ivs.pages.HomePage;
import com.ivs.pages.LoginPage;
import com.ivs.testrail.TestRailListener;
import com.ivs.testrail.TestRailCase;
import com.ivs.util.DataProviderSource;
import com.ivs.util.ExcelUtil;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.lang.reflect.Method;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

@Listeners(TestRailListener.class)
public class HRMobileCorporateLoginTest extends BaseTest {
    int intColumns = 3;
    String inputFileName;
    Object[][] outputParams = new Object [1][intColumns];

    @TestRailCase(testRailId = 3238)
    @Test (dataProvider = "testData", dataProviderClass= DataProviderSource.class)
    public void loginInvalidPINTest (Object [] objInput) {

        String pin = objInput[0].toString();
        Locale.setDefault(new Locale(this.language, this.language.toUpperCase()));
        ResourceBundle resourceBundle = ResourceBundle.getBundle("messages", Locale.getDefault());
        String expectedText;

        SoftAssert soft = new SoftAssert();

        try{


            if (pin.contains(",")){
                pin = pin.split(",")[0];
            }
            driver = pageGen.getDriver();

            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

            //LoginPage loginPage = pageGen.GetInstance(LoginPage.class);
            LoginPage loginPage = new LoginPage(driver);
            loginPage.loginAndEnterPIN(pin,language);
            loginPage.verifyWrongCredentials();


            outputParams[0][1] = "OK";
            outputParams[0][2] = "";

            soft.assertTrue(true);
            soft.assertAll();
        }
        catch (Exception e) {
            outputParams[0][1] = "NOK";
            outputParams[0][2] = e.toString();
            e.printStackTrace();

            soft.assertTrue(false);
            soft.assertAll();
        }
    }

    @TestRailCase(testRailId = 3237)
    @Test (dataProvider = "testData", dataProviderClass= DataProviderSource.class)
    public void loginValidPINTest (Object [] objInput) {
        SoftAssert soft = new SoftAssert();
        String pin = objInput[0].toString();
        try {
            driver = pageGen.getDriver();


            if (pin.contains(",")){
                pin = pin.split(",")[0];
            }


            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            LoginPage loginPage = new LoginPage(driver);
            loginPage.loginAndEnterPIN(pin,language);
            HomePage homepage = new HomePage(driver);

            homepage.verifyLoginSuccess();

            outputParams[0][1] = "OK";
            outputParams[0][2] = "";

            soft.assertTrue(true);
            soft.assertAll();
        }
        catch (Exception e) {
            outputParams[0][1] = "NOK";
            outputParams[0][2] = e.toString();
            e.printStackTrace();

            soft.assertTrue(false);
            soft.assertAll();
        }

    }

    @AfterMethod
    public void saveOutput(Method method) throws InterruptedException {

        HomePage homePage = new HomePage(driver);
        homePage.doLogOut();

        String testName = method.getName();

        inputFileName = "HRMobileCorporate" + testName.substring(0, 1).toUpperCase() + testName.substring(1)+ "InputParameters.xlsx";
        ExcelUtil objData = new ExcelUtil();
        objData.SaveParameters(inputFileName, "Input1", outputParams,intColumns,2, platform);
    }
}
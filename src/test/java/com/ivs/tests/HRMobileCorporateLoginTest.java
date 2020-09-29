package com.ivs.tests;

import com.ivs.pages.HomePage;
import com.ivs.pages.LoginPage;
import com.ivs.testrail.TestRailListener;
import com.ivs.testrail.TestRailCase;
import com.ivs.util.JSONDataProvider;
import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Listeners(TestRailListener.class)
public class HRMobileCorporateLoginTest extends BaseTest {

    @TestRailCase(testRailId = 3237)
    @Test(priority=1,groups={"Login"}, dataProvider="fetchData_JSON", dataProviderClass= JSONDataProvider.class, enabled=true)
    public void loginValidPINTest(String rowID, String description, JSONObject testData){


        String pin = applicationPIN;
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

        }
        catch (Exception e) {

            e.printStackTrace();

        }

    }

    @TestRailCase(testRailId = 3238)
    @Test(priority=2,groups={"Login"}, dataProvider="fetchData_JSON", dataProviderClass= JSONDataProvider.class, enabled=true)
    public void loginInvalidPINTest(String rowID, String description, JSONObject testData){

        String expectedText;
        String pin = testData.get("pin").toString();
        Locale.setDefault(new Locale(this.language, this.language.toUpperCase()));
        ResourceBundle resourceBundle = ResourceBundle.getBundle("messages", Locale.getDefault());


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

        }
        catch (Exception e) {
            e.printStackTrace();

        }
    }


}
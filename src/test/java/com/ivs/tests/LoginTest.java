package com.ivs.tests;

import com.ivs.pages.LoginPage;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;


public class LoginTest extends BaseTest {


    @Test (priority = 0)
    public void invalidLoginTest_InvalidUserNameInvalidPassword () throws InterruptedException {

        driver = pageGen.getDriver();
        driver.get(appiumServerAddress);

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        LoginPage loginPage = pageGen.GetInstance(LoginPage.class);
        //loginPage.doLogin(masterUsername, masterPassword);


        //*************ASSERTIONS***********************
        //Thread.sleep(500); //It is better to use explicit wait here.
        //wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='pages.loginPage.somethingWentWrong']")));
        //page.GetInstance(LoginPage.class).verifyWrongCredentials(("pages.loginPage.somethingWentWrong"));
    }

    @Test (priority = 0)
    public void loginTest () throws InterruptedException {

        driver = pageGen.getDriver();
        driver.get(appiumServerAddress);

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        LoginPage loginPage = pageGen.GetInstance(LoginPage.class);
        //loginPage.doLogin(masterUsername, masterPassword);

    }

}

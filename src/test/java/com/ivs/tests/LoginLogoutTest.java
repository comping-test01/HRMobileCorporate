package com.ivs.tests;


import com.ivs.pages.ClientSelectPage;
import com.ivs.pages.HomePage;
import com.ivs.pages.LoginPage;
import com.ivs.util.ExcelUtil;
import com.ivs.util.Utils;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.concurrent.TimeUnit;


public class LoginLogoutTest extends BaseTest {


    private Object [][] arrInputParams;
    private int intBrojac = 0, columns = 5;
    String text;

    @DataProvider(name = "testData")
    public Object[][] testData() {

        ExcelUtil objData = new ExcelUtil();
        arrInputParams = objData.GetParameters("LoginLogoutMobileInputParameters.xlsx","Input1",columns);
        return arrInputParams;
    }

    @Test(dataProvider = "testData")
    public void LoginLogoutTest(Object [] objInput) throws InterruptedException {
        MobileElement element;
        SoftAssert soft = new SoftAssert();
        driver = pageGen.getDriver();
        Utils utils = new Utils(driver);
        String test = objInput[0].toString();
        String pin = objInput[1].toString();
        String companyName = objInput[2].toString();


        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        LoginPage loginPage = new LoginPage(driver);

        try {
            loginPage.pressLogin();
            loginPage.enterPIN(pin);
            switch (test.toUpperCase()){
                case "INVALID":
                    if (!loginPage.isPINPageVisible())
                        throw new Exception("PIN is valid");
                    break;
                case "VALID":
                    ClientSelectPage clientSelectPage = new ClientSelectPage(driver);
                    HomePage homePage = new HomePage(driver);

                    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

                    // odabir clienta za slucaj kad izbaci clientSelectPage pri loginu
                    if(clientSelectPage.isVisible()) {
                        clientSelectPage.doSearchAndSelectClient(companyName);
                        element = utils.fluentWaitforElement(By.xpath("//*[@text='Dobrodošli, ']"), 60, 2);
                        text = element.getText();
                        Assert.assertEquals(text, "Dobrodošli, ");
                    }

                    homePage.doLogOut();
                    if(loginPage.isVisible() == false) throw new Exception("Neuspješan logout");
                    break;
            }

            arrInputParams[intBrojac][3] = "OK";
            arrInputParams[intBrojac][4] = "";

            intBrojac++;
            soft.assertTrue(true);
            soft.assertAll();


        } catch (Exception e) {

            arrInputParams[intBrojac][3] = "NOK";
            arrInputParams[intBrojac][4] = e.toString();
            e.printStackTrace();
            intBrojac++;
            soft.assertTrue(false);
            soft.assertAll();
        }
    }

    @AfterMethod
    public void logOut(){
        try {
            Thread.sleep(1000);
            LoginPage loginPage = new LoginPage(driver);
            if (loginPage.isPINPageVisible())
                loginPage.closePINPage();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @AfterSuite
    public void closeOut() {
        ExcelUtil objData = new ExcelUtil();
        objData.SaveParameters("LoginLogoutMobileInputParameters.xlsx","Input1",arrInputParams,columns,2,"android");
        driver.quit();
    }
}
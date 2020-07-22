package com.ivs.tests;


import com.ivs.pages.ClientSelectPage;
import com.ivs.pages.HomePage;
import com.ivs.pages.LoginPage;
import com.ivs.pages.TransferToMyselfPage;
import com.ivs.util.ExcelUtil;
import com.ivs.util.Utils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.concurrent.TimeUnit;


public class TransferToMyselfCorporateTest extends BaseTest {


    private Object [][] arrInputParams;
    private int intBrojac = 0, columns = 9;
    String text;



    @DataProvider(name = "testData")
    public Object[][] testData() {

        ExcelUtil objData = new ExcelUtil();
        arrInputParams = objData.GetParameters("TransferToMyselfMobileInputParameters.xlsx","Input1",columns);  //C:\Appium\TransferToMyselfMobileInputParameters.xlsx
        return arrInputParams;
    }

    @Test(dataProvider = "testData")
    public void TransferToMyselfTest(Object [] objInput) throws InterruptedException {
        MobileElement element;
        SoftAssert soft = new SoftAssert();
        driver = pageGen.getDriver();
        Utils utils = new Utils(driver);
        String testType = objInput[0].toString();
        String companyName = objInput[2].toString();
        String payerIBAN = objInput[3].toString();
        String payeeIBAN = objInput[4].toString();
        String amount = objInput[5].toString();
        String description = objInput[6].toString();


        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        LoginPage loginPage = new LoginPage(driver);

        //driver.findElement(By.xpath("//*[@text='ic menu']")).click();
        try {
            loginPage.enterPIN();
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
            // odabir clienta za slucaj kad ne izbaci
            else{
                element = utils.fluentWaitforElement(By.xpath("//*[@text='Dobrodošli, ']"), 60, 2);
                text = element.getText();
                Assert.assertEquals(text, "Dobrodošli, ");
                //provjera je li potrebna promjena subjekta //*[@text='APOLLO HR D.O.O.']

                if (!(isElementPresent(By.xpath("//*[@text='" + companyName + "' and @class='android.view.View']"), driver))) {
                    homePage.runHomeMenu();
                    homePage.doChangeCompanyClick();
                    clientSelectPage.doSearchAndSelectClient(companyName);
                }
            }
            homePage.doSelectPaymentAndTransferToMyself();

            TransferToMyselfPage transferToMyselfPage = new TransferToMyselfPage(driver);

            if(!transferToMyselfPage.isVisible()){
                driver.navigate().back(); //zatvaranje izbornika
                homePage.doSelectPaymentAndTransferToMyself();
            }

            transferToMyselfPage.doSelectPayer(payerIBAN);
            transferToMyselfPage.doSelectPayee(payeeIBAN);

            transferToMyselfPage.doFillAmount(amount);
            transferToMyselfPage.doFillDescription(description);
            transferToMyselfPage.doProceed();


            switch (testType.toUpperCase()) {
                case "SAVE":
                    transferToMyselfPage.doSave();
                    break;

                case "UPLOAD":
                    transferToMyselfPage.doUpload();
                    break;

                case "AUTHORIZE":
                    transferToMyselfPage.doAuthorize();
                    break;

                case "PAY":
                    transferToMyselfPage.doPay();
                    break;

                default:
                    throw new Exception("Invalid test type (" + testType + ") in input excel (row: " + (intBrojac + 1) + ") !");
            }

            arrInputParams[intBrojac][7] = "OK";
            arrInputParams[intBrojac][8] = "";

            driver.findElement(By.xpath("//*[@text='Završi']")).click();
            intBrojac++;
            soft.assertTrue(true);
            soft.assertAll();


        } catch (Exception e) {

            arrInputParams[intBrojac][7] = "NOK";
            arrInputParams[intBrojac][8] = e.toString();
            e.printStackTrace();

            //ako je test stao u unosu poziva na broj
            if (isElementPresent(By.xpath("//*[@text='ic info']"),driver)){
                driver.findElement(By.xpath("//*[@text='ic info']")).click();
                Thread.sleep(1000);
            }

            if (isElementPresent(By.xpath("//*[@text='ios-ic-closex']"),driver)){
                ////*[@text='ios-ic-closex']
                driver.findElement(By.xpath("//*[@text='ios-ic-closex']")).click();
                Thread.sleep(1000);
            }
            logOut();
//
//            driver.findElement(By.xpath("//*[@text='Ionic App']")).click();
//            driver.findElement(By.xpath("//*[@text='ic menu']")).click();
//            Thread.sleep(1000);
//            element = utils.fluentWaitforElement(By.xpath("//*[@text='ic logout']"), 30, 2);
//            //element.click();
//            tapOnElement(element);

            intBrojac++;
            soft.assertTrue(false);
            soft.assertAll();
        }


    }



    @AfterSuite
    public void closeOut() {
        WebElement element;

        ExcelUtil objData = new ExcelUtil();
        objData.SaveParameters("TransferToMyselfMobileInputParameters.xlsx","Input1",arrInputParams,columns,2,"android");

        /*
        if (isElementPresent(By.xpath("//*[@text='ios-ic-closex']"),driver)){
            ////*[@text='ios-ic-closex']
            driver.findElement(By.xpath("//*[@text='ios-ic-closex']")).click();
        }
        driver.findElement(By.xpath("//*[@text='Ionic App']")).click();
        driver.findElement(By.xpath("//*[@text='ic menu']")).click();

        //element = Utils.fluentWaitforElement(By.xpath("//*[@text='Početna']"), 20, 2);
        //element.click();

        if (isElementPresent(By.xpath("//*[@text='ic logout']"),driver)){
            ////*[@text='ios-ic-closex']
            driver.findElement(By.xpath("//*[@text='ic logout']")).click();
        }
    */
    //    driver.close();
        driver.quit();
       // driver.closeApp();


    }





    static boolean isElementPresent(By by, AppiumDriver d)
    {
        d.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        boolean exists = d.findElements(by).size() != 0;
        d.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        return exists;
    }
}

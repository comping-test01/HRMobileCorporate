package com.ivs.tests;


import com.ivs.pages.*;
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

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;


public class BeneficiaryListTest extends BaseTest {


    private Object [][] arrInputParams;
    private int intBrojac = 0, columns = 6;
    String text;



    @DataProvider(name = "testData")
    public Object[][] testData() {

        ExcelUtil objData = new ExcelUtil();
        arrInputParams = objData.GetParameters("BeneficiaryListMobileInputParameters.xlsx","Input1",columns);
        return arrInputParams;
    }

    @Test(dataProvider = "testData")
    public void BeneficiaryListTest(Object [] objInput) throws InterruptedException {
        MobileElement element;
        SoftAssert soft = new SoftAssert();
        driver = pageGen.getDriver();
        Utils utils = new Utils(driver);
        String companyName = objInput[0].toString();
        String IBAN = objInput[1].toString();
        String identifierName = objInput[2].toString();
        String beneficiaryName = objInput[3].toString();
        String expectedText;


        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        LoginPage loginPage = new LoginPage(driver);
        ResourceBundle resourceBundle = ResourceBundle.getBundle("messages", Locale.getDefault());

        //driver.findElement(By.xpath("//*[@text='ic menu']")).click();
        try {
            loginPage.loginAndEnterPIN(applicationPIN,language);
            ClientSelectPage clientSelectPage = new ClientSelectPage(driver);
            HomePage homePage = new HomePage(driver);

            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

            // odabir clienta za slucaj kad izbaci clientSelectPage pri loginu
            /*expectedText = resourceBundle.getString("WelcomeMessage");
            if(clientSelectPage.isVisible()) {
                clientSelectPage.doSearchAndSelectClient(companyName);
                element = utils.fluentWaitforElement(By.xpath("//*[@text='"+expectedText+", ']"), 60, 2);
                text = element.getText();
                Assert.assertEquals(text, "\""+expectedText+", ");
            }
            // odabir clienta za slucaj kad ne izbaci
            else{
                element = utils.fluentWaitforElement(By.xpath("//*[@text='"+expectedText+", ']"), 60, 2);
                text = element.getText();
                Assert.assertEquals(text, expectedText+", ");
                //provjera je li potrebna promjena subjekta //*[@text='APOLLO HR D.O.O.']

                if (!(isElementPresent(By.xpath("//*[@text='" + companyName + "' and @class='android.view.View']"), driver))) {
                    homePage.runHomeMenu();
                    homePage.doChangeCompanyClick();
                    clientSelectPage.doSearchAndSelectClient(companyName);
                }
            }*/
            homePage.doSelectPaymentAndBeneficiaryList();
            BeneficiaryListPage beneficiaryListPage = new BeneficiaryListPage(driver);

            /*if (!beneficiaryListPage.checkIfNewBeneficiaryButtonExists()){
                //ako ne postoji objekt, pokušavamo još jednom selektirati primatelhe
                homePage.doSelectBeneficiaryList();
                //paySomeonePage = new PaySomeonePage(driver);
            }*/
            String pinNumber = "789987";
            beneficiaryListPage.deleteAddedBeneficiary(beneficiaryName,pinNumber);
            homePage.doSelectPaymentAndBeneficiaryList();
            beneficiaryListPage.addBeneficiary(IBAN, identifierName, beneficiaryName);
            //beneficiaryListPage.checkAddedBeneficiary(beneficiaryName, applicationPIN);
            homePage.doSelectPaymentAndPaySomeone();
            PaySomeonePage paySomeonePage = new PaySomeonePage(driver);
            paySomeonePage.checkBeneficiary(beneficiaryName, IBAN, identifierName);
            //validacija
            boolean found = beneficiaryListPage.checkIfSearchButtonExists(20,2);
            arrInputParams[intBrojac][4] = "OK";
            arrInputParams[intBrojac][5] = "";
            intBrojac++;
            soft.assertTrue(true);
            soft.assertAll();


        } catch (Exception e) {

            arrInputParams[intBrojac][4] = "NOK";
            arrInputParams[intBrojac][5] = e.toString();
            e.printStackTrace();

            HomePage homePage = new HomePage(driver);
            homePage.doLogOut();

            intBrojac++;
            soft.assertTrue(false);
            soft.assertAll();
        }


    }



    @AfterSuite
    public void closeOut() {
        WebElement element;

        ExcelUtil objData = new ExcelUtil();
        objData.SaveParameters("BeneficiaryListMobileInputParameters.xlsx","Input1",arrInputParams,columns,2,"android");
        driver.quit();
    }

    static boolean isElementPresent(By by, AppiumDriver d)
    {
        d.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        boolean exists = d.findElements(by).size() != 0;
        d.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        return exists;
    }
}

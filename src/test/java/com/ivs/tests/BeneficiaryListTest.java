package com.ivs.tests;


import com.ivs.pages.BeneficiaryListPage;
import com.ivs.pages.ClientSelectPage;
import com.ivs.pages.HomePage;
import com.ivs.pages.LoginPage;
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


        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        LoginPage loginPage = new LoginPage(driver);

        //driver.findElement(By.xpath("//*[@text='ic menu']")).click();
        try {
            loginPage.loginAndEnterPIN(applicationPIN);
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
            homePage.doSelectPaymentAndBeneficiaryList();
            BeneficiaryListPage beneficiaryListPage = new BeneficiaryListPage(driver);

            if (!beneficiaryListPage.checkIfNewBeneficiaryButtonExists()){
                //ako ne postoji objekt, pokušavamo još jednom selektirati primatelhe
                homePage.doSelectBeneficiaryList();
                //paySomeonePage = new PaySomeonePage(driver);
            }

            beneficiaryListPage.addBeneficiary(IBAN, identifierName, beneficiaryName);
            beneficiaryListPage.checkAddedBeneficiary(beneficiaryName, applicationPIN);

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

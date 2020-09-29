package com.ivs.tests;

import com.ivs.pages.*;
import com.ivs.util.ExcelUtil;
import com.ivs.util.Utils;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class HRMobileCorporatePaySomeoneOffLimitsTest extends BaseTest {

    private Object [][] arrInputParams;
    private int intBrojac = 0, columns = 14;
    String text;



    @DataProvider(name = "testData")
    public Object[][] testData() {

        ExcelUtil objData = new ExcelUtil();
        arrInputParams = objData.GetParameters("HRMobileCorporatePaySomeoneOffLimitsInputParameters.xlsx","Input1",columns);
        return arrInputParams;
    }
    @Test(dataProvider = "testData")
    public void paySomeoneOffLimitsTest(Object [] objInput) throws InterruptedException {
        MobileElement element;
        SoftAssert soft = new SoftAssert();
        driver = pageGen.getDriver();
        String companyName = objInput[0].toString();
        String payerIBANInput = objInput[1].toString();
        String payeeNameInput = objInput[2].toString();
        String payeeIBANInput = objInput[3].toString();
        String amountInput = objInput[4].toString();
        String debitRef1Input = objInput[5].toString();
        String debitRef2Input = objInput[6].toString();
        String creditRef1Input = objInput[7].toString();
        String creditRef2Input = objInput[8].toString();
        String paymentDescriptionInput = objInput[9].toString();
        WebDriverWait wait = new WebDriverWait(driver, 10);

        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        LoginPage loginPage = new LoginPage(driver);
        try {
            loginPage.loginAndEnterPIN(applicationPIN,language);
            ClientSelectPage clientSelectPage = new ClientSelectPage(driver);
            HomePage homePage = new HomePage(driver);
            AccountsPage accountsPage = new AccountsPage(driver);
            driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
            homePage.doSelectAccounts(wait);
            accountsPage.chooseAccount(payerIBANInput,wait);
            //TODO accounts odabrat option za otvorit racun i povuc podatke
            //homePage.doSelectPaymentAndPaySomeone(wait);
        } catch (Exception e) {

            arrInputParams[intBrojac][10] = "NOK";
            arrInputParams[intBrojac][13] = e.toString();
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
        objData.SaveParameters("HRMobileCorporatePaySomeoneOffLimitsInputParameters.xlsx","Input1",arrInputParams,columns,4,"android");
        driver.quit();
    }
}

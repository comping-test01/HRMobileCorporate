package com.ivs.tests;


import com.ivs.pages.*;
import com.ivs.util.ExcelUtil;
import com.ivs.util.Utils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import com.ivs.pages.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.util.concurrent.TimeUnit;


public class HRMobileCorporatePaySomeoneTest extends BaseTest {


    private Object [][] arrInputParams;
    private int intBrojac = 0;
    String text;



    @DataProvider(name = "testData")
    public Object[][] testData() {

        ExcelUtil objData = new ExcelUtil();
        arrInputParams = objData.GetParameters("PaySomeoneInputParameters.xlsx","Input1",16);
        return arrInputParams;
    }

    @Test(dataProvider = "testData")
    public void testPaySomeone(Object [] objInput) throws InterruptedException {
        MobileElement element;
        SoftAssert soft = new SoftAssert();
        driver = pageGen.getDriver();
        Utils utils = new Utils(driver);
        String testType = objInput[0].toString();
        String payerNameInput = objInput[1].toString();
        String payerIBANInput = objInput[2].toString();
        String payeeNameInput = objInput[3].toString();
        String payeeIBANInput = objInput[4].toString();
        String amountInput = objInput[5].toString();
        String dateInput = objInput[6].toString();
        String debitRef1Input = objInput[7].toString();
        String debitRef2Input = objInput[8].toString();
        String creditRef1Input = objInput[9].toString();
        String creditRef2Input = objInput[10].toString();
        String paymentDescriptionInput = objInput[11].toString();



        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        LoginPage loginPage = new LoginPage(driver);
        HomePage homePage = null;

        try {
            loginPage.loginAndEnterPIN(applicationPIN);
            homePage = new HomePage(driver);

            //homePage.skipSettingMainCompany();

            //slijedeće je odabir poslovnog subjekta ILI početna
            String xpathString = "//*[@text='Početna'] | " +
                                 "//*[@text='Odabir poslovnog subjekta' and @class='android.view.View']";

            element = utils.fluentWaitforElement(By.xpath(xpathString), 60, 1);

            if (isElementPresent(By.xpath("//*[@text='Odabir poslovnog subjekta' and @class='android.view.View']"), driver)) {
                //homePage.runHomeMenu();
                //homePage.doChangeCompanyClick();
                ClientSelectPage clientSelectPage = new ClientSelectPage(driver);
                clientSelectPage.doSearchAndSelectClient(payerNameInput);
            }

            element = utils.fluentWaitforElement(By.xpath("//*[@text='Dobrodošli, ']"), 60, 2);
            text = element.getText();
            Assert.assertEquals(text, "Dobrodošli, ");
            //Assert.assertNotNull(homePage.getHomePageHeader());

            //provjera je li potrebna promjena subjekta //*[@text='APOLLO HR D.O.O.']

            if (!(isElementPresent(By.xpath("//*[@text='" + payerNameInput + "' and @class='android.widget.TextView']"), driver))) {
                System.out.println("Ponovni odabir poslovnog subjekta ...:" + payerNameInput);
                homePage.runHomeMenu();
                homePage.doChangeCompanyClick();
                ClientSelectPage clientSelectPage = new ClientSelectPage(driver);
                clientSelectPage.doSearchAndSelectClient(payerNameInput);

            }


            homePage.doSelectPaymentAndPaymentSubItem2();
            PaySomeonePage paySomeonePage = new PaySomeonePage(driver);

            if (!paySomeonePage.checkIfOdaberiDrugiRacunExists()){
                //ako ne postoji objekt, pokušavamo još jednom selektirati plaćanje
                homePage.doSelectPaymentSubItem();
                paySomeonePage = new PaySomeonePage(driver);
            }


            BeneficiaryListPage beneficiaryListPage = new BeneficiaryListPage(driver);

            switch (testType.toUpperCase()) {
                case "FROM BENEFICIARY":
                   paySomeonePage.setPayerData(payerNameInput,payerIBANInput);
                    paySomeonePage.showBeneficiaryList();
                    beneficiaryListPage.doSearchAndSelectBeneficiary(payeeNameInput,payeeIBANInput);

                    break;

                default:
                    paySomeonePage.setPayerData(payerNameInput,payerIBANInput);
                    paySomeonePage.setPayeeData(payeeNameInput,payeeIBANInput);
                    break;
            }

            paySomeonePage.doFillPaySomeoneForm(amountInput,dateInput,debitRef1Input, debitRef2Input,creditRef1Input,creditRef2Input,paymentDescriptionInput);
            paySomeonePage.doVerifyData();

            //nextButton.click();


            String strBrojAutorizacije = "";
            String referenceID = "";
            String strExpectedText = "";

            //System.out.println("Test type je: " + testType);

            switch (testType.toUpperCase()) {
                case "AUTHORIZE&SEND":
                case "FROM BENEFICIARY":
                    paySomeonePage.doAuthorizeAndSend(applicationPIN);
                    strExpectedText = "NALOG USPJEŠNO POSLAN NA IZVRŠENJE. MOLIMO PROVJERITE STATUS U LISTI NALOGA.!";
                    strBrojAutorizacije = paySomeonePage.getAuthorizationID();
                    paySomeonePage.verifyPaymentResultMessage(strExpectedText);
                    referenceID = paySomeonePage.getRefrenceID();
                    paySomeonePage.finishPaySomeone();
                    break;

                case "AUTHORIZE":
                    paySomeonePage.doAuthorize();
                    strExpectedText = "NALOG USPJEŠNO POSLAN I AUTORIZIRAN TE SE NALAZI U AUTORIZACIJI.!";
                    loginPage.enterPIN(applicationPIN);
                    paySomeonePage.verifyPaymentResultMessage(strExpectedText);
                    strBrojAutorizacije = paySomeonePage.getAuthorizationID();
                    referenceID = paySomeonePage.getRefrenceID();
                    paySomeonePage.finishPaySomeone();
                    break;

                case "SAVE":
                    paySomeonePage.doSaveForLater();
                    strExpectedText = "NALOG USPJEŠNO SPREMLJEN I NALAZI SE U LISTI NALOGA, U PRIPREMI.!";
                    paySomeonePage.verifyPaymentResultMessage(strExpectedText);
                    paySomeonePage.finishPaySomeone();
                    break;

                case "LOAD":
                    paySomeonePage.doUpload();
                    strExpectedText = "NALOG USPJEŠNO UČITAN I NALAZI SE U AUTORIZACIJI.!";
                    paySomeonePage.verifyPaymentResultMessage(strExpectedText);
                    referenceID = paySomeonePage.getRefrenceID();
                    paySomeonePage.finishPaySomeone();
                    break;

                case "OFF LIMITS":
                    paySomeonePage.verifyOffLimitsMessage("Iskorišten dnevni limit.");
                    //if (blnResult==false){
                    //    throw new Exception("Nije pronađena poruka za off limits. Provjerite je li iznos > 500000!");
                    //}
                    break;

                case "FOREIGN ACCOUNT":
                    strExpectedText = "NEISPRAVAN UNOS PODATAKA. MOLIMO UNESITE OVAJ TIP NALOGA KROZ PLAĆANJA U STRANOJ VALUTI.";
                    paySomeonePage.verifyForForeignAccountTopScreenErorMessage(strExpectedText);
                    break;
                default:
                    throw new Exception("Invalid test type (" + testType + ") in input excel (row: " + (intBrojac + 1) + ") !");
            }

            System.out.println("BrojAutorizacije = " + strBrojAutorizacije);
            System.out.println("ReferenceID = " + referenceID);

            arrInputParams[intBrojac][12] = "OK";
            arrInputParams[intBrojac][13] = "'" + strBrojAutorizacije;
            arrInputParams[intBrojac][14] = "'" + referenceID;
            arrInputParams[intBrojac][15] = "";

            intBrojac++;
            soft.assertTrue(true);
            soft.assertAll();


        } catch (Exception e) {

            arrInputParams[intBrojac][12] = "NOK";
            arrInputParams[intBrojac][13] = "";
            arrInputParams[intBrojac][14] = "";
            arrInputParams[intBrojac][15] = e.toString();
            e.printStackTrace();


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
         objData.SaveParameters("PaySomeoneInputParameters.xlsx","Input1",arrInputParams,16,4,"android");

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
    }





    static boolean isElementPresent(By by, AppiumDriver d)
    {
        d.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        boolean exists = d.findElements(by).size() != 0;
        d.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        return exists;
    }
}

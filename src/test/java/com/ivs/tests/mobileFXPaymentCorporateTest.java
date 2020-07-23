package com.ivs.tests;

import com.ivs.pages.ClientSelectPage;
import com.ivs.pages.ForeignPaymentPage;
import com.ivs.pages.HomePage;
import com.ivs.pages.LoginPage;
import com.ivs.util.ExcelUtil;
import com.ivs.util.Utils;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.concurrent.TimeUnit;

public class mobileFXPaymentCorporateTest extends BaseTest {

    private Object[][] arrInputParams;
    private int intBrojac = 0;
    private int columns = 13;
    String text;


    @DataProvider(name = "testData")
    public Object[][] testData() {

        ExcelUtil objData = new ExcelUtil();
        arrInputParams = objData.GetParameters("ForeignPaymentInputParameters.xlsx", "Input1", columns);
        return arrInputParams;
    }

    @Test(dataProvider = "testData")
    public void ForeignPaymentTest(Object[] objInput) throws InterruptedException {
        MobileElement element;
        SoftAssert soft = new SoftAssert();
        driver = pageGen.getDriver();
        Utils utils = new Utils(driver);
        String testType = objInput[0].toString();
        String payerNameInput = objInput[1].toString();
        String payerIBANInput = objInput[2].toString();
        String payeeNameInput = objInput[3].toString();
        String payeeIBANInput = objInput[4].toString();
        String currencyInput = objInput[5].toString();
        String amountInput = objInput[6].toString();
        String dateInput = objInput[7].toString();
        String paymentDescriptionInput = objInput[8].toString();

        String strBrojAutorizacije = null;
        String referenceID = null;
        String strExpectedText = null;


        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        LoginPage loginPage = new LoginPage(driver);

        HomePage homePage = null;

        try {
            loginPage.loginAndEnterPIN(applicationPIN,language);
            homePage = new HomePage(driver);


            if (Utils.isElementPresent(By.xpath("//*[@text='Odabir poslovnog subjekta' and @class='android.view.View']"), driver)) {
                //homePage.runHomeMenu();
                //homePage.doChangeCompanyClick();
                ClientSelectPage clientSelectPage = new ClientSelectPage(driver);
                clientSelectPage.doSearchAndSelectClient(payerNameInput);

            }

            element = utils.fluentWaitforElement(By.xpath("//*[@text='Dobrodošli, ']"), 90, 2);
            text = element.getText();
            Assert.assertEquals(text, "Dobrodošli, ");
            //Assert.assertNotNull(homePage.getHomePageHeader());


            if (!(Utils.isElementPresent(By.xpath("//*[@text='" + payerNameInput + "' and @class='android.widget.TextView']"), driver))) {
                //System.out.println("Ponovni odabir poslovnog subjekta ...:" + payerNameInput);
                homePage.runHomeMenu();
                homePage.doChangeCompanyClick();
                ClientSelectPage clientSelectPage = new ClientSelectPage(driver);
                clientSelectPage.doSearchAndSelectClient(payerNameInput);

            }

            try {
                element = utils.fluentWaitforElement(By.xpath("//*[@text='Dobrodošli, ']"), 90, 2);
                homePage.doSelectPaymentAndFXPaymentSubItem();
            } catch (Exception e) {
                System.out.println("Nije kliknuo na Plaćanje - Plaćanje u stranoj valuti - TODO ...");
                if (utils.isElementPresent(By.id("menu-item-FOREIGN_PAYMENT"),driver)){
                    //ako je prikazan izbornik selektiramo samo subitem
                    homePage.doSelectFXPaymentSubItem();
                } else {
                    homePage.doSelectPaymentAndFXPaymentSubItem();
                }
                e.printStackTrace();
            }

            ForeignPaymentPage foreignPaymentPage = new ForeignPaymentPage(driver);
            foreignPaymentPage.setPayerData(payerIBANInput);
            foreignPaymentPage.setPayeeData(payeeIBANInput, payeeNameInput);
            foreignPaymentPage.doSelectCurrency(currencyInput);

            foreignPaymentPage.doProceedAndVerifyInputs(testType);

            if (testType.equalsIgnoreCase("Local currency & country")) {
                foreignPaymentPage.verifyLocalCurrencyMessage("UNESENI PODACI NE ODGOVARAJU OVOM TIPU PLAĆANJA, NALOG UNESITE KROZ IZBORNIK PLAĆANJA.");

            } else {

            foreignPaymentPage.doFillAmount(amountInput);
            if (!dateInput.equals("")) foreignPaymentPage.setDate(dateInput);
            foreignPaymentPage.doFillDescription(paymentDescriptionInput);

            foreignPaymentPage.clickNextButton();

            switch (testType.toUpperCase()) {
                case "SWIFT":
                case "FUTURE DATE":
                case "AUTHORIZE&SEND":
                    foreignPaymentPage.doAuthorizeAndSend();
                    strExpectedText = "NALOG USPJEŠNO POSLAN NA IZVRŠENJE. MOLIMO PROVJERITE STATUS U LISTI NALOGA.!";
                    foreignPaymentPage.verifyPaymentResultMessage(strExpectedText);
                    strBrojAutorizacije = foreignPaymentPage.getAuthorizationID();
                    referenceID = foreignPaymentPage.getRefrenceID();
                    break;

                case "NATIONAL FX":
                case "INTERNAL FX":
                    foreignPaymentPage.doUpload();
                    strExpectedText = "NALOG USPJEŠNO UČITAN I NALAZI SE U AUTORIZACIJI.!";
                    foreignPaymentPage.verifyPaymentResultMessage(strExpectedText);
                    referenceID = foreignPaymentPage.getRefrenceID();
                    break;

                case "AUTHORIZE":
                    foreignPaymentPage.doAuthorize();
                    strExpectedText = "NALOG USPJEŠNO POSLAN I AUTORIZIRAN TE SE NALAZI U AUTORIZACIJI.!";
                    foreignPaymentPage.verifyPaymentResultMessage(strExpectedText);
                    strBrojAutorizacije = foreignPaymentPage.getAuthorizationID();
                    referenceID = foreignPaymentPage.getRefrenceID();
                    break;

                case "SAVE":
                    foreignPaymentPage.doSave();
                    strExpectedText = "NALOG USPJEŠNO SPREMLJEN I NALAZI SE U LISTI NALOGA, U PRIPREMI.!";
                    foreignPaymentPage.verifyPaymentResultMessage(strExpectedText);
                    break;

                case "UPLOAD":
                    foreignPaymentPage.doUpload();
                    strExpectedText = "NALOG USPJEŠNO UČITAN I NALAZI SE U AUTORIZACIJI.!";
                    foreignPaymentPage.verifyPaymentResultMessage(strExpectedText);
                    referenceID = foreignPaymentPage.getRefrenceID();
                    break;
                case "OFF LIMITS":
                    foreignPaymentPage.verifyOffLimitsMessage("ISKORIŠTEN DNEVNI LIMIT.");
                    break;
                default:
                    throw new Exception("Invalid test name (" + objInput[0].toString() + ") in input excel (row: " + (intBrojac + 1) + ") !");
            }
        }

                arrInputParams[intBrojac][9] = "OK";
                System.out.println("BrojAutorizacije = " + strBrojAutorizacije);
                arrInputParams[intBrojac][10] = "'" + strBrojAutorizacije;
                System.out.println("ReferenceID = " + referenceID);
                arrInputParams[intBrojac][11] = "'" + referenceID;
                arrInputParams[intBrojac][12] = "";

                intBrojac++;
                soft.assertTrue(true);
                soft.assertAll();


            } catch(Exception e){
                arrInputParams[intBrojac][9] = "NOK";
                arrInputParams[intBrojac][10] = "";
                arrInputParams[intBrojac][11] = "";
                arrInputParams[intBrojac][12] = e.toString();
                System.out.println("Exception in test case execution: " + e.getMessage());

                homePage.doLogOut();
                intBrojac++;
                soft.assertTrue(false);
                soft.assertAll();
            }


        }


        @AfterSuite
        public void closeOut () {

            ExcelUtil objData = new ExcelUtil();
            objData.SaveParameters("ForeignPaymentInputParameters.xlsx", "Input1", arrInputParams, columns, 4, "android");
            driver.quit();

        }

    }


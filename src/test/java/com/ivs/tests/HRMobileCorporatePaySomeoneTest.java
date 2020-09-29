package com.ivs.tests;

import com.ivs.pages.*;
import com.ivs.testrail.TestRailCase;
import com.ivs.util.JSONDataProvider;
import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.math.BigDecimal;
import java.util.regex.Pattern;


public class HRMobileCorporatePaySomeoneTest extends BaseTest {


    String authNumber;
    String refNumber;
    String startBalance;
    String endBalance;

    BigDecimal startBalanceDouble;
    BigDecimal endBalanceDouble;
    BigDecimal amountInputDouble;

    String payerNameInput;
    String payerIBANInput;
    String payeeNameInput;
    String payeeIBANInput;
    String amountInput;
    String dateInput;
    String debitRef1Input;
    String debitRef2Input;
    String creditRef1Input;
    String creditRef2Input;
    String paymentDescriptionInput;

    private void setInputParameters(JSONObject testData) {
        authNumber = "";
        refNumber = "";
        expectedText = null;
        startBalance = "";
        endBalance= "";

        startBalanceDouble = null;
        endBalanceDouble = null;
        amountInputDouble = null;
        payerNameInput = setSingleParameter("payerNameInput", testData);
        payerIBANInput = setSingleParameter("payerIBANInput", testData);
        payeeNameInput = setSingleParameter("payeeNameInput", testData);
        payeeIBANInput = setSingleParameter("payeeIBANInput", testData);
        amountInput = setSingleParameter("amountInput", testData);
        dateInput = setSingleParameter("dateInput", testData);
        debitRef1Input = setSingleParameter("debitRef1Input", testData);
        debitRef2Input = setSingleParameter("debitRef2Input", testData);
        creditRef1Input = setSingleParameter("creditRef1Input", testData);
        creditRef2Input = setSingleParameter("creditRef2Input", testData);
        paymentDescriptionInput = setSingleParameter("paymentDescriptionInput", testData);

        amountInput = (language.equals("en")) ? amountInput.replace(",", ".") : amountInput;
    }

    @TestRailCase(testRailId = 12694)
    @Test(groups={"PaySomeone"}, dataProvider="fetchData_JSON", dataProviderClass= JSONDataProvider.class, enabled=true)
    public void paySomeoneUploadTest(String rowID, String description, JSONObject testData) throws InterruptedException {

        setInputParameters(testData);
        setResources();

        try {
            loginToApplication(3);
            HomePage homePage = new HomePage(driver);
            homePage.verifyLoginSuccess();

            switchToWebView();
            WebDriverWait wait = new WebDriverWait(driver, 20);
            AccountsPage accountsPage = new AccountsPage(driver);
            String startBalance = accountsPage.checkAccountBalance(payerIBANInput);
            switchToWebView();
            PaySomeonePage paySomeonePage = new PaySomeonePage(driver);
            wait.until(ExpectedConditions.elementToBeClickable(By.name("ic-menu"))).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.id("menu-item-PAYMENTS"))).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.id("menu-item-NEW_KUNA_PAYMENT_ORDER"))).click();
            wait.until(ExpectedConditions.textMatches(By.className("title-default"), Pattern.compile("Plaćanje")));
            paySomeonePage.setPayerData(payerIBANInput);
            paySomeonePage.setPayeeData(payeeNameInput,payeeIBANInput,amountInput);
            paySomeonePage.doFillPaySomeoneForm(amountInput,dateInput,debitRef1Input, debitRef2Input,creditRef1Input,creditRef2Input,paymentDescriptionInput);
            paySomeonePage.doProceed();
            paySomeonePage.doUpload();

            expectedText = "NALOG USPJEŠNO UČITAN I NALAZI SE U AUTORIZACIJI.!";
            refNumber = paySomeonePage.getRefrenceID();
            switchToWebView();
            paySomeonePage.verifyPaymentResultMessage(expectedText);

            String endBalance = accountsPage.checkAccountBalance(payerIBANInput);
            Assert.assertEquals(startBalance,endBalance,"For paysomeone - upload - account start balance should match end balance.");

        } catch (Exception e) {
            Assert.fail("Exception in test:" + e.getMessage());
            e.printStackTrace();


        }


    }

    @TestRailCase(testRailId = 12695)
    @Test(groups={"PaySomeone"}, dataProvider="fetchData_JSON", dataProviderClass= JSONDataProvider.class, enabled=true)
    public void paySomeoneAuthorizeTest(String rowID, String description, JSONObject testData) throws InterruptedException {

        setInputParameters(testData);
        setResources();

        try {
            loginToApplication(3);
            HomePage homePage = new HomePage(driver);
            homePage.verifyLoginSuccess();
            //setUpCompany(homePage, payerNameInput);

            switchToWebView();
            WebDriverWait wait = new WebDriverWait(driver, 20);
            AccountsPage accountsPage = new AccountsPage(driver);
            String startBalance = accountsPage.checkAccountBalance(payerIBANInput);
            switchToWebView();
            PaySomeonePage paySomeonePage = new PaySomeonePage(driver);
            wait.until(ExpectedConditions.elementToBeClickable(By.name("ic-menu"))).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.id("menu-item-PAYMENTS"))).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.id("menu-item-NEW_KUNA_PAYMENT_ORDER"))).click();
            wait.until(ExpectedConditions.textMatches(By.className("title-default"), Pattern.compile("Plaćanje")));
            paySomeonePage.setPayerData(payerIBANInput);
            paySomeonePage.setPayeeData(payeeNameInput,payeeIBANInput,amountInput);
            paySomeonePage.doFillPaySomeoneForm(amountInput,dateInput,debitRef1Input, debitRef2Input,creditRef1Input,creditRef2Input,paymentDescriptionInput);
            paySomeonePage.doProceed();
            paySomeonePage.doAuthorize();
            LoginPage loginPage = new LoginPage(driver);
            loginPage.enterPIN(applicationPIN);

            expectedText = "NALOG USPJEŠNO POSLAN I AUTORIZIRAN TE SE NALAZI U AUTORIZACIJI.!";
            refNumber = paySomeonePage.getRefrenceID();
            authNumber = paySomeonePage.getAuthorizationID();
            switchToWebView();
            paySomeonePage.verifyPaymentResultMessage(expectedText);

            String endBalance = accountsPage.checkAccountBalance(payerIBANInput);
            Assert.assertEquals(startBalance,endBalance,"For paysomeone - authorize - account start balance should match end balance.");

        } catch (Exception e) {
            Assert.fail("Exception in test:" + e.getMessage());
            e.printStackTrace();


        }


    }

    @TestRailCase(testRailId = 12693)
    @Test(groups={"PaySomeone"}, dataProvider="fetchData_JSON", dataProviderClass= JSONDataProvider.class, enabled=true)
    public void paySomeoneSaveTest(String rowID, String description, JSONObject testData) throws InterruptedException {

        setInputParameters(testData);
        setResources();

        try {
            loginToApplication(3);
            HomePage homePage = new HomePage(driver);
            homePage.verifyLoginSuccess();

            switchToWebView();
            WebDriverWait wait = new WebDriverWait(driver, 20);
            AccountsPage accountsPage = new AccountsPage(driver);
            String startBalance = accountsPage.checkAccountBalance(payerIBANInput);
            switchToWebView();
            PaySomeonePage paySomeonePage = new PaySomeonePage(driver);
            wait.until(ExpectedConditions.elementToBeClickable(By.name("ic-menu"))).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.id("menu-item-PAYMENTS"))).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.id("menu-item-NEW_KUNA_PAYMENT_ORDER"))).click();
            wait.until(ExpectedConditions.textMatches(By.className("title-default"), Pattern.compile("Plaćanje")));
            paySomeonePage.setPayerData(payerIBANInput);
            paySomeonePage.setPayeeData(payeeNameInput,payeeIBANInput,amountInput);
            paySomeonePage.doFillPaySomeoneForm(amountInput,dateInput,debitRef1Input, debitRef2Input,creditRef1Input,creditRef2Input,paymentDescriptionInput);
            paySomeonePage.doProceed();
            paySomeonePage.doSave();

            expectedText = "NALOG USPJEŠNO SPREMLJEN I NALAZI SE U LISTI NALOGA, U PRIPREMI.!";
            switchToWebView();
            paySomeonePage.verifyPaymentResultMessage(expectedText);

            String endBalance = accountsPage.checkAccountBalance(payerIBANInput);
            Assert.assertEquals(startBalance,endBalance,"For paysomeone - save - account start balance should match end balance.");

        } catch (Exception e) {
            Assert.fail("Exception in test:" + e.getMessage());
            e.printStackTrace();


        }


    }

    @TestRailCase(testRailId = 12698)
    @Test(groups={"PaySomeone"}, dataProvider="fetchData_JSON", dataProviderClass= JSONDataProvider.class, enabled=true)
    public void paySomeoneOffLimitsTest(String rowID, String description, JSONObject testData) throws InterruptedException {

        setInputParameters(testData);
        setResources();

        try {
            loginToApplication(3);
            HomePage homePage = new HomePage(driver);
            homePage.verifyLoginSuccess();

            switchToWebView();
            WebDriverWait wait = new WebDriverWait(driver, 20);
            AccountsPage accountsPage = new AccountsPage(driver);
            String startBalance = accountsPage.checkAccountBalance(payerIBANInput);

            switchToWebView();
            PaySomeonePage paySomeonePage = new PaySomeonePage(driver);
            wait.until(ExpectedConditions.elementToBeClickable(By.name("ic-menu"))).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.id("menu-item-PAYMENTS"))).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.id("menu-item-NEW_KUNA_PAYMENT_ORDER"))).click();
            wait.until(ExpectedConditions.textMatches(By.className("title-default"), Pattern.compile("Plaćanje")));
            paySomeonePage.setPayerData(payerIBANInput);
            paySomeonePage.setPayeeData(payeeNameInput,payeeIBANInput,amountInput);
            paySomeonePage.doFillPaySomeoneForm(amountInput,dateInput,debitRef1Input, debitRef2Input,creditRef1Input,creditRef2Input,paymentDescriptionInput);
            paySomeonePage.doProceed();

            expectedText = "Iskorišten dnevni limit.";
            paySomeonePage.verifyErrorMessage(expectedText);
            switchToWebView();

            String endBalance = accountsPage.checkAccountBalance(payerIBANInput);
            Assert.assertEquals(startBalance,endBalance,"For paysomeone - off limits - account start balance should match end balance.");

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Exception in test:" + e.getMessage());
        }


    }

    @TestRailCase(testRailId = 13109)
    @Test(groups={"PaySomeone"}, dataProvider="fetchData_JSON", dataProviderClass= JSONDataProvider.class, enabled=true)
    public void paySomeoneForeignAccountTest(String rowID, String description, JSONObject testData) throws InterruptedException {

        setInputParameters(testData);
        setResources();

        try {
            loginToApplication(3);
            HomePage homePage = new HomePage(driver);
            homePage.verifyLoginSuccess();

            switchToWebView();
            WebDriverWait wait = new WebDriverWait(driver, 20);
            AccountsPage accountsPage = new AccountsPage(driver);
            String startBalance = accountsPage.checkAccountBalance(payerIBANInput);
            switchToWebView();
            PaySomeonePage paySomeonePage = new PaySomeonePage(driver);
            wait.until(ExpectedConditions.elementToBeClickable(By.name("ic-menu"))).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.id("menu-item-PAYMENTS"))).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.id("menu-item-NEW_KUNA_PAYMENT_ORDER"))).click();
            wait.until(ExpectedConditions.textMatches(By.className("title-default"), Pattern.compile("Plaćanje")));
            paySomeonePage.setPayerData(payerIBANInput);
            paySomeonePage.setPayeeData(payeeNameInput,payeeIBANInput,amountInput);
            paySomeonePage.doFillPaySomeoneForm(amountInput,dateInput,debitRef1Input, debitRef2Input,creditRef1Input,creditRef2Input,paymentDescriptionInput);
            paySomeonePage.doProceed();


            expectedText = "Neispravan unos podataka. Molimo unesite ovaj tip naloga kroz Plaćanja u stranoj valuti.";
            paySomeonePage.verifyErrorMessage(expectedText);
            switchToWebView();

            String endBalance = accountsPage.checkAccountBalance(payerIBANInput);
            Assert.assertEquals(startBalance,endBalance,"For paysomeone - foreign account - account start balance should match end balance.");


        } catch (Exception e) {
            Assert.fail("Exception in test:" + e.getMessage());
            e.printStackTrace();


        }


    }

}

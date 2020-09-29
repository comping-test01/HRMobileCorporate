package com.ivs.pages;

import com.ivs.util.Utils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static io.appium.java_client.touch.WaitOptions.waitOptions;
import static io.appium.java_client.touch.offset.PointOption.point;
import static java.time.Duration.ofMillis;


public class ForeignPaymentPage extends BasePage {


    public ForeignPaymentPage(AppiumDriver<MobileElement> driver) {
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    //@FindBy(xpath = "//*[@text='Plaćanja u stranoj valuti' and @class='android.view.View' and ./parent::*[(./preceding-sibling::* | ./following-sibling::*)[@class='android.view.View']]]")
    @FindBy(xpath = "(((((//*[@class='android.view.View' and ./parent::*[@class='android.view.View' and ./parent::*[@class='android.view.View' and (./preceding-sibling::* | ./following-sibling::*)[@id='content-wrapper']] and (./preceding-sibling::* | ./following-sibling::*)[@class='android.view.View']]]/*[@class='android.view.View'])[1]/*[@class='android.view.View'])[2]/*[@class='android.view.View'])[2]/*[@class='android.view.View'])[5]/*[@class='android.view.View' and ./*[@class='android.view.View' and ./*[./*[@text and @class='android.view.View']]]])[3]")
    MobileElement fxPageTitleText;

    @FindBy(xpath = "//*[@class='android.view.View' and ./*[./*[@text]] and ./*[./*[./*[@text and @class='android.widget.Button']]]]")
    MobileElement pageTitleElement;

    @FindBy(xpath = "//*[@text='ODABERI DRUGI RAČUN']")
    MobileElement selectAnotherAccountLink;

    @FindBy(xpath = "//*[@class='android.widget.EditText' and (./preceding-sibling::* | ./following-sibling::*)[@text='ic input search']]")
    MobileElement payerAccountSearch;



    @FindBy(xpath = "//*[@class='android.widget.EditText' and ./parent::*[(./preceding-sibling::* | ./following-sibling::*)[@text='Broj računa *']]]")
    MobileElement payeeIBAN;

    @FindBy(xpath = "//*[@text and @class='android.view.View' and (./preceding-sibling::* | ./following-sibling::*)[@class='android.view.View' and ./*[@class='android.view.View' and ./*[@class='android.view.View']]]]")
    MobileElement payeeIBANValidationMessage;

    @FindBy(xpath="//*[@text='ic acc number off']")
    MobileElement payeeIBANvalidateButton;

    @FindBy(xpath="//*[@text='ic account owner']")
    MobileElement beneficiarySelectButton;

    @FindBy(xpath="//*[@class='android.widget.EditText' and ./parent::*[(./preceding-sibling::* | ./following-sibling::*)[@text='Vlasnik računa *']]]")
    MobileElement payeeName;

    @FindBy(xpath="//*[@class='android.view.View' and ./*[@text='Valuta *']]")
    MobileElement currencySelectArrow;

    @FindBy(xpath="//*[@class='android.widget.EditText']")
                  //*[@class='android.widget.EditText' and ./parent::*[./parent::*[./parent::*[(./preceding-sibling::* | ./following-sibling::*)[./*[@class='android.view.View']]]]]]
    MobileElement currencySearch;

    @FindBy(xpath="//*[@class='android.view.View' and ./*[@class='android.view.View' and ./*[@class='android.view.View' and ./*[@text] and ./*[./*[@class='android.widget.Image']]]]]")
    MobileElement confirmCurrencySelect;

    //@FindBy(xpath="//*[@class='android.view.View' and ./*[@text='currency-conversion']]")
    @FindBy(xpath="//*[@class='android.view.View' and ./*[@class='android.widget.Image'] and ./parent::*[(./preceding-sibling::* | ./following-sibling::*)[./*[@class='android.view.View']]]]")
    MobileElement amountSelect;



    @FindBy(xpath="//*[@text='POTVRDI' and @class='android.widget.Button']")
    MobileElement amountConfirmButton;

    @FindBy(xpath="//*[@class='android.widget.Button' and @text='NASTAVI']")
    MobileElement nextButton;


    @FindBy(xpath ="//*[@text='Na datum']")
    MobileElement paymentDate;

    @FindBy(xpath ="//*[@text='ic calendar button']")
    MobileElement calendarIcon;

    @FindBy(xpath ="//*[@class='android.widget.EditText' and ./parent::*[(./preceding-sibling::* | ./following-sibling::*)[@text='Opis plaćanja*']]]")
    MobileElement paymentDescriptionField;


    @FindBy(xpath = "((//*[@class='android.view.View' and ./parent::*[@class='android.view.View' and ./parent::*[@id='content-wrapper']]]/*/*/*/*[@class='android.view.View' and ./parent::*[@class='android.view.View' and ./parent::*[@class='android.view.View'] and (./preceding-sibling::* | ./following-sibling::*)[@class='android.view.View']]])[9]/*/*[@text and @class='android.widget.Button'])[1]")
    MobileElement saveBtn;

    //@FindBy(xpath = "((//*[@class='android.view.View' and ./parent::*[@class='android.view.View' and ./parent::*[@id='content-wrapper']]]/*/*/*/*[@class='android.view.View' and ./parent::*[@class='android.view.View' and ./parent::*[@class='android.view.View'] and (./preceding-sibling::* | ./following-sibling::*)[@class='android.view.View']]])[8]/*/*[@text and @class='android.widget.Button'])[2]")
    @FindBy(xpath = "//*[@text='UČITAJ']")
    MobileElement uploadBtn;

    @FindBy(xpath = "((//*[@class='android.view.View' and ./parent::*[@class='android.view.View' and ./parent::*[@id='content-wrapper']]]/*/*/*/*[@class='android.view.View' and ./parent::*[@class='android.view.View' and ./parent::*[@class='android.view.View'] and (./preceding-sibling::* | ./following-sibling::*)[@class='android.view.View']]])[9]/*/*[@text and @class='android.widget.Button'])[3]")
    MobileElement authorizeBtn;

    @FindBy(xpath = "((//*[@class='android.view.View' and ./parent::*[@class='android.view.View' and ./parent::*[@id='content-wrapper']]]/*/*/*/*[@class='android.view.View' and ./parent::*[@class='android.view.View' and ./parent::*[@class='android.view.View'] and (./preceding-sibling::* | ./following-sibling::*)[@class='android.view.View']]])[9]/*/*[@text and @class='android.widget.Button'])[4]")
    MobileElement authorizeAndSendBtn;

    @FindBy(xpath = "(((//*[@class='android.view.View' and ./parent::*[@class='android.view.View' and ./parent::*[@id='content-wrapper']]]/*[@class='android.view.View'])[2]/*[@class='android.view.View'])[2]/*[@text and @class='android.view.View'])[1]")
    MobileElement paymentResultMessage;


    @FindBy(xpath ="//android.view.View[contains(@text,'Referenca')]")
    MobileElement referenceIDTxtField;

    //@FindBy(xpath ="(((//*[@class='android.view.View' and ./parent::*[@class='android.view.View' and ./parent::*[@id='content-wrapper']]]/*[@class='android.view.View'])[2]/*[@class='android.view.View'])[2]/*[@text and @class='android.view.View'])[2]")
    @FindBy(xpath ="//android.view.View[contains(@text,'Broj autorizacije')]")
    MobileElement authIDTxtField;

    @FindBy(xpath ="//*[@text and @class='android.view.View' and (./preceding-sibling::* | ./following-sibling::*)[@class='android.view.View' and ./*[@class='android.widget.Image'] and ./*[@class='android.view.View'] and ./*[@class='android.widget.TextView']]]")
    MobileElement offLimitMsgTxtField;

    @FindBy(xpath ="//*[@text and @class='android.view.View' and (./preceding-sibling::* | ./following-sibling::*)[@class='android.view.View' and ./*[@class='android.view.View']]]")
    MobileElement localCCYMsgTxtField;






    public void setPayeeData(String strPayeeIBAN){
        try {

            MobileElement element;
            Utils.fluentWaitforElement(driver,payeeIBAN,60,1);
            payeeIBAN.click();
            payeeIBAN.sendKeys(strPayeeIBAN);
            payeeIBANvalidateButton.click();
            Thread.sleep(2000);
            //System.out.println("Payee name: " + payeeName.getAttribute("text").toString());
           // if (payeeName.getAttribute("text").length() == 0){
          //      payeeName.sendKeys(strPayeeName);
           //8 }
            //wait.until((ExpectedCondition<Boolean>) driver -> payeeName.getAttribute("text").length() != 0);



        } catch (Exception e) {
            System.out.println("setPayeeData: " + e.getMessage() + ",line number: "+ e.getStackTrace()[0].getLineNumber() );
        }
    }


    public void setPayerData(String strPayerIBAN){

        try {
            /*System.out.println("U odaberi drugi racun");
            String xpath = "//*[@text='PLATITELJ']/following-sibling::android.view.View";
            MobileElement iban = fluentWaitforElement((By.xpath(xpath)),30,1);
            String txt = iban.getText();
            System.out.println(txt);

             */
            MobileElement element;

            element = fluentWaitforElement(By.xpath("//*[@text='ODABERI DRUGI RAČUN']"), 20, 2);
            element.click();
            MobileElement accountToClick = driver.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(new UiSelector().textContains(\"" + strPayerIBAN + "\"));"));
            accountToClick.click();

        }catch (Exception e) {
            System.out.println("setPayerData: " + e.getMessage());
        }

    }

    public void doSelectCurrency(String currency) throws  TimeoutException {
        currencySelectArrow.click();
        MobileElement elementToClick = driver.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(new UiSelector().text(\"" + currency + "\"));"));
        elementToClick.click();

    }


    public void doFillAmount(String amount) throws TimeoutException {
        fluentWaitforElement(amountSelect,30,2);
        amountSelect.click();
        formatInputAmount(amount);
        amountConfirmButton.click();
    }

    private void formatInputAmount(String strAmount){

        if (strAmount.indexOf(",") < 0) {
            //ako nema ',' potrebno je dodati 00 zbog ispravnog formatiranja upisa
            strAmount = strAmount + ",00";
        } else if (strAmount.indexOf(",") == 0) {
            strAmount = "0" + strAmount;
        }

        if (strAmount.length() - strAmount.indexOf(",") > 3) {
            //ako broj ima više od 2 decimale, potrebno je ostaviti 2 decimale
            strAmount = strAmount.substring(0, strAmount.indexOf(",") + 2);
        } else if (strAmount.length() - strAmount.indexOf(",") == 2) {
            //ako broj ima samo jednu decimalu
            strAmount = strAmount + "0";
        }

        //System.out.println("Final Iznos: " + strAmount);
        for (int i = 0; i < strAmount.length(); i++) {
            String c = strAmount.substring(i, i + 1);
            if (!(c.equals(","))) {
                //driver.findElement(By.xpath("//*[@text='" + c + "']")).click();
                //tapOnElement(driver.findElement(By.xpath("//*[@text='" + c + "']")));
                tapOnElement(fluentWaitforElement((By.xpath("//*[@text='" + c + "']")),3,1));
            }
        }

    }

    public void setDate(String strDate){

        String inputMonthName;
        System.out.println("Zadani datum je: " + strDate);

        try {
            calendarIcon.click();
            Calendar now = Calendar.getInstance();
            int nowMonth = now.get(Calendar.MONTH) + 1; // Note: zero based!
            Calendar cal = Calendar.getInstance();
            cal.setTime(new SimpleDateFormat("dd.MM.yyyy").parse(strDate));

            int inputDay = cal.get(Calendar.DAY_OF_MONTH);
            int inputMonth = cal.get(Calendar.MONTH) + 1;
            inputMonthName = Utils.getMonthNameHRV(inputMonth);

            int month1 = nowMonth;
            int month2 = nowMonth + 1;

            MobileElement month1El = null;
            MobileElement month2El = null;

            while (inputMonth > month1) {

                String monthName1 = Utils.getMonthNameHRV(month1);
                String monthName2 = Utils.getMonthNameHRV(month2);

                month1El = driver.findElement(By.xpath("//*[@text='" + monthName1 + "']"));
                month2El = driver.findElement(By.xpath("//*[@text='" + monthName2 + "']"));


                new TouchAction(driver)
                        .press(point(0, month2El.getLocation().getY()))
                        .waitAction(waitOptions(ofMillis(1000)))
                        .moveTo(point(0, month1El.getLocation().getY()))
                        .release().perform();
                month1++;
                month2++;
            }

            //pozicionirani smo na pravi mjesec
            MobileElement target = driver.findElementByXPath("//*[@text='" + inputMonthName + "']/following-sibling::*[@class='android.view.View'][contains(text(),'" + inputDay + "')]");
            tapOnElement(target);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doFillDescription(String description) throws InterruptedException {
        //boolean wait = fluentWaitforElement(proceedBtn,10,1);
        paymentDescriptionField.click();
        paymentDescriptionField.sendKeys(description);
    }

    public void clickNextButton() throws InterruptedException {
        MobileElement element = null;
        Thread.sleep(2000);
        fluentWaitforElement(nextButton, 60, 5);
        driver.hideKeyboard();
        Thread.sleep(500);
        nextButton.click();
    }


    public void doProceedAndVerifyInputs(String testType) throws Exception {
        MobileElement element = null;
        fluentWaitforElement(nextButton,60,5);
        nextButton.click();

        String xpathString = "//*[@text='Detalji plaćanja'] | " +
                             "//*[@text='Račun primatelja je zatvoren'] | " +
                             "//*[@text='Pogrešan račun primatelja']";

        if (!testType.equalsIgnoreCase("Local currency & country")) {
            element = fluentWaitforElement(By.xpath(xpathString), 30, 1);
            if (!element.getText().equals("Detalji plaćanja")) throw new Exception(element.getText());
        }

    }

    public void doSave() throws InterruptedException {
        boolean wait = fluentWaitforElement(saveBtn,60,5);
        saveBtn.click();
    }

    public void doUpload() throws InterruptedException {
        boolean wait = fluentWaitforElement(uploadBtn,60,5);
        uploadBtn.click();
    }

    public void doAuthorize() throws InterruptedException {
        boolean wait = fluentWaitforElement(authorizeBtn,60,5);
        authorizeBtn.click();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterPIN("789987");
    }

    public void doAuthorizeAndSend() throws InterruptedException {
        boolean wait = fluentWaitforElement(authorizeAndSendBtn,60,5);
        authorizeAndSendBtn.click();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterPIN("789987");

    }

    public void verifyPaymentResultMessage(String expectedText) {
        fluentWaitforElement(paymentResultMessage,30,1);
        System.out.println(paymentResultMessage.getText().toUpperCase());
        System.out.println(expectedText);
        Assert.assertEquals(paymentResultMessage.getText().toUpperCase(), expectedText);
    }

    public String getAuthorizationID(){
        String authID = "";
        fluentWaitforElement(authIDTxtField,30,1);
        authID = authIDTxtField.getText().substring(authIDTxtField.getText().indexOf(":") + 2);
        return authID;
    }

    public String getRefrenceID(){
        String referenceID = "";
        fluentWaitforElement(referenceIDTxtField,30,1);
        referenceID = referenceIDTxtField.getText().substring(referenceIDTxtField.getText().indexOf(":") + 2);
        return referenceID;
    }

    public String getError() throws InterruptedException {
        // boolean wait = fluentWaitforElement(uploadBtn,60,5);
        // return error.getText();
        return null;
    }

    public void verifyErrorMessage(String expectedText) throws InterruptedException {
        String err = getError();
        Assert.assertEquals(err, expectedText);
    }


    public void verifyOffLimitsMessage(String expectedText) throws Exception {
        System.out.println("U verify off limits message");
        fluentWaitforElement(offLimitMsgTxtField,30,1);
        System.out.println(offLimitMsgTxtField.getText().toUpperCase());
        System.out.println(expectedText);
        Assert.assertEquals(offLimitMsgTxtField.getText().toUpperCase(), expectedText);
        //*[@text='Iskorišten dnevni limit.']

    }

    public void verifyLocalCurrencyMessage(String expectedText) throws Exception {
        System.out.println("U verify local currency message");
        fluentWaitforElement(localCCYMsgTxtField,30,1);
        System.out.println(localCCYMsgTxtField.getText().toUpperCase());
        System.out.println(expectedText);
        Assert.assertEquals(localCCYMsgTxtField.getText().toUpperCase(), expectedText);
    }

    public void finishFXPaySomeone(){
        if (isElementPresent(By.xpath("//*[@text='Završi']"))) {
            driver.findElement(By.xpath("//*[@text='Završi']")).click();
        }

    }

    public void checkIfErrorMessageExists() throws Exception {
        boolean errorExists = fluentWaitforElementBoolean(By.xpath("//*[@text='Uneseni podaci ne odgovaraju ovom tipu plaćanja, Nalog unesite kroz izbornik Plaćanja.']"),10,1);
        if (errorExists){
            throw new Exception("Pogreška: 'Uneseni podaci ne odgovaraju ovom tipu plaćanja, Nalog unesite kroz izbornik Plaćanja.' !");
        }
    }
}

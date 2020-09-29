package com.ivs.pages;

import com.ivs.util.Utils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import static io.appium.java_client.touch.WaitOptions.waitOptions;
import static io.appium.java_client.touch.offset.PointOption.point;
import static java.time.Duration.ofMillis;

public class PaySomeonePage extends BasePage {

    public PaySomeonePage(AppiumDriver<MobileElement> driver) {
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }


    @FindBy(xpath = "//*[@text='ODABERI DRUGI RAČUN']")
    MobileElement selectAnotherAccountLink;

    @FindBy(xpath = "//*[@class='android.widget.EditText' and ./parent::*[(./preceding-sibling::* | ./following-sibling::*)[@text='Broj računa*']]]")
    MobileElement payeeIBAN;

    //@FindBy(xpath = "//*[@text and @class='android.view.View' and (./preceding-sibling::* | ./following-sibling::*)[@class='android.view.View' and ./*[@class='android.view.View' and ./*[@class='android.view.View']]]]")
    //@FindBy(xpath = "((((//*[@class='android.view.View' and ./parent::*[@class='android.view.View' and ./parent::*[@id='content-wrapper']]]/*[@class='android.view.View'])[2]/*[@class='android.view.View'])[2]/*[@class='android.view.View'])[5]/*[@text and @class='android.view.View'])[1]")
                         //*[@text and @class='android.view.View' and (./preceding-sibling::* | ./following-sibling::*)[@class='android.view.View' and ./*[@class='android.view.View' and ./*[@class='android.view.View']]]]
     //((((//*[@class='android.view.View' and ./parent::*[@class='android.view.View' and ./parent::*[@id='content-wrapper']]]/*[@class='android.view.View'])[2]/*[@class='android.view.View'])[2]/*[@class='android.view.View'])[5]/*[@text and @class='android.view.View'])[2]
    @FindBy(xpath ="//android.view.View[contains(@text,'Molimo unesite ovaj tip naloga kroz Plaćanja u stranoj valuti')]")
    MobileElement payeeIBANValidationMessage;

    @FindBy(xpath = "//*[@text='Pogrešan račun primatelja']")
    MobileElement payeeIBANValidationErrorMessage;

    @FindBy(xpath="//*[@text='ic acc number off']")
    MobileElement payeeIBANvalidateButton;

    @FindBy(xpath="//*[@text='ic account owner']")
    MobileElement beneficiarySelectButton;

    @FindBy(xpath ="//*[@class='android.widget.EditText' and ./parent::*[./parent::*[./parent::*[(./preceding-sibling::* | ./following-sibling::*)[./*[@class='android.view.View']]]]]]")
    MobileElement beneficiarySearch;

    @FindBy(xpath="//*[@class='android.widget.EditText' and ./parent::*[(./preceding-sibling::* | ./following-sibling::*)[@text='Vlasnik računa*']]]")
    MobileElement payeeName;

    @FindBy(xpath="//*[@class='android.view.View' and ./*[@class='android.view.View' and ./*[@text='Iznos*']]]")
    MobileElement amountField;

    @FindBy(xpath="//*[@class='android.widget.Button' and @text='NASTAVI']")
    MobileElement nextButton;

    @FindBy(xpath="//*[@text and @class='android.widget.EditText' and ./parent::*[(./preceding-sibling::* | ./following-sibling::*)[@text='Account owner*']]] | //*[@text and @class='android.widget.EditText' and ./parent::*[(./preceding-sibling::* | ./following-sibling::*)[@text='Vlasnik računa*']]]")
    MobileElement filledPayeeName;


    @FindBy(xpath ="//*[@text='Na datum']")
    MobileElement paymentDate;

    @FindBy(xpath ="//*[@class='android.widget.EditText' and ./parent::*[(./preceding-sibling::* | ./following-sibling::*)[@text='Opis plaćanja*']]]")
    WebElement paymentDescriptionField;

    @FindBy(xpath ="//*[@text='ic calendar button']")
    MobileElement calendarIcon;

    @FindBy(xpath ="//*[@id=\"content-wrapper\"]/ng-component/pay-someone-confirmation/ion-header/ion-toolbar[1]/ion-buttons[2]/ion-button//button")
    MobileElement finishButton;




/*
    @FindBy(id = "debitReference-part1")
    WebElement debitReferencePart1;

    @FindBy(id = "debitReference-part2")
    WebElement debitReferencePart2;

    @FindBy(id = "creditReference-part1")
    WebElement creditReferencePart1;

    @FindBy(id = "creditReference-part2")
    WebElement creditReferencePart2;

    @FindBy(id = "paysomeone-9")
    WebElement paymentDescription;



 */
    //@FindBy(xpath = "((//*[@class='android.view.View' and ./parent::*[@class='android.view.View' and ./parent::*[@id='content-wrapper']]]/*/*/*/*[@class='android.view.View' and ./parent::*[@class='android.view.View' and ./parent::*[@class='android.view.View'] and (./preceding-sibling::* | ./following-sibling::*)[@class='android.view.View']]])[9]/*/*[@text and @class='android.widget.Button'])[1]")
    @FindBy(xpath = "//*[@text='SPREMI']")
    MobileElement saveBtn;

    @FindBy(xpath = "//*[@text='UČITAJ']")
    MobileElement loadBtn;

    //@FindBy(xpath = "((//*[@class='android.view.View' and ./parent::*[@class='android.view.View' and ./parent::*[@id='content-wrapper']]]/*/*/*/*[@class='android.view.View' and ./parent::*[@class='android.view.View' and ./parent::*[@class='android.view.View'] and (./preceding-sibling::* | ./following-sibling::*)[@class='android.view.View']]])[9]/*/*[@text and @class='android.widget.Button'])[3]")
    @FindBy(xpath = "//*[@text='AUTORIZIRAJ']")
    MobileElement authorizeBtn;

    //@FindBy(xpath = "((//*[@class='android.view.View' and ./parent::*[@class='android.view.View' and ./parent::*[@id='content-wrapper']]]/*/*/*/*[@class='android.view.View' and ./parent::*[@class='android.view.View' and ./parent::*[@class='android.view.View'] and (./preceding-sibling::* | ./following-sibling::*)[@class='android.view.View']]])[9]/*/*[@text and @class='android.widget.Button'])[4]")
    @FindBy(xpath = "//*[@text='PLATI']")
    MobileElement payBtn;

    @FindBy(className = "ion-text-center")
    MobileElement paymentResultMessage;

    @FindBy(xpath ="//*[@text='VIŠE POLJA ic arrowdwn grey']")
    MobileElement moreFieldsBtn;


    //@FindBy(xpath ="(((//*[@class='android.view.View' and ./parent::*[@class='android.view.View' and ./parent::*[@id='content-wrapper']]]/*[@class='android.view.View'])[2]/*[@class='android.view.View'])[2]/*[@text and @class='android.view.View'])[3]")
    @FindBy(xpath ="//android.view.View[contains(@text,'Referenca')]")
    MobileElement referenceIDTxtField;

    //@FindBy(xpath ="(((//*[@class='android.view.View' and ./parent::*[@class='android.view.View' and ./parent::*[@id='content-wrapper']]]/*[@class='android.view.View'])[2]/*[@class='android.view.View'])[2]/*[@text and @class='android.view.View'])[2]")
    @FindBy(xpath ="//android.view.View[contains(@text,'Broj autorizacije')]")
    MobileElement authIDTxtField;


    public void doSelectPayer(String payerIBANinput){
        Utils.fluentWaitforElement(driver,selectAnotherAccountLink, 10, 2);
        selectAnotherAccountLink.click();
        driver.findElement(By.xpath("//*[@text='" + payerIBANinput + " - HRK']")).click();
        //Utils.fluentWaitforElement(driver,el, 10, 2);

        //driver.findElement(By.xpath("//*[@text='HR5023400091170010419 - HRK']")).click();
        //driver.findElement(By.xpath("//*[@text='HR2823400091170010427 - HRK']")).click();
    
    }

    public void setPayeeData(String strPayeeName, String strPayeeIBAN, String amount) throws Exception {
            amountField.click();
            formatInputAmount(amount);
            List<MobileElement> nextButtonsList = driver.findElements(By.xpath("//*[@class='android.widget.Button' and @text='NASTAVI']"));
            nextButtonsList.get(0).click();
            MobileElement element;
            Utils.fluentWaitforElement(driver,payeeIBAN,60,1);
            payeeIBAN.click();
            payeeIBAN.sendKeys(strPayeeIBAN);
            payeeIBANvalidateButton.click();
            Thread.sleep(2000);

        if (payeeName.getAttribute("text").length() == 0){
            payeeName.sendKeys(strPayeeName);
        }

    }

    public void setPayerData(String strPayerIBAN) throws InterruptedException {
            MobileElement element;
            wait.until(ExpectedConditions.elementToBeClickable(By.className("text-center"))).click();
            driver.context("NATIVE_APP");
            Thread.sleep(3000);
            strPayerIBAN = strPayerIBAN.concat(" - HRK");
            MobileElement elementToClick = driver.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(new UiSelector().textContains(\"" + strPayerIBAN + "\"));"));
            elementToClick.click();
    }



    public void doFillPaySomeoneForm(String strAmount, String strPaymentDate, String strDebitReference1, String strDebitReference2,
                                     String strCreditReference1, String strCreditReference2, String strPaymentDescription) throws InterruptedException {

        swipeToBottom();
        paymentDescriptionField.sendKeys(strPaymentDescription);
        //driver.pressKey(new KeyEvent(AndroidKey.ENTER));
        driver.hideKeyboard();
        //enterDebitReferences(strDebitReference1,strDebitReference2);
        //enterCreditReferences(strCreditReference1,strCreditReference2);
        //wait.until(ExpectedConditions.elementToBeClickable(calendarIcon)).click();

        //formatInputDate(strPaymentDate);
        //setDate(strPaymentDate);

    }


    private void enterDebitReferences(String strDebitReference1,String strDebitReference2){

        MobileElement element;
        element = this.fluentWaitforElement(By.xpath("//*[@text='Model i poziv na broj platitelja']"), 10, 2);
        element.click();
        element = this.fluentWaitforElement(By.xpath("//*[@class='android.widget.EditText' and ./parent::*[(./preceding-sibling::* | ./following-sibling::*)[@text='Model']]]"), 20, 2);
        element.click();
        element.sendKeys(strDebitReference1);
        ((AndroidDriver) driver).pressKey(new KeyEvent(AndroidKey.ENTER));

        element = this.fluentWaitforElement(By.xpath("//*[@class='android.widget.EditText' and ./parent::*[(./preceding-sibling::* | ./following-sibling::*)[@text='Referenca']]]"), 20, 2);
        element.click();
        element.sendKeys(strDebitReference2);
        ((AndroidDriver) driver).pressKey(new KeyEvent(AndroidKey.ENTER));
        driver.hideKeyboard();
        }

    private void enterCreditReferences(String strCreditReference1, String strCreditReference2){

        MobileElement element;
        element = this.fluentWaitforElement(By.xpath("//*[@text='Model i poziv na broj primatelja']"), 10, 2);
        element.click();
        element = this.fluentWaitforElement(By.xpath("//*[@class='android.widget.EditText' and ./parent::*[(./preceding-sibling::* | ./following-sibling::*)[@text='Model']]]"), 20, 2);
        element.click();
        element.sendKeys(strCreditReference1);
        ((AndroidDriver) driver).pressKey(new KeyEvent(AndroidKey.ENTER));

        element = this.fluentWaitforElement(By.xpath("//*[@class='android.widget.EditText' and ./parent::*[(./preceding-sibling::* | ./following-sibling::*)[@text='Referenca']]]"), 20, 2);
        element.click();
        element.sendKeys(strCreditReference2);
        ((AndroidDriver) driver).pressKey(new KeyEvent(AndroidKey.ENTER));
        driver.hideKeyboard();
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
                tapOnElement(driver.findElement(By.xpath("//*[@text='" + c + "']")));
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

            int month1Y = 0;
            int month2Y = 0;


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

            //new TouchAction(driver).tap(point(clickON.getLocation().getX(), clickON.getLocation().getY())).perform();

            String xpath = "(((((//*[@class='android.view.View' and ./parent::*[@class='android.view.View' and ./parent::*[@id='content-wrapper']]]/*[@class='android.view.View'])[2]/*[@class='android.view.View'])[2]/*[@class='android.view.View'])[10]/*[@class='android.view.View'])[1]/*[@text and @class='android.view.View'])[2]";
            MobileElement e = fluentWaitforElement(By.xpath(xpath),30,1);
            System.out.println("Odabrani datum je: " + e.getText());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void formatInputDate(String strDate) {

        String inputMonthName;
        String nowMonthName;
        String nowNextMonthName;
        String inputNextMonthName;
        List<MobileElement> dateElementToPickList;
        String path;
        MobileElement clickON = null;
        //System.out.println("Zadani datum je: " + strDate);

        try {
            //calendarIcon.click();
            Calendar now = Calendar.getInstance();
            int nowMonth = now.get(Calendar.MONTH) + 1; // Note: zero based!
            Calendar cal = Calendar.getInstance();
            cal.setTime(new SimpleDateFormat("dd.MM.yyyy").parse(strDate));

            int inputDay = cal.get(Calendar.DAY_OF_MONTH);
            int inputMonth = cal.get(Calendar.MONTH) + 1;

            int month1 = nowMonth;
            int month2 = nowMonth + 1;

            MobileElement month1El = null;
            MobileElement month2El = null;


            int month1Y = 0;
            int month2Y = 0;


            path = "//*[@text='" + inputDay + "']";

            while (inputMonth > month1) {

                String monthName1 = Utils.getMonthNameHRV(month1);
                String monthName2 = Utils.getMonthNameHRV(month2);

                month1El = driver.findElement(By.xpath("//*[@text='" + monthName1 + "']"));
                month2El = driver.findElement(By.xpath("//*[@text='" + monthName2 + "']"));
                month1Y = month1El.getLocation().getY();
                month2Y = month2El.getLocation().getY();

                    new TouchAction(driver)
                            .press(point(0, month2El.getLocation().getY()))
                            .waitAction(waitOptions(ofMillis(1000)))
                            .moveTo(point(0, month1El.getLocation().getY()))
                            .release().perform();
                    month1++;
                    month2++;

            }

            //pozicionirani smo na pravi mjesec
            dateElementToPickList = driver.findElements(By.xpath(path));
            if (dateElementToPickList.size() == 1 ) {

                clickON = dateElementToPickList.get(0);
            }
            else if (dateElementToPickList.size() == 3){  clickON = dateElementToPickList.get(1);}

            else {

                if ((dateElementToPickList.get(0).getLocation().getY() < month2Y) & (dateElementToPickList.get(0).getLocation().getY() > month1Y) & !(month2 == nowMonth+4)) {
                    clickON = dateElementToPickList.get(0);

                } else {
                    clickON = dateElementToPickList.get(1);
                }
            }
            tapOnElement(clickON);
            //new TouchAction(driver).tap(point(clickON.getLocation().getX(), clickON.getLocation().getY())).perform();

            //String xpath = "(((((//*[@class='android.view.View' and ./parent::*[@class='android.view.View' and ./parent::*[@id='content-wrapper']]]/*[@class='android.view.View'])[2]/*[@class='android.view.View'])[2]/*[@class='android.view.View'])[10]/*[@class='android.view.View'])[1]/*[@text and @class='android.view.View'])[2]";
            //MobileElement e = fluentWaitforElement(By.xpath(xpath),30,1);
            //System.out.println("Odabrani datum je: " + e.getText());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doProceed(){
        wait.until(ExpectedConditions.elementToBeClickable(nextButton)).click();
    }

    public void doAuthorizeAndSend(String pin) throws InterruptedException {
        boolean wait = fluentWaitforElement(payBtn,60,5);
        tapOnElement(payBtn);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterPIN(pin);
        fluentWaitforElement(paymentResultMessage, 30, 2);
    }

    public void doAuthorize() throws InterruptedException {
        boolean wait = fluentWaitforElement(authorizeBtn,60,5);
        tapOnElement(authorizeBtn);
    }

    public void doSave() throws InterruptedException {
        boolean wait = fluentWaitforElement(saveBtn,60,5);
        tapOnElement(saveBtn);

    }

    public void doUpload() throws InterruptedException {
        boolean wait = fluentWaitforElement(loadBtn,60,5);
        loadBtn.click();
    }

    public void verifyPaymentResultMessage (String expectedText) {
        wait.until(ExpectedConditions.visibilityOf(paymentResultMessage));
        //System.out.println("U verify result message");
        System.out.println(paymentResultMessage.getText().toUpperCase());
        System.out.println(expectedText);
        Assert.assertEquals(paymentResultMessage.getText().toUpperCase(), expectedText.toUpperCase());
        //wait.until(ExpectedConditions.visibilityOf(finishButton)).click();
    }

    public void verifyErrorMessage (String expectedText) throws InterruptedException {
        Thread.sleep(5000);
        driver.context("NATIVE_APP");
        scrollIntoView(expectedText);
    }

    public void verifyForForeignAccountTopScreenErorMessage (String expectedText) throws InterruptedException {
        //Assert.assertEquals(readText(paymentResultMessage).toUpperCase(), expectedText);
        Boolean errorMsgExist = fluentWaitforElement(payeeIBANValidationMessage, 30, 5);
        System.out.println(payeeIBANValidationMessage.getText());
        System.out.println(expectedText);
        if (errorMsgExist) {
            if (payeeIBANValidationMessage.getText().length() > 0) {
                Assert.assertEquals(payeeIBANValidationMessage.getText().toUpperCase(), expectedText.toUpperCase());
                //throw new Exception("Pogreška prilikom pokušaja plaćanja (" + topScreenErrorMessage.getText() + ") !");
            }
        }


    }

    public String getAuthorizationID(){
        String authID = "";
        fluentWaitforElement(authIDTxtField,30,1);
        authID = authIDTxtField.getText().substring(authIDTxtField.getText().indexOf(":") + 2);
        System.out.println(authID);
        return authID;
    }

    public String getRefrenceID(){
        String referenceID = "";
        //MobileElement e = driver.findElement(By.xpath("//android.view.View[contains(@text,'Referenca')]"));
        fluentWaitforElement(referenceIDTxtField,30,1);
        referenceID = referenceIDTxtField.getText().substring(referenceIDTxtField.getText().indexOf(":") + 2);
        System.out.println(referenceID);
        return referenceID;
    }

    public void showBeneficiaryList(){

        beneficiarySelectButton.click();
        Utils.fluentWaitforElement(driver,driver.findElement(By.xpath("//*[@text='Odaberite primatelja' and @top='true']")), 30, 5);
       String Actualtext = driver.findElement(By.xpath("//*[@text='Odaberite primatelja' and @top='true']")).getText();
        Assert.assertEquals(Actualtext, "Odaberite primatelja");


    }

    public void checkBeneficiary(String beneficiaryName,String IBAN, String identifierName, WebDriverWait wait) throws Exception {

        //WebDriverWait wait = new WebDriverWait(driver, 10);

        wait.until(ExpectedConditions.elementToBeClickable(beneficiarySelectButton)).click();
        beneficiarySelectButton.click();

        wait.until(ExpectedConditions.elementToBeClickable(beneficiarySearch)).click();
        beneficiarySearch.click();

        wait.until(ExpectedConditions.visibilityOf(beneficiarySearch)).click();
        beneficiarySearch.sendKeys(beneficiaryName);

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@class='android.widget.Button' and ./parent::*[@text='"+beneficiaryName+"']]"))).click();
        driver.findElement(By.xpath("//*[@class='android.widget.Button' and ./parent::*[@text='"+beneficiaryName+"']]")).click();

        /*DEBUG Potentially different xpath according to the application state
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@class='android.widget.Button' and ./parent::*[@text='"+IBAN+" "+identifierName+"']]"))).click();
        driver.findElement(By.xpath("//*[@class='android.widget.Button' and ./parent::*[@text='"+IBAN+" "+identifierName+"']]")).click();*/
        wait.until(ExpectedConditions.elementToBeClickable(filledPayeeName)).click();
        String identifierInPaySomeone = filledPayeeName.getText();
        if (identifierName.equalsIgnoreCase(identifierInPaySomeone)){

        } else{

            throw new RuntimeException("Wrong beneficiary identifier shown");

        }

    }
}


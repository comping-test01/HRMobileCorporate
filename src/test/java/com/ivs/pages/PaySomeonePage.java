package com.ivs.pages;

import com.ivs.util.Utils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

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

    @FindBy(xpath = "//*[@class='android.widget.EditText' and (./preceding-sibling::* | ./following-sibling::*)[@text='ic input search']]")
    MobileElement payerAccountSearch;

    @FindBy(xpath ="//*[@class='android.view.View' and ./*[@text='HR5023400091170010419 - HUF']]")
    MobileElement confirmClientSelect;


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

    @FindBy(xpath="//*[@class='android.widget.EditText' and ./parent::*[(./preceding-sibling::* | ./following-sibling::*)[@text='Vlasnik računa*']]]")
    MobileElement payeeName;

    @FindBy(xpath="//*[@class='android.view.View' and ./*[@text='Iznos*']]")
    MobileElement amountField;

    @FindBy(xpath="//*[@class='android.widget.Button' and @text='NASTAVI']")
    MobileElement nextButton;




    @FindBy(xpath ="//*[@text='Na datum']")
    MobileElement paymentDate;

    @FindBy(xpath ="//*[@class='android.widget.EditText' and ./parent::*[(./preceding-sibling::* | ./following-sibling::*)[@text='Opis plaćanja*']]]")
    WebElement paymentDescriptionField;

    @FindBy(xpath ="//*[@text='ic calendar button']")
    MobileElement calendarIcon;


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
    MobileElement saveForLaterBtn;

    @FindBy(xpath = "//*[@text='UČITAJ']")
    MobileElement loadBtn;

    //@FindBy(xpath = "((//*[@class='android.view.View' and ./parent::*[@class='android.view.View' and ./parent::*[@id='content-wrapper']]]/*/*/*/*[@class='android.view.View' and ./parent::*[@class='android.view.View' and ./parent::*[@class='android.view.View'] and (./preceding-sibling::* | ./following-sibling::*)[@class='android.view.View']]])[9]/*/*[@text and @class='android.widget.Button'])[3]")
    @FindBy(xpath = "//*[@text='AUTORIZIRAJ']")
    MobileElement authorizeBtn;

    //@FindBy(xpath = "((//*[@class='android.view.View' and ./parent::*[@class='android.view.View' and ./parent::*[@id='content-wrapper']]]/*/*/*/*[@class='android.view.View' and ./parent::*[@class='android.view.View' and ./parent::*[@class='android.view.View'] and (./preceding-sibling::* | ./following-sibling::*)[@class='android.view.View']]])[9]/*/*[@text and @class='android.widget.Button'])[4]")
    @FindBy(xpath = "//*[@text='PLATI']")
    MobileElement payBtn;

    @FindBy(xpath = "(((//*[@class='android.view.View' and ./parent::*[@class='android.view.View' and ./parent::*[@id='content-wrapper']]]/*[@class='android.view.View'])[2]/*[@class='android.view.View'])[2]/*[@text and @class='android.view.View'])[1]")
    MobileElement paymentResultMessage;

    @FindBy(xpath ="//*[@text='VIŠE POLJA ic arrowdwn grey']")
    MobileElement moreFieldsBtn;

    @FindBy(xpath ="//*[@text='Završi']")
    MobileElement finishButton;

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

    public boolean checkIfOdaberiDrugiRacunExists() throws InterruptedException {
        boolean exists = fluentWaitforElement(selectAnotherAccountLink,20,5);
        return exists;
    }

    public void setPayeeData(String strPayeeName, String strPayeeIBAN) throws Exception {
        try {

            MobileElement element;
            Utils.fluentWaitforElement(driver,payeeIBAN,60,1);
            payeeIBAN.click();
            payeeIBAN.sendKeys(strPayeeIBAN);
            payeeIBANvalidateButton.click();
            Thread.sleep(2000);
            //checking error message
        } catch (Exception e) {
            System.out.println("setPayeeData: " + e.getMessage());
        }

        /*boolean errorMessageExist= fluentWaitforElement(payeeIBANValidationErrorMessage,10,1);
        if (errorMessageExist){
            throw new Exception("Invalid strPayeeIBAN - error 'Pogrešan račun primatelja' (" + strPayeeIBAN + ") !");
        }
*/
        //System.out.println("Payee name: " + payeeName.getAttribute("text").toString());
        if (payeeName.getAttribute("text").length() == 0){
            payeeName.sendKeys(strPayeeName);
        }
        //wait.until((ExpectedCondition<Boolean>) driver -> payeeName.getAttribute("text").length() != 0);

    }

    public void setPayerData(String strPayerName, String strPayerIBAN){

        try {
            MobileElement element;
            element = this.fluentWaitforElement(By.xpath("//*[@text='ODABERI DRUGI RAČUN']"), 20, 2);
            element.click();

            //Utils.fluentWaitforElement(driver, payerAccountSearch,60,1);
            //payerAccountSearch.click();
            //payerAccountSearch.sendKeys(strPayerIBAN);
            driver.findElement(By.xpath("//*[@text='" + strPayerIBAN + " - HRK']")).click();


        }catch (Exception e) {
            System.out.println("setPayerData: " + e.getMessage());
        }

    }



    public void doFillPaySomeoneForm(String strAmount, String strPaymentDate, String strDebitReference1, String strDebitReference2,
                                     String strCreditReference1, String strCreditReference2, String strPaymentDescription){

    try {

        Utils.fluentWaitforElement(driver,amountField, 20, 2);
        amountField.click();
        formatInputAmount(strAmount);

        List<MobileElement> nextButtonsList = driver.findElements(By.xpath("//*[@class='android.widget.Button' and @text='NASTAVI']"));
        nextButtonsList.get(0).click();

        if ((nextButtonsList.size() > 1) && (Utils.isElementPresent(By.xpath("//*[@text='ic cancel']"), driver))) {
            try {
                nextButtonsList.get(1).click();
            } catch (Exception e2) {
                boolean test=true;
            }
        }

        paymentDescriptionField.click();
        paymentDescriptionField.sendKeys(strPaymentDescription);
        //driver.pressKey(new KeyEvent(AndroidKey.ENTER));
        driver.hideKeyboard();
        enterDebitReferences(strDebitReference1,strDebitReference2);
        enterCreditReferences(strCreditReference1,strCreditReference2);

        //formatInputDate(strPaymentDate);
        setDate(strPaymentDate);

        //to get on main page after calendar picker
        MobileElement nextButton = driver.findElement(By.xpath("(//*[@class='android.view.View' and ./parent::*[@class='android.view.View' and ./parent::*[@id='content-wrapper']]]/*/*/*/*/*[@class='android.widget.Button'])[2]"));
        Utils.fluentWaitforElement(driver, moreFieldsBtn,10, 2);
        Utils.fluentWaitforElement(driver, nextButton,10, 2);
        nextButton.click();
    } catch (Exception e) {
        System.out.println("doFillPaySomeoneForm: " + e.getMessage());
    }

    }


    private void enterDebitReferences(String strDebitReference1,String strDebitReference2){

        MobileElement element;
        List<MobileElement>nextButtonsList;


        if (strDebitReference1.length()!=0){
            try{
            //samo ako je upisan model PNB platitelja
            element = this.fluentWaitforElement(By.xpath("//*[@text='Model i poziv na broj platitelja']"), 10, 2);
            element.click();
            element = this.fluentWaitforElement(By.xpath("//*[@class='android.widget.EditText' and ./parent::*[(./preceding-sibling::* | ./following-sibling::*)[@text='Model']]]"), 20, 2);
            element.click();
            element.sendKeys(strDebitReference1);
            ((AndroidDriver) driver).pressKey(new KeyEvent(AndroidKey.ENTER));
            if (!strDebitReference1.equals("99")) {
                element = this.fluentWaitforElement(By.xpath("//*[@class='android.widget.EditText' and ./parent::*[(./preceding-sibling::* | ./following-sibling::*)[@text='Referenca']]]"), 20, 2);
                element.click();
                element.sendKeys(strDebitReference2);
                ((AndroidDriver) driver).pressKey(new KeyEvent(AndroidKey.ENTER));
                driver.hideKeyboard();
            }
            nextButtonsList = driver.findElements(By.xpath("//*[@class='android.widget.Button' and @text='NASTAVI']"));

            // //*[@text='NASTAVI' and ./parent::*[./parent::*[./parent::*[./parent::*[(./preceding-sibling::* | ./following-sibling::*)[./*[./*[./*[./*[@text='Model i poziv na broj']]]]]]]]]]
            nextButtonsList.get(0).click();
        }
        catch (WebDriverException e ){ driver.findElements(By.xpath("//*[@text='ic info']"));return;}
        }


    }

    private void enterCreditReferences(String strCreditReference1, String strCreditReference2){

        MobileElement element;
        List<MobileElement>nextButtonsList;

        if (strCreditReference1.length()!=0) {
            //samo ako je upisan model PNB primatelja
            element = this.fluentWaitforElement(By.xpath("//*[@text='Model i poziv na broj primatelja']"), 10, 2);
            element.click();
            element = this.fluentWaitforElement(By.xpath("//*[@class='android.widget.EditText' and ./parent::*[(./preceding-sibling::* | ./following-sibling::*)[@text='Model']]]"), 20, 2);
            element.click();
            element.sendKeys(strCreditReference1);
            ((AndroidDriver) driver).pressKey(new KeyEvent(AndroidKey.ENTER));

            if (!strCreditReference1.equals("99")) {
                element = this.fluentWaitforElement(By.xpath("//*[@class='android.widget.EditText' and ./parent::*[(./preceding-sibling::* | ./following-sibling::*)[@text='Referenca']]]"), 20, 2);
                element.click();
                element.sendKeys(strCreditReference2);
                ((AndroidDriver) driver).pressKey(new KeyEvent(AndroidKey.ENTER));
                driver.hideKeyboard();
            }

            nextButtonsList = driver.findElements(By.xpath("//*[@class='android.widget.Button' and @text='NASTAVI']"));
            //System.out.println("Broj (PNB primatelja) NASTAVI buttona: " + nextButtonsList.size());
            // //*[@text='NASTAVI' and ./parent::*[./parent::*[./parent::*[./parent::*[(./preceding-sibling::* | ./following-sibling::*)[./*[./*[./*[./*[@text='Model i poziv na broj']]]]]]]]]]
            nextButtonsList.get(0).click();

        }
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
        //Date today = null;
        //Date inputDate = null;
        //String strDan = null;
        //SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        //DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        //LocalDateTime now = LocalDateTime.now();

        String inputMonthName;
        String nowMonthName;
        String nowNextMonthName;
        String inputNextMonthName;
        List<MobileElement> dateElementToPickList;
        String path;
        MobileElement clickON = null;
        //System.out.println("Zadani datum je: " + strDate);

        try {
            calendarIcon.click();
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

    public void doVerifyData() throws InterruptedException {
        //System.out.println("PaySomeonePage.doProceedAfterPaymentForm");

        fluentWaitforElement(nextButton,60,5);
        nextButton.click();

        //slijedeće je sazetak ili error pregled error poruka
        String xpathString = "//*[@text='Sažetak'] | " +
                "//*[@text and @class='android.view.View' and (./preceding-sibling::* | ./following-sibling::*)[@class='android.view.View' and ./*[@class='android.widget.Image'] and ./*[@class='android.view.View'] and ./*[@class='android.widget.TextView']]] | " +
                "//*[@text and @class='android.view.View' and (./preceding-sibling::* | ./following-sibling::*)[@class='android.view.View' and ./*[@class='android.view.View']]]";
                
        try {
            MobileElement element = fluentWaitforElement(By.xpath(xpathString), 60, 1);
            //System.out.println("element.getText() = " + element.getText());

        }
        catch(Exception e){
            e.printStackTrace();
        }

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

    public void doSaveForLater() throws InterruptedException {
        boolean wait = fluentWaitforElement(saveForLaterBtn,60,5);
        tapOnElement(saveForLaterBtn);

    }


    public void doUpload() throws InterruptedException {
        boolean wait = fluentWaitforElement(loadBtn,60,5);
        loadBtn.click();
    }




    public void verifyPaymentResultMessage (String expectedText) {
        fluentWaitforElement(paymentResultMessage,30,1);
        //System.out.println("U verify result message");
        System.out.println(paymentResultMessage.getText().toUpperCase());
        System.out.println(expectedText);
        Assert.assertEquals(paymentResultMessage.getText().toUpperCase(), expectedText.toUpperCase());
    }

    public void verifyOffLimitsMessage (String expectedText) {
        String strXpath = "//*[contains(text(),'" + expectedText +"')]";
        MobileElement offMsg = fluentWaitforElement(By.xpath(strXpath),30,1);
        //System.out.println("U verify off limit message");
        System.out.println(offMsg.getText());
        System.out.println(expectedText);
        Assert.assertEquals(offMsg.getText().toUpperCase(), expectedText.toUpperCase());

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
        return authID;
    }

    public String getRefrenceID(){
        String referenceID = "";
        //MobileElement e = driver.findElement(By.xpath("//android.view.View[contains(@text,'Referenca')]"));
        fluentWaitforElement(referenceIDTxtField,30,1);
        referenceID = referenceIDTxtField.getText().substring(referenceIDTxtField.getText().indexOf(":") + 2);
        return referenceID;
    }

    public void finishPaySomeone(){
        if (isElementPresent(By.xpath("//*[@text='Završi']"))) {
            driver.findElement(By.xpath("//*[@text='Završi']")).click();
        }

    }

    public String getAmount(String strAmount){
        //formatInputAmount(strAmount);
        return ""; //driver.findElement(By.xpath("//*[@text='" + strAmount + "']")).getAttribute("text");
    }

    public String getPaymentDate(String strDate){

        return "";//driver.findElement(By.xpath("//*[@text='" + strDate + ".']")).getAttribute("text");
    }

    public void showBeneficiaryList(){

        beneficiarySelectButton.click();
        Utils.fluentWaitforElement(driver,driver.findElement(By.xpath("//*[@text='Odaberite primatelja' and @top='true']")), 30, 5);
       String Actualtext = driver.findElement(By.xpath("//*[@text='Odaberite primatelja' and @top='true']")).getText();
        Assert.assertEquals(Actualtext, "Odaberite primatelja");


    }
}


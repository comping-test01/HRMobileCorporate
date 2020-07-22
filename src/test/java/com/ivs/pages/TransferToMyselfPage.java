package com.ivs.pages;

import com.ivs.util.Utils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class TransferToMyselfPage extends BasePage {

    public TransferToMyselfPage(AppiumDriver<MobileElement> driver) {
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }
    MobileElement payer;


    @FindBy(xpath = "//*[@text='ODABERI DRUGI RAČUN']")
    MobileElement selectAnotherAccountLink;

    final String payeeAccBoxXpath ="(((//*[@class='android.view.View' and ./parent::*[@class='android.view.View' and ./parent::*[@id='content-wrapper']]]/*[@class='android.view.View'])[2]/*[@class='android.view.View'])[2]/*/*[@class='android.view.View' and ./*[@class='android.view.View'] and ./parent::*[@class='android.view.View']])[2]";
    @FindBy(xpath = payeeAccBoxXpath)
    MobileElement payeeAccBox;

    @FindBy(xpath = "//*[@class='android.view.View' and ./*[@text='Iznos*']]")
    MobileElement amount;

    @FindBy(xpath = "//*[@class='android.widget.EditText']")
    MobileElement paymentDescriptionField;

    @FindBy(xpath = "//*[@text='NASTAVI']")
    MobileElement proceedBtn;

    @FindBy(xpath = "//*[@text='SPREMI']")
    MobileElement saveBtn;

    @FindBy(xpath = "//*[@text='UČITAJ']")
    MobileElement uploadBtn;

    @FindBy(xpath = "//*[@text='AUTORIZIRAJ']")
    MobileElement authorizeBtn;

    @FindBy(xpath = "//*[@text='PLATI']")
    MobileElement payBtn;

    @FindBy(xpath = "//*[@text='ZATVORI']")
    MobileElement zatvoriBtn;

    @FindBy(xpath = "//*[@text and @class='android.view.View' and (./preceding-sibling::* | ./following-sibling::*)[@class='android.view.View' and ./*[@class='android.widget.Image']]]")
    MobileElement message;

    public boolean isVisible(){
        return fluentWaitforElementBoolean(By.xpath(payeeAccBoxXpath),60,2);
    }

    public void doSelectPayer(String payerIBAN){
        Utils.fluentWaitforElement(driver,selectAnotherAccountLink, 10, 2);
        selectAnotherAccountLink.click();

        MobileElement elementToClick = driver.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(new UiSelector().text(\"" + payerIBAN + " - HRK\"));"));
        elementToClick.click();
    
    }

    public void doSelectPayee(String payeeIBAN) throws Exception {
        Utils.fluentWaitforElement(driver,payeeAccBox, 10, 2);
        int swipes = 0;
        int maxSwipes = 10;

        while(swipes<=maxSwipes){
            payeeAccBox.click();
            if(driver.findElements(By.xpath("//*[@text='" + payeeIBAN + "']")).size()!=0) {
                zatvoriBtn.click();
                break;
            }
            zatvoriBtn.click();
            doSwipeLR('L',payeeAccBox);
            if (swipes == maxSwipes)  throw new Exception("PayeeIBAN (" + payeeIBAN + ") was not found (doSelectPayee) !");
            swipes++;
        }
    }

    public void doFillAmount(String strAmount){
        Utils.fluentWaitforElement(driver,amount, 10, 2);
        amount.click();
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
        proceedBtn.click();
    }
    public void doFillDescription(String strPaymentDescription) throws InterruptedException {
        Thread.sleep(1000);
        Utils.fluentWaitforElement(driver,paymentDescriptionField, 10, 2);
        paymentDescriptionField.click();
        paymentDescriptionField.sendKeys(strPaymentDescription);
        //driver.pressKey(new KeyEvent(AndroidKey.ENTER));
        driver.hideKeyboard();
    }
    public boolean checkIfOdaberiDrugiRacunExists() throws InterruptedException {
        boolean exists = fluentWaitforElement(selectAnotherAccountLink,20,5);
        return exists;
    }

    public void doProceed() throws InterruptedException {
        Thread.sleep(1000);
        Utils.fluentWaitforElement(driver,proceedBtn, 10, 2);
        proceedBtn.click();
    }

    public void doSave() {
        Utils.fluentWaitforElement(driver,saveBtn, 10, 2);
        saveBtn.click();
        Utils.fluentWaitforElement(driver,message, 10, 2);
        String text = message.getText();
        Assert.assertEquals(message.getText().toUpperCase(), "Nalog uspješno spremljen i nalazi se u Listi naloga, u pripremi.!".toUpperCase());
    }

    public void doUpload() {
        Utils.fluentWaitforElement(driver,uploadBtn, 10, 2);
        uploadBtn.click();
        Utils.fluentWaitforElement(driver,message, 10, 2);
        String text = message.getText();
        Assert.assertEquals(message.getText().toUpperCase(), "Nalog uspješno učitan i nalazi se u Autorizaciji.!".toUpperCase());
    }

    public void doAuthorize() throws InterruptedException {
        Utils.fluentWaitforElement(driver,authorizeBtn, 10, 2);
        authorizeBtn.click();
        LoginPage login = new LoginPage(driver);
        login.enterPIN("789987");
        Utils.fluentWaitforElement(driver,message, 10, 2);
        String text = message.getText();
        Assert.assertEquals(message.getText().toUpperCase(), "Nalog uspješno poslan i autoriziran te se nalazi u Autorizaciji.!".toUpperCase());
    }

    public void doPay() throws InterruptedException {
        Utils.fluentWaitforElement(driver,payBtn, 10, 2);
        payBtn.click();
        LoginPage login = new LoginPage(driver);
        login.enterPIN("789987");
        Utils.fluentWaitforElement(driver,message, 10, 2);
        String text = message.getText();
        Assert.assertEquals(message.getText().toUpperCase(), "Nalog uspješno poslan na izvršenje. Molimo provjerite status u Listi naloga.!".toUpperCase());
    }

}


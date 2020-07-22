package com.ivs.pages;

import com.ivs.util.Utils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class PaymentListPage extends BasePage {

    public PaymentListPage(AppiumDriver<MobileElement> driver) {
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    @FindBy(xpath = "//*[@text='U pripremi']")
    MobileElement ToDo;

    //*[@class='android.view.View' and ./*[@text='PLATITELJ'] and ./*[@class='android.view.View']]

    @FindBy(xpath = "(((//*[@class='android.view.View' and ./parent::*[@class='android.view.View' and ./parent::*[@id='content-wrapper']]]/*[@class='android.view.View'])[2]/*[@class='android.view.View'])[2]/*/*[@class='android.view.View' and ./*[@class='android.view.View'] and ./parent::*[@class='android.view.View']])[1]")
    MobileElement account;

    @FindBy(xpath = "//*[@class='android.view.View' and ./*[@text='PLATITELJ'] and ./*[@class='android.view.View']]")
    MobileElement firstAccount;

    final String threeDotsXpath = "//*[@text='ic grey dots']";
    @FindBy(xpath = threeDotsXpath)
    MobileElement threeDots;

    @FindBy(xpath = "//*[@text='Kopiraj']")
    MobileElement copy;

    @FindBy(xpath = "//*[@text='ZATVORI']")
    MobileElement zatvoriBtn;

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

    @FindBy(xpath = "//*[@text='Plati']")
    MobileElement proceedPlatiBtn;

    @FindBy(xpath="//*[@text='U REDU']")
    MobileElement okButton;

    @FindBy(xpath = "//*[@text and @class='android.view.View' and (./preceding-sibling::* | ./following-sibling::*)[@class='android.view.View' and ./*[@class='android.widget.Image']]]")
    MobileElement message;

    public void pressToDo(){
        Utils.fluentWaitforElement(driver,ToDo, 10, 2);
        ToDo.click();
    }

    public void selectAccount(String IBAN) throws Exception {
            Utils.fluentWaitforElement(driver,account, 10, 2);
            int swipes = 0;
            int maxSwipes = 10;

            while(swipes<=maxSwipes){
                if (swipes == 0)
                    tapOnElement(firstAccount);
                else
                    tapOnElement(account);
                Thread.sleep(500);
                if(driver.findElements(By.xpath("//*[@text='" + IBAN + "']")).size()!=0) {
                    tapOnElement(zatvoriBtn);
                    break;
                }
                tapOnElement(zatvoriBtn);
                doSwipeLR('L',account);
                if (swipes == maxSwipes)  throw new Exception("PayeeIBAN (" + IBAN + ") was not found (doSelectPayee) !");
                swipes++;
                Thread.sleep(3000);
        }
    }

    public void copyFirstPayment() throws Exception {
        if(driver.findElements(By.xpath(threeDotsXpath)).size() == 0)
            throw new Exception("Ne postoji niti jedan nalog u pripremi");
        Utils.fluentWaitforElement(driver,threeDots, 10, 2);
        threeDots.click();
        copy.click();
        Thread.sleep(2000);
    }

    public void proceed() throws InterruptedException {
        //Utils.fluentWaitforElement(driver,proceedBtn, 10, 2);
        for (int i=1; i<=2; i++){
            boolean nasao = fluentWaitforElement(proceedBtn, 10, 2);
            if (nasao){
                try{
                    proceedBtn.click();
                } catch (Exception e){
                    System.out.println("proceedBtn not found");
                }

            } else { //proceed ima tekst 'Plati'
                try{
                    proceedPlatiBtn.click();
                } catch (Exception e){
                    System.out.println("proceedPlatiBtn not found");
                }
            }
            Thread.sleep(3000);
        }

    }

    public void doSave() throws InterruptedException {
        Utils.fluentWaitforElement(driver,saveBtn, 10, 2);
        saveBtn.click();
        Utils.fluentWaitforElement(driver,message, 10, 2);
        Thread.sleep(5000); //moze se i smanjiti malo
        Assert.assertEquals(message.getText().toUpperCase(), "Nalog uspješno spremljen i nalazi se u Listi naloga, u pripremi.!".toUpperCase());
    }

    public void doUpload() {
        Utils.fluentWaitforElement(driver,uploadBtn, 10, 2);
        uploadBtn.click();
        Utils.fluentWaitforElement(driver,message, 10, 2);
        String text = message.getText();
        Assert.assertEquals(message.getText().toUpperCase(), "Nalog uspješno učitan i nalazi se u Autorizaciji.!".toUpperCase());
    }

    public void doAuthorize(String strPIN) throws InterruptedException {
        Utils.fluentWaitforElement(driver,authorizeBtn, 10, 2);
        authorizeBtn.click();
        LoginPage login = new LoginPage(driver);
        login.enterPIN(strPIN);
        Utils.fluentWaitforElement(driver,message, 10, 2);
        String text = message.getText();
        Assert.assertEquals(message.getText().toUpperCase(), "Nalog uspješno poslan i autoriziran te se nalazi u Autorizaciji.!".toUpperCase());
    }

    public void doPay(String strPIN) throws InterruptedException {
        Utils.fluentWaitforElement(driver,payBtn, 10, 2);
        payBtn.click();
        LoginPage login = new LoginPage(driver);
        login.enterPIN(strPIN);
        Utils.fluentWaitforElement(driver,message, 10, 2);
        String text = message.getText();
        Assert.assertEquals(message.getText().toUpperCase(), "Nalog uspješno poslan na izvršenje. Molimo provjerite status u Listi naloga.!".toUpperCase());
    }
}


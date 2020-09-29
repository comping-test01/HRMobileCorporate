package com.ivs.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.SQLOutput;
import java.util.NoSuchElementException;

public class LoginPage extends BasePage {


    public LoginPage(AppiumDriver<MobileElement> driver) {

        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }




/*    @FindAll({
                @FindBy(xpath = "//android.widget.Button[contains(@text, 'Prijava')]"),
               @FindBy(xpath = "//android.widget.Button[contains(@text, 'Login')]"),
               @FindBy(xpath = "//android.widget.Button[contains(@text, 'Registriraj moj račun')]"),
            @FindBy(xpath = "//android.widget.Button[contains(@text, 'Register my account')]")
    })*/


    @FindBy(xpath = "//android.widget.Button[contains(@text, 'Prijava')] | //android.widget.Button[contains(@text, 'Login')] | //android.widget.Button[contains(@text, 'Registriraj moj račun')] | //android.widget.Button[contains(@text, 'Register my account')]")
    WebElement loginButton;

    @FindBy(xpath = "//span[contains(text(),'PIN')]")
    WebElement enterPinText;

    @FindBy(xpath = "//*[@class='android.widget.Button' and ./parent::*[./parent::*[(./preceding-sibling::* | ./following-sibling::*)[./*[@class='android.widget.Button']]]]]")
    MobileElement selectLanguageButton;

    @FindBy(xpath = "//*[@text='close HR' and ./parent::*[(./preceding-sibling::* | ./following-sibling::*)[./*[@text='close EN']]]]")
    WebElement selectHRButton;

    @FindBy(xpath = "//*[@text='close EN' and ./parent::*[(./preceding-sibling::* | ./following-sibling::*)[./*[@text='close HR']]]]")
    WebElement selectENButton;

    final String preLoginImageLogoXpath = "//android.widget.Image[contains(@text, 'prelogin-logo')]";


    final String loginButtonRegisterXpath = "//*[@text='Registriraj moj račun']";
    @FindBy(xpath=loginButtonRegisterXpath)
    MobileElement loginButtonRegister;


    //driver.findElement(By.xpath("//*[@text='Registriraj moj račun']"));
    @FindBy(xpath="//*[@text='Registriraj moj račun']")
    MobileElement registrationButton;

    //@FindBy(xpath="//*[@text='U REDU']")
    @FindBy(xpath="//*[@class='android.widget.Button' and ./parent::*[@class='android.widget.LinearLayout']]")
    MobileElement okButton;


    @FindBy(xpath="//*[@id='com.ivs.digi4biz:id/' and @class='android.widget.ImageView' and ./parent::*[@class='android.widget.FrameLayout']]")
    MobileElement pinPageX;

    @FindBy(xpath="//*[@text='menu']")
    MobileElement homeMenu;

    @FindBy(xpath = "//*[@text='Neispravan PIN. Pokušaj: 1/3'] | //*[@text='Incorrect PIN. Attempt: 1/3']")
    WebElement wrongPINTxt;


    //*********Page Methods*********

    public void loginAndEnterPIN(String strPIN, String language) throws InterruptedException {
        waitForClickableElement(driver, selectLanguageButton,10);
        tapOnElement(selectLanguageButton);
        if (language.equals("hr")){
            waitForClickableElement(driver, selectHRButton,10);
            selectHRButton.click();
        }
        else{
            waitForClickableElement(driver, selectENButton,10);
            selectENButton.click();
        }

        waitForClickableElement(driver,loginButton,30);
        loginButton.click();
        //waitForElement(driver,enterPinText,10);
        enterPIN(strPIN);
        Thread.sleep(1000);


    }

    public void enterPIN() {
        boolean wait = fluentWaitforElement(loginButton,60,5);
        if (wait){
            loginButton.click();
        }else{
            loginButtonRegister.click();
        }

        driver.findElement(By.xpath("//*[@text='7']")).click();
        driver.findElement(By.xpath("//*[@text='8']")).click();
        driver.findElement(By.xpath("//*[@text='9']")).click();
        driver.findElement(By.xpath("//*[@text='9']")).click();
        driver.findElement(By.xpath("//*[@text='8']")).click();
        driver.findElement(By.xpath("//*[@text='7']")).click();
        okButton.click();

    }

    public void enterPIN( String pin) {
        for(char c : pin.toCharArray())
            driver.findElement(By.xpath("//*[@text='"+c+"']")).click();
        okButton.click();
    }

    public void pressLogin() {
        boolean wait = fluentWaitforElement(loginButton,5,1);
        loginButton.click();
    }

    // Verify Wrong credentials
    public boolean verifyWrongCredentials() {
        try {
            wait.until(ExpectedConditions.visibilityOf(wrongPINTxt));
            return true;

        } catch (NoSuchElementException e) {
            return false;

        }
    }

    public boolean preLoginLogoIsVisible(){
        return driver.findElements(By.xpath(preLoginImageLogoXpath)).size() != 0;
    }

    public boolean isPINPageVisible(){
        boolean wait = fluentWaitforElementBoolean(By.xpath("//*[@text='Unesi PIN']"),10,2);
        return driver.findElements(By.xpath("//*[@text='Unesi PIN']")).size() != 0;
    }

    public void closePINPage(){
        pinPageX.click();
    }


}
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
import java.util.NoSuchElementException;

public class LoginPage extends BasePage {


    public LoginPage(AppiumDriver<MobileElement> driver) {

        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    final String loginButtonXpath = "(((((//*[@class='android.view.View' and ./parent::*[@class='android.view.View' and ./parent::*[@class='android.view.View' and ./parent::*[@id='content-wrapper']]]]/*[@class='android.view.View'])[2]/*[@class='android.view.View'])[2]/*[@class='android.view.View'])[1]/*[@class='android.view.View'])[3]/*[@class='android.view.View'])[4]";
    //final String loginButtonXpath ="//*[@class='android.widget.Button' and ./parent::*[(./preceding-sibling::* | ./following-sibling::*)[@text]]]";
    @FindBy(xpath=loginButtonXpath)
    MobileElement loginButton;

    final String loginButtonRegisterXpath = "//*[@text='Registriraj moj račun']";
    @FindBy(xpath=loginButtonRegisterXpath)
    MobileElement loginButtonRegister;

    @FindBy(xpath = "//span[contains(text(),'1/3')]")
    MobileElement incorrectPINText;


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

    //*********Page Methods*********

    public void loginAndEnterPIN(String strPIN) throws InterruptedException {

        fluentWaitforElement(loginButton,60,5);
        loginButton.click();
        Thread.sleep(2000);
        enterPIN(strPIN);
        Thread.sleep(1000);


    }

    public void enterPIN() throws InterruptedException {
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

    public void enterPIN( String pin) throws InterruptedException {
        for(char c : pin.toCharArray())
            driver.findElement(By.xpath("//*[@text='"+c+"']")).click();
        okButton.click();
    }

    public void pressLogin() throws InterruptedException {
        boolean wait = fluentWaitforElement(loginButton,5,1);
        loginButton.click();
    }

    // Verify Wrong credentials
    public boolean verifyWrongCredentials() {
        boolean wait = fluentWaitforElement(incorrectPINText, 5, 1);
        return wait;
    }

    public boolean isVisible(){
        return driver.findElements(By.xpath(loginButtonXpath)).size() != 0;
    }

    public boolean isPINPageVisible(){
        boolean wait = fluentWaitforElementBoolean(By.xpath("//*[@text='Unesi PIN']"),10,2);
        return driver.findElements(By.xpath("//*[@text='Unesi PIN']")).size() != 0;
    }

    public void closePINPage(){
        pinPageX.click();
    }


}
package com.ivs.pages;

import com.fasterxml.jackson.databind.ser.Serializers;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AccountsPage extends BasePage {
    public AccountsPage(AppiumDriver<MobileElement> driver) {
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }



    public void chooseAccount(String payerIBANInput,  WebDriverWait wait) throws InterruptedException {
        //*[@text='ic grey dots' and ./parent::*[./parent::*[(./preceding-sibling::* | ./following-sibling::*)[@text='HR5023400091170010419']]]]
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@text='ic grey dots' and ./parent::*[./parent::*[(./preceding-sibling::* | ./following-sibling::*)[@text='"+payerIBANInput+"']]]]"))).click();



    }
}

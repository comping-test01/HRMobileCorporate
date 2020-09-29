package com.ivs.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class AccountsPage extends BasePage {

    public AccountsPage(AppiumDriver<MobileElement> driver) {
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public String checkAccountBalance(String IBAN) throws InterruptedException {
        wait.until(ExpectedConditions.elementToBeClickable(By.name("ic-menu"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("menu-item-ACCOUNTS"))).click();
        driver.context("NATIVE_APP");
        Thread.sleep(2000);
        MobileElement accountCard = driver.findElement(By.xpath("//*[@class='android.view.View' and ./*[@class='android.view.View' and ./*[./*[@class='android.view.View' and ./*[@text='" + IBAN +"']]] and ./*[@class='android.view.View']]]"));
        String accountBalance = accountCard.findElement(By.xpath("//*[@text='Stanje']/following::*")).getText();
        return accountBalance;
    }
}

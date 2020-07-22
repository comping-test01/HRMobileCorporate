package com.ivs.util;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

public class Utils {

    WebDriver driver;

    public Utils(WebDriver driver) {
        this.driver = driver;
    }

    public MobileElement fluentWaitforElement(By by, long timeoutSec, long pollingSec) {

        FluentWait<WebDriver> fWait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(timeoutSec))
                .pollingEvery(Duration.ofSeconds(pollingSec))
                .ignoring(org.openqa.selenium.NoSuchElementException.class).ignoring(NoSuchElementException.class);
        //.ignoring(NoSuchElementException.class, TimeoutException.class).ignoring(StaleElementReferenceException.class);


        long maksimum = 1;
        for (int i = 0; i < maksimum; i++) {
            try {
                fWait.until(ExpectedConditions.visibilityOf(driver.findElement(by)));
                fWait.until(ExpectedConditions.elementToBeClickable(by));
            } catch (Exception e) {
                //System.out.println("Element Not found trying again - " + by.toString());
                //e.printStackTrace();
                maksimum++;
                if (maksimum>(timeoutSec/pollingSec)) maksimum = timeoutSec/pollingSec;
            }
        }

        return driver.findElement(by);
    }

    public Boolean fluentWaitforElementBoolean(By by, long timeoutSec, long pollingSec) {

        try {
            Wait<WebDriver> fluentWait = new FluentWait<WebDriver>(this.driver)
                    .pollingEvery(Duration.ofSeconds(pollingSec))
                    .withTimeout(Duration.ofSeconds(timeoutSec))
                    .ignoring(org.openqa.selenium.NoSuchElementException.class).ignoring(NoSuchElementException.class);
            fluentWait.until(ExpectedConditions.elementToBeClickable(by));
            if(driver.findElement(by).isDisplayed()){
                return true;
            }
        }catch (TimeoutException toe) {
            System.out.println("Timeout error, element not found: " + by.toString());
            toe.printStackTrace();

        }catch (Exception e) {
            System.out.println("Element not found: " + by.toString());
            e.printStackTrace();
        }
        return false;
    }

    public Boolean fluentWaitforElement(WebElement element, long timeoutSec, long pollingSec) {
        try {
            Wait<WebDriver> fluentWait = new FluentWait<WebDriver>(this.driver)
                    .pollingEvery(Duration.ofSeconds(pollingSec))
                    .withTimeout(Duration.ofSeconds(timeoutSec))
                    .ignoring(org.openqa.selenium.NoSuchElementException.class).ignoring(NoSuchElementException.class);
            fluentWait.until(ExpectedConditions.elementToBeClickable(element));
            if(element.isDisplayed())
            {
                return true;
            }
        }catch (TimeoutException toe) {
            System.out.println("Timeout error, element not found: " + element.toString());
            toe.printStackTrace();

        }catch (Exception e) {
            System.out.println("Element not found: " + element.toString());
            e.printStackTrace();
        }
        return false;
    }



    public static void fluentWaitforElement(WebDriver driver, WebElement element, long timeoutSec, long pollingSec) {
        try {
            FluentWait<WebDriver> fluentWait = new FluentWait<WebDriver>(driver)
                    .withTimeout(Duration.ofSeconds(timeoutSec))
                    .pollingEvery(Duration.ofSeconds(pollingSec))
                    .ignoring(StaleElementReferenceException.class)
                    .ignoring(org.openqa.selenium.NoSuchElementException.class).ignoring(NoSuchElementException.class);


            long maksimum = 1;
        }catch (TimeoutException toe) {
            System.out.println("Timeout error, element not found");
            toe.printStackTrace();

        }catch (Exception e) {
            System.out.println("Element not found");
            e.printStackTrace();
        }
    }

    public static void fluentWaitforElement(WebDriver driver, WebElement element) {
        try {
            FluentWait<WebDriver> fluentWait = new FluentWait<WebDriver>(driver)
                    .withTimeout(30, TimeUnit.SECONDS)
                    .pollingEvery(1, TimeUnit.SECONDS)
                    .ignoring(StaleElementReferenceException.class)
                    .ignoring(org.openqa.selenium.NoSuchElementException.class).ignoring(NoSuchElementException.class);
            fluentWait.until(ExpectedConditions.elementToBeClickable(element));
            if(element.isDisplayed())
            {
                element.click();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isElementPresent(By by, AppiumDriver d)
    {
        d.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        boolean exists = d.findElements(by).size() != 0;
        d.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        return exists;
    }

    public static boolean isElementPresent(MobileElement element , WebDriver d)
    {
        d.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        boolean exists = isElementPresent(element,d);
        d.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        return exists;
    }

    public static String getMonthNameHRV(int month) {

        String monthName = null;
        switch (month) {
            case 1:
                monthName = "Siječanj";
                break;
            case 2:
                monthName = "Veljača";
                break;
            case 3:
                monthName = "Ožujak";
                break;
            case 4:
                monthName = "Travanj";
                break;
            case 5:
                monthName = "Svibanj";
                break;
            case 6:
                monthName = "Lipanj";
                break;
            case 7:
                monthName = "Srpanj";
                break;
            case 8:
                monthName = "Kolovoz";
                break;
            case 9:
                monthName = "Rujan";
                break;
            case 10:
                monthName = "Listopad";
                break;
            case 11:
                monthName = "Studeni";
                break;
            case 12:
                monthName = "Prosinac";
                break;
        }
        return monthName;
    }
}

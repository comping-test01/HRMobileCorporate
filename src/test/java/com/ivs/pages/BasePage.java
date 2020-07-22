package com.ivs.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.functions.ExpectedCondition;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.List;

import static io.appium.java_client.touch.offset.PointOption.point;

//komentar
public class BasePage extends PageGenerator {

    public BasePage(AppiumDriver<MobileElement> driver) {
        super(driver);
    }

    //If we need we can use custom wait in BasePage and all page classes
    WebDriverWait wait = new WebDriverWait(this.driver,20);
    public SoftAssert softAssert = new SoftAssert();

    //Click Method by using JAVA Generics (You can use both By or Webelement)
    /*public <T> void click (T elementAttr) {
        if(elementAttr.getClass().getName().contains("By")) {
            driver.findElement((By) elementAttr).click();
        } else {
            ((MobileElement) elementAttr).click();
        }
    }
    */


    //Write Text by using JAVA Generics (You can use both By or Webelement)
    public <T> void writeText (T elementAttr, String text) {
        if(elementAttr.getClass().getName().contains("By")) {
            driver.findElement((By) elementAttr).sendKeys(text);
        } else {
            ((MobileElement) elementAttr).sendKeys(text);
        }
    }

    //Read Text by using JAVA Generics (You can use both By or Webelement)
    public <T> String readText (T elementAttr) {
        if(elementAttr.getClass().getName().contains("By")) {
            return driver.findElement((By) elementAttr).getText();
        } else {
            return ((MobileElement) elementAttr).getText();
        }
    }

    //Close popup if exists
    public void handlePopup (By by) throws InterruptedException {
        List<MobileElement> popup = driver.findElements(by);
        if(!popup.isEmpty()){
            popup.get(0).click();
            Thread.sleep(200);
        }
    }

    public boolean isElementPresent(By by){
        try{
            driver.findElement(by);
            return true;
        }
        catch(NoSuchElementException e){
            return false;
        }
    }

    public boolean isElementPresent(MobileElement element, int timeOutSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeOutSeconds);
        try {
            if (wait.until(ExpectedConditions.visibilityOf(element)) != null) {
                return true;
            }
        } catch (Exception e) {
            System.out.println("isElementPresent, element not found: " + element.toString());;
        }
        return false;
    }

    public MobileElement fluentWaitforElement(By by, long timeoutSec, long pollingSec) {

        FluentWait<WebDriver> fWait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(timeoutSec))
                .pollingEvery(Duration.ofSeconds(pollingSec))
                .ignoring(org.openqa.selenium.NoSuchElementException.class).ignoring(java.util.NoSuchElementException.class);
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
                    .ignoring(org.openqa.selenium.NoSuchElementException.class).ignoring(java.util.NoSuchElementException.class);
            fluentWait.until(ExpectedConditions.elementToBeClickable(driver.findElement(by)));
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
                    .ignoring(org.openqa.selenium.NoSuchElementException.class).ignoring(java.util.NoSuchElementException.class);
            fluentWait.until(ExpectedConditions.elementToBeClickable(element));
            if(element.isDisplayed())
            {
                return true;
            }
        }catch (TimeoutException toe) {
            System.out.println("Timeout error, element not found");
            toe.printStackTrace();

        }catch (Exception e) {
            System.out.println("Element not found");
            e.printStackTrace();
        }
        return false;
    }

    public void tapOnElement(MobileElement element){
        new TouchAction(driver)
                .tap(point(element.getLocation().getX()+5, element.getLocation().getY()+5))
                .perform();
    }

    public ExpectedCondition elementFoundAndClicked(By locator) {
        return (ExpectedCondition<Object>) driver -> {
            WebElement el = driver.findElement(locator);
            el.click();
            return true;
        };
    }


    private ExpectedCondition elementFoundAndNotEmpty(By locator) {
        return (ExpectedCondition<Object>) driver -> {
            MobileElement el = driver.findElement(locator);
            return el.getText().length() != 0;

        };
    }

    public void doSwipeLR(char direction, MobileElement el){  //direction - 'L' = left, other = right
        Point p = el.getLocation();

        //int width = driver.manage().window().getSize().getWidth(); // jedno vijeme radilo, sad ne radi - vraca (0,0)
        int width = 1080;

        PointOption pointOption1 = new PointOption<>();
        pointOption1.withCoordinates((int)(width*(direction == 'L'? 0.8 : 0.2)), p.getY());
        PointOption pointOption2 = new PointOption<>();
        pointOption2.withCoordinates((int)(width*(direction == 'L' ? 0.2 : 0.8)), p.getY());

        new TouchAction(driver).press(pointOption1).waitAction(WaitOptions.waitOptions(Duration.ofMillis(475))).moveTo(pointOption2).release().perform();
    }
}
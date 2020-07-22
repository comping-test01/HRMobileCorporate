package com.ivs.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

//Note (OB):
//Without Page Factory we can generate and return a new instance of a page by using below line.
//return pageClass.getDeclaredConstructor(WebDriver.class, WebDriverWait.class).newInstance(this.driver, this.wait);

public class PageGenerator {

    AppiumDriver<MobileElement> driver;

    //Constructor
    public PageGenerator(AppiumDriver<MobileElement> driver){
        this.driver = driver;
    }

    public AppiumDriver<MobileElement> getDriver(){return driver;}

    //JAVA Generics to Create and return a New Page
    public  <TPage extends BasePage> TPage GetInstance (Class<TPage> pageClass) {
        try {
            //Initialize the Page with its elements and return it.

            //return PageFactory.initElements(new AppiumFieldDecorator(driver),this.pageClass);
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
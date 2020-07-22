package com.ivs.util;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.openqa.selenium.remote.DesiredCapabilities;

public abstract class DriverManager {


    protected DesiredCapabilities capabilities;
    AppiumDriverLocalService service;
    AppiumDriver<MobileElement> driver = null;

    protected abstract void startService();
    protected abstract void stopService();
    protected abstract void createDriver(String location) throws Exception;



    public AppiumDriver<MobileElement> getDriver(String location) throws Exception {


        if (null == driver) {
            //capabilities.setCapability("app", PropertyReader.getProperties("application"));
            startService();
            createDriver(location);
        }
        return driver;
    }






}
package com.ivs.util;

import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;
import java.util.concurrent.TimeUnit;

public class IOSManager extends DriverManager{


        @Override
        public void createDriver(String appiumServer) throws Exception {

            capabilities = new DesiredCapabilities();
            capabilities.setCapability("deviceName", "device");
            capabilities.setCapability("platformName", "iOS");

            //driver = new IOSDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
            driver = new IOSDriver(new URL(appiumServer), capabilities);

            driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        }


    public void stopService() {

    }

    public void startService() {

    }
}

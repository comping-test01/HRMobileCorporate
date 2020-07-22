package com.ivs.util;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;


public class AndroidManager extends DriverManager {

            private String reportDirectory = "reports";
            private String reportFormat = "xml";



        @Override
        public void createDriver(String appiumServer) throws Exception {


//            capabilities = new DesiredCapabilities();
//            capabilities.setCapability("reportDirectory", reportDirectory);
//            capabilities.setCapability("reportFormat", reportFormat);
//            //capabilities.setCapability("deviceName","520034ffe2ea25af");
//            //capabilities.setCapability(MobileCapabilityType.UDID, "39V7N19109011053");
//            //capabilities.setCapability("udid", "520034ffe2ea25af");
//            //capabilities.setCapability("deviceName", "520034ffe2ea25af");
//            capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.ivs.digi4biz");
//            capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, ".MainActivity");
//            capabilities.setCapability(MobileCapabilityType.NO_RESET, true);
//            //capabilities.setCapability("platformName", "Android");
//            //capabilities.setCapability("platformVersion", "8.0.0");
//
//
//
//            //driver = new AndroidDriver<>(new URL("http://localhost:4723/wd/hub"), capabilities);
//            driver = new AndroidDriver<>(new URL(appiumServer), capabilities);
            capabilities = new DesiredCapabilities();
            //capabilities.setCapability("bowserName", "Huawei P9 2019");

            //capabilities.setCapability("reportDirectory", reportDirectory);
            //capabilities.setCapability("reportFormat", reportFormat);
            //capabilities.setCapability("deviceName","520034ffe2ea25af");
            //capabilities.setCapability(MobileCapabilityType.UDID, "39V7N19109011053");
 /*
            capabilities.setCapability("udid", "39V7N19109011053"); //9cc1725b9dcf
*/
            capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "hr.pbz.digi4biz");
            capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, ".MainActivity");
            capabilities.setCapability(MobileCapabilityType.NO_RESET, true);
            capabilities.setCapability("automationName","UiAutomator2");


            //deviceName":"192.168.5.19:5555","deviceUDID":"192.168.5.19:5555"
            //capabilities.setCapability("udid", "39V7N19109011053");
            capabilities.setCapability("platformName", "ANDROID");

            //capabilities.setCapability("udid", "39V7N19109011053"); //upisuje se ili udid mobitela ili IP adresa ako je spojen preko wifi-ja
            //capabilities.setCapability("udid", "192.168.5.19:5555");
            //capabilities.setCapability("platformVersion", "8.0.0");*/


            //driver = new AndroidDriver<>(new URL("http://localhost:4444/wd/hub"), capabilities);
            //driver = new AndroidDriver<>(new URL(appiumServer), capabilities);

            //driver = (AppiumDriver<MobileElement>) new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);

            //driver = new AndroidDriver<>(new URL("http://localhost:4723/wd/hub"), capabilities);
            driver = new AndroidDriver<>(new URL(appiumServer), capabilities);
        }


    public  void startService() {
        //boolean flag=   checkIfServerIsRunnning(4723);
        //if(!flag)
        //{
        int port = 4723;
        URL appiumServerURL;

        /*AppiumServiceBuilder serviceBuilder = new AppiumServiceBuilder();
        serviceBuilder.withIPAddress("127.0.0.1");
        serviceBuilder.usingPort(port);
        serviceBuilder.usingDriverExecutable(new File("C:/Program Files/nodejs/node.exe"));
        serviceBuilder.withAppiumJS(new File("C:/Users/msivec/AppData/Roaming/npm/node_modules/appium/build/lib/main.js"));
        serviceBuilder.withLogFile(new File (System.getProperty("user.home")+"\\AppiumServerLogs.txt"));
        System.out.println(System.getProperty("user.home"));
        service = AppiumDriverLocalService.buildService(serviceBuilder);
*/
        if (!checkIfServerIsRunning(port)) {

            System.out.println("Appium Server NOT running!!!");

            //service.start();
            //appiumServerURL = service.getUrl();

        }
        else {
            System.out.println("Appium server running on Port - " + port);
        }
    }


    public boolean checkIfServerIsRunning(int port) {

        boolean isServerRunning = false;
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(port);
            serverSocket.close();

        } catch (IOException e) {
            //If control comes here, then it means that the port is in use
            isServerRunning = true;

        } finally {
            serverSocket = null;
        }
        return isServerRunning;
    }


    @Override
    public void stopService() {
        if (null != service && service.isRunning())
            service.stop();
    }


    /*private static DesiredCapabilities getDesiredCapabilities(String udid) {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        String appPath = null;
        capabilities.setCapability("platformName", configProperty.getProperty("platformName"));
        capabilities.setCapability("platformVersion", configProperty.getProperty("version"));

        if (StringUtils.isNotBlank(udid)) {
            if (isDeviceAvailable(udid)) {
                capabilities.setCapability("udid", udid);
                capabilities.setCapability("deviceName", udid);
            } else {
                throw new RuntimeException(udid + " not available after waiting for " + TOTAL_TIMES + " minutes");
            }
        } else {
            udid = getAvailableDeviceUDID();
            if (StringUtils.isBlank(udid)) {
                throw new RuntimeException("No device available after waiting for " + TOTAL_TIMES + " minutes");
            }
            capabilities.setCapability("udid", udid);
            capabilities.setCapability("deviceName", udid);
        }
        logger.info("Device udid : " + udid);

        if(configProperty.getProperty("platform").equalsIgnoreCase("win")) {
            capabilities.setCapability("platformVersion", udid);
        }
        if("runSauceLabFromLocal".equalsIgnoreCase(configProperty.getProperty("true"))){
            appPath="";
        }
        if ("mobileweb".equalsIgnoreCase(configProperty.getProperty("appType"))) {
            capabilities.setCapability("app", configProperty.getProperty("browser"));
        } else if ("Win".equalsIgnoreCase(platform)) {
            capabilities.setCapability("app", configProperty.getProperty("WinAppPackage"));
        } else if (configProperty.hasProperty("appPathiOS")) {
            capabilities.setCapability("app", configProperty.getProperty("appPathiOS"));
        } else {
            appPath = getAppAbsoultePath();
            capabilities.setCapability("app", appPath);
        }

        return capabilities;
    }

     */


}

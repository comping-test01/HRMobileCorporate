package com.ivs.tests;


import com.ivs.pages.PageGenerator;
import com.ivs.util.DriverManagerFactory;
import com.ivs.util.ExcelUtil;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.annotations.*;

import static io.appium.java_client.touch.offset.PointOption.point;


public class BaseTest
{

    PageGenerator pageGen;

    Object[][] arrInputParams;

    String applicationPIN;
    String remoteServerAddress;
    String localServerAddress;
    String serverAddress;
    DriverManagerFactory.DriverType driverType;
    String language;
    String platform;



    protected AppiumDriver<MobileElement> driver = null;

    @BeforeClass
    @Parameters({"platform","language"})
    public void setupApplication(@Optional("Android") String platform, @Optional("hr") String language, ITestContext context) throws Exception {

        ExcelUtil objData = new ExcelUtil();
        arrInputParams = objData.GetLoginValues("login.xlsx","Input1");
        applicationPIN = arrInputParams[0][0].toString();
        localServerAddress = arrInputParams[0][1].toString();
        remoteServerAddress = arrInputParams[0][2].toString();

        serverAddress = (remoteServerAddress.length()==0) ? localServerAddress : remoteServerAddress;
            serverAddress = localServerAddress;

        DriverManagerFactory.DriverType localDriverType;

        if(platform.equalsIgnoreCase("Android")){
            localDriverType = DriverManagerFactory.DriverType.ANDROID;
        } else if(platform.equalsIgnoreCase("IOS")) {
            localDriverType = DriverManagerFactory.DriverType.IOS;
        } else {
            throw new RuntimeException("Browser is not correct");
        }

        if (driverType != localDriverType) {
            if (driver != null) {
                closeApplication();
            }
            driverType = localDriverType;
            driver = (AndroidDriver<MobileElement>) DriverManagerFactory.getManager(localDriverType).getDriver(serverAddress);
        }

        this.language = language;
        this.platform = platform;
        context.setAttribute("language",language);
        context.setAttribute("platform",platform);

    }
    @BeforeMethod
    public void methodLevelSetup () {
        pageGen = new PageGenerator(driver);
    }

    @AfterSuite
    public void globalTearDown () {
        //service.stop();
    }




/*
    @AfterMethod
    public void logOut() {
        HomePage homePage =new HomePage(driver);
        try {
            homePage.doLogOut();
        } catch (InterruptedException e) {
            System.out.println("***** logOut *****");
            e.printStackTrace();
        }*/


//        if (Utils.isElementPresent(By.xpath("//*[@text='ios-ic-closex']"),driver)){
//            ////*[@text='ios-ic-closex']
//            driver.findElement(By.xpath("//*[@text='ios-ic-closex']")).click();
//        }
//
//
//        driver.findElement(By.xpath("//*[@text='Ionic App']")).click();
//        driver.findElement(By.xpath("//*[@text='ic menu']")).click();
//
//
//        if (Utils.isElementPresent(By.xpath("//*[@text='ic logout']"),driver)){
//            ////*[@text='ios-ic-closex']
//            driver.findElement(By.xpath("//*[@text='ic logout']")).click();
//        }


    //}

    @AfterClass
    public void closeApplication()
    {
        driver.closeApp();
        driver.quit();
    }


    public void tapOnElement(MobileElement element){
        new TouchAction(driver)
                .tap(point(element.getLocation().getX()+5, element.getLocation().getY()+5))
                .perform();
    }





}
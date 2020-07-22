package com.ivs.tests;

import com.ivs.pages.HomePage;
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
import org.testng.annotations.*;

import static io.appium.java_client.touch.offset.PointOption.point;


public class BaseTest
{

    WebDriverWait wait;
    PageGenerator pageGen;

    Object[][] arrInputParams;
    //String masterUsername;
    //String masterPassword;
    //String operaterUsername;
    //String operaterPassword;
    //String applicationAddress;
    String appiumServerAddress;
    String masterPIN;

    private String reportDirectory = "reports";
    private String reportFormat = "xml";
    private String testName = "testPaySomeone";
    protected AppiumDriver<MobileElement> driver = null;
    DesiredCapabilities dc = new DesiredCapabilities();


    private static AppiumDriverLocalService service;


    @BeforeClass
    public void globalSetup () throws Exception {

        ExcelUtil objData = new ExcelUtil();
        arrInputParams = objData.GetLoginValues("login.xlsx","Input1");     //"C:\\Appium\\login.xlsx"
        //masterUsername = arrInputParams[0][0].toString();
        //masterPassword = arrInputParams[0][1].toString();
        //operaterUsername = arrInputParams[0][2].toString();
        //operaterPassword = arrInputParams[0][3].toString();
        //applicationAddress = arrInputParams[0][4].toString();
        masterPIN = arrInputParams[0][0].toString();
        if (masterPIN.length()==0){
            //ako PIN nije definiran u login datoteci stavljamo defaultni
            masterPIN = "789987";
        }
        appiumServerAddress = arrInputParams[0][1].toString();
        if (appiumServerAddress.length()==0){
            //ako nije definirana adresa appium servera, koristimo lokalnu adresu
            appiumServerAddress = "http://localhost:4723/wd/hub";
        }

        driver = (AndroidDriver<MobileElement>) DriverManagerFactory.getManager (DriverManagerFactory.DriverType.ANDROID).getDriver(appiumServerAddress);

    }

    @AfterSuite
    public void globalTearDown () {
        //service.stop();
    }



    @BeforeMethod
    public void methodLevelSetup () {
        //Instantiate the Page Class
        pageGen = new PageGenerator(driver);
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
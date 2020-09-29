package com.ivs.tests;


import com.ivs.pages.ClientSelectPage;
import com.ivs.pages.HomePage;
import com.ivs.pages.LoginPage;
import com.ivs.pages.PageGenerator;
import com.ivs.util.DriverManagerFactory;
import com.ivs.util.ExcelUtil;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.*;
import com.ivs.util.JSONDataProvider;
import com.ivs.util.PropertiesFile;

import java.lang.reflect.Method;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static io.appium.java_client.touch.offset.PointOption.point;


public class BaseTest
{

    PageGenerator pageGen;

    String applicationPIN;
    String remoteServerAddress;
    String localServerAddress;
    String serverAddress;
    String environment;
    DriverManagerFactory.DriverType driverType;
    String language;
    String platform;
    String testRailEnabled;
    String dataFile;

    String expectedText;
    ResourceBundle resourceBundle;

    protected AppiumDriver<MobileElement> driver = null;

    @BeforeClass
    @Parameters({"platform","language", "testRailEnabled"})
    public void setupApplication(@Optional("Android") String platform, @Optional("hr") String language, @Optional("true") String testRailEnabled, ITestContext context) throws Exception {

        PropertiesFile properties = new PropertiesFile();

        applicationPIN = properties.getapplicationPIN();
        localServerAddress = properties.getLocalServerAddress();
        remoteServerAddress = properties.getRemoteServerAddress();
        environment = properties.getEnvironment();


        serverAddress = (environment.equals("local")) ? localServerAddress : remoteServerAddress;

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
        this.testRailEnabled = testRailEnabled;
        context.setAttribute("language",language);
        context.setAttribute("platform",platform);
        context.setAttribute("platform",platform);

    }

    String setSingleParameter(String parameterName, JSONObject testData) {
        String s;
        try {
            s = testData.get(parameterName).toString();
        } catch(Exception e) {
            s = null;
        }

        return s;

    }

    void setResources() {
        Locale.setDefault(new Locale(this.language, this.language.toUpperCase()));
        resourceBundle = ResourceBundle.getBundle("messages", Locale.getDefault());
    }

    void loginToApplication(int waitTime) throws InterruptedException {
        driver.manage().timeouts().implicitlyWait(waitTime, TimeUnit.SECONDS);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAndEnterPIN(applicationPIN,language);
    }

    HomePage setUpHomePage() {
        HomePage homePage = pageGen.GetInstance(HomePage.class);
        expectedText = resourceBundle.getString("WelcomeMessage");
        Assert.assertTrue(homePage.getWelcomeText().toUpperCase().contains(expectedText));
        return homePage;
    }

    void setUpCompany(HomePage homePage, String legalEntityNameInput) throws Exception {
        homePage.doChangeCompany(legalEntityNameInput);
        ClientSelectPage clientSelectPage = pageGen.GetInstance(ClientSelectPage.class);
        clientSelectPage.doSearchAndSelectClient(legalEntityNameInput);
    }

    void switchToWebView() {
        Set<String> availableContexts = driver.getContextHandles();
        for (String contextName : availableContexts) {
            //System.out.println(contextName);
        }
        availableContexts.stream()
                .filter(context -> context.toLowerCase().contains("webview"))
                .forEach(newcontext -> driver.context(newcontext));
    }


    @BeforeMethod
    public void methodLevelSetup () {
        pageGen = new PageGenerator(driver);
    }

    @AfterMethod
    public void logOut(Method method) throws InterruptedException {
        HomePage homePage = new HomePage(driver);
       homePage.doLogOut();
    }


    @AfterClass
    public void closeApplication() {
      //  driver.closeApp();
       // driver.quit();
    }

    @AfterSuite
    public void closeOut() {
        driver.quit();
    }


    public void tapOnElement(MobileElement element){
        new TouchAction(driver)
                .tap(point(element.getLocation().getX()+5, element.getLocation().getY()+5))
                .perform();
    }





}
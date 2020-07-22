package com.ivs.pages;

import com.ivs.util.Utils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import static io.appium.java_client.touch.offset.PointOption.point;

public class HomePage extends BasePage {

    public HomePage (AppiumDriver<MobileElement> driver) {

        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }
    //Utils utils = new Utils(driver);

    //*[@id='back']
    @FindBy(xpath="//*[@id='back']")
    MobileElement backButton;

    @FindBy(xpath="//*[@text='Ionic App']")
    MobileElement iconicAppButton;

    @FindBy(xpath="//*[@text='menu']")
    MobileElement homeMenu;

    @FindBy(xpath="//*[@text='ic menu']")
    MobileElement homeMenu2;


    @FindBy(xpath="//*[@text='ic change comp Promijeni subjekt']")
    MobileElement changeCompanyButton;


    @FindBy(xpath="((((//*[@class='android.view.View' and ./parent::*[@class='android.view.View' and ./parent::*[@id='content-wrapper']]]/*[@class='android.view.View'])[2]/*[@class='android.view.View'])[2]/*[@class='android.view.View'])[1]/*[@text and @class='android.view.View'])[2]")
    MobileElement changeCompanyElement;

    @FindBy(xpath="//*[@text='Preskoči']")
    MobileElement setMainCompanyButton;

    //Plaćanja/Plaćanje

    //*[@class='android.view.View' and ./*[@class='android.view.View' and ./*[@text='ic payment']]]
    //@FindBy(xpath="//*[@text='Plaćanja']")
    //@FindBy(xpath="//*[@class='android.view.View' and ./*[@text='ic payment']]")
    //@FindBy(xpath="//*[@text='ic arrow right' and (./preceding-sibling::* | ./following-sibling::*)[@text='Plaćanja']]")
    //@FindBy(xpath="//*[@id='menu-item-PAYMENTS']")
    //@FindBy(id="menu-item-PAYMENTS")
    @FindBy(xpath="//*[@text='Plaćanja']")
    MobileElement paymentsMainMenu;

    @FindBy(xpath="//*[@class='android.view.View' and ./*[@class='android.view.View' and ./*[@text='ic payment']]]")
    MobileElement paymentsMainMenu2;

    @FindBy(xpath="//*[@text='Plaćanja u stranoj valuti']")
    MobileElement fxPaymentsSubItem;

    //@FindBy(xpath="//*[@text='Plaćanje']")
    //@FindBy(xpath="//*[@id='menu-item-NEW_KUNA_PAYMENT_ORDER']")
    @FindBy(id="menu-item-NEW_KUNA_PAYMENT_ORDER")
    MobileElement paymentsSubItem;


    @FindBy(xpath="//*[@text='close PBZ logo']")
    MobileElement pbzLogoButton;


    @FindBy(xpath="//*[@text='Plaćanje' and ./parent::*[@text='Plaćanje']]")
    MobileElement paymentsFastAccessButton;


    @FindBy(xpath="//*[@text='close PBZ logo']")
    MobileElement quickAccessButton;

    @FindBy(xpath = "(((((//*[@class='android.view.View' and ./parent::*[@class='android.view.View' and ./parent::*[@class='android.view.View' and (./preceding-sibling::* | ./following-sibling::*)[@id='content-wrapper']] and (./preceding-sibling::* | ./following-sibling::*)[@class='android.view.View']]]/*[@class='android.view.View'])[1]/*[@class='android.view.View'])[2]/*[@class='android.view.View'])[2]/*[@class='android.view.View'])[5]/*[@class='android.view.View' and ./*[@class='android.view.View' and ./*[./*[@text and @class='android.view.View']]]])[3]")
    MobileElement fxPageTitleText;

    public void runHomeMenu() {
        wait.until(ExpectedConditions.visibilityOf(homeMenu));
        //Utils.fluentWaitforElement(driver, homeMenu, 10,2);
        homeMenu.click();
    }

    public void doChangeCompanyClick(){

        Utils.fluentWaitforElement(driver, changeCompanyButton, 10,2);
        changeCompanyButton.click();
    }
    /*element = this.fluentWaitforElement(By.xpath("//*[@text='ic change comp Promijeni subjekt']"), 10, 2);
                element.click();
    */

    public void doSelectPaymentMenu(){
        paymentsMainMenu.click();

    }

    public void doSelectpPaymentsOnFastAccess(){
        WebElement element;
        /*Utils.fluentWaitforElement(driver, pbzLogoButton, 10, 2);
        pbzLogoButton.click();
        if (!(Utils.isElementPresent(paymentsFastAccessButton, driver))) {
            //ako se popup menu nije pojavio....
            pbzLogoButton.click();
        }
        //driver.findElement(By.xpath("//*[@text='Plaćanje' and ./parent::*[@text='Plaćanje']]")).click();
        Utils.fluentWaitforElement(driver, paymentsFastAccessButton, 10, 2);
        paymentsFastAccessButton.click();*/

        element = this.fluentWaitforElement(By.xpath("//*[@text='close PBZ logo']"), 10, 2);
        element.click();
        if (!(Utils.isElementPresent(By.xpath("//*[@text='Plaćanje' and ./parent::*[@text='Plaćanje']]"), driver))) {
            //ako se popup menu nije pojavio....
            driver.findElement(By.xpath("//*[@text='close PBZ logo']")).click();
        }
        element = this.fluentWaitforElement(By.xpath("//*[@text='Plaćanje' and ./parent::*[@text='Plaćanje']]"), 10, 2);
        element.click();
    }

//    public void doSelectPaymentAndPaymentSubItem(){
//        iconicAppButton.click();
//        runHomeMenu();
//        Utils.fluentWaitforElement(driver, paymentsMainMenu);
//        paymentsMainMenu.click();
//
//        /*List<WebElement> childs = paymentsSubMenu.findElements(By.xpath(".//*"));
//        childs.get(0).click();
//        System.out.println(childs.size());
//        iconicAppButton.click();
//        */
//        paymentsSubItem.click();
//        System.out.println("Plaćanja-plaćanje");
//    }



    public void doSelectPaymentAndPaymentSubItem2(){
        AndroidDriver driver2 = (AndroidDriver) driver;
        iconicAppButton.click();
        runHomeMenu();
//        //paymentsMainMenu.click();
        fluentWaitforElement(paymentsMainMenu,30,1);
        tapOnElement(paymentsMainMenu);
//        new TouchAction(driver)
//                .tap(point(paymentsMainMenu.getLocation().getX()+5, paymentsMainMenu.getLocation().getY()+5))
//                .perform();

        try {
            Thread.sleep(500);
            driver2.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

            WebElement e = driver2.findElementByXPath("//*[@text='Plaćanje']");
            MobileElement m = (MobileElement) driver.findElementByXPath("//*[@text='Plaćanje']");

            int x = e.getLocation().getX();
            int x2 = m.getLocation().getX();

            int width = e.getSize().getWidth();
            int y = e.getLocation().getY();
            int y2 = m.getLocation().getY();
            int height = e.getSize().getHeight();
            int realX = 300; //(x/3) + (width/2);
            int realY = y+height/2+10;

            //System.out.println("x = " + x + ", y = " + y + ", width = " + width + ", realX = " + realX);
            //System.out.println("x2 = " + x2 + ", y2 = " + y2 );

            PointOption pointOption = new PointOption<>();
            pointOption.withCoordinates(realX, realY);
            new TouchAction(driver2).press(pointOption).waitAction(WaitOptions.waitOptions(Duration.ofMillis(475))).moveTo(pointOption).release().perform();

        } catch (Exception e) {
            System.out.println("***** doSelectPaymentAndPaymentSubItem2 *****");
            e.printStackTrace();
        }
    }

    public void doSelectPaymentAndFXPaymentSubItem() throws InterruptedException {
        Thread.sleep(2000);
        AndroidDriver driver2 = (AndroidDriver) driver;
        iconicAppButton.click();
        runHomeMenu();
//        //paymentsMainMenu.click();
        fluentWaitforElement(paymentsMainMenu,30,1);
        Thread.sleep(1000);
        tapOnElement(paymentsMainMenu);
//        new TouchAction(driver)
//                .tap(point(paymentsMainMenu.getLocation().getX()+5, paymentsMainMenu.getLocation().getY()+5))
//                .perform();
        int realX=0;
        int realY=0;
        try {
            Thread.sleep(500);
            driver2.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

            //WebElement e = driver2.findElementByXPath("//*[@text='Plaćanja u stranoj valuti']");
            WebElement e = driver2.findElementById("menu-item-FOREIGN_PAYMENT");
            //MobileElement m = (MobileElement) driver.findElementByXPath("//*[@text='Plaćanja u stranoj valuti']");
            //MobileElement m = (MobileElement) driver.findElementsById("menu-item-FOREIGN_PAYMENT");

            int x = e.getLocation().getX();
            //int x2 = m.getLocation().getX();

            int width = e.getSize().getWidth();
            int y = e.getLocation().getY();
            //int y2 = m.getLocation().getY();
            int height = e.getSize().getHeight();
            realX = 300; //(x/3) + (width/2);
            realY = y+height/2+10;

            //System.out.println("x = " + x + ", y = " + y + ", width = " + width + ", realX = " + realX);
            //System.out.println("x2 = " + x2 + ", y2 = " + y2 );

            PointOption pointOption = new PointOption<>();
            pointOption.withCoordinates(realX, realY);
            new TouchAction(driver2).press(pointOption).waitAction(WaitOptions.waitOptions(Duration.ofMillis(475))).moveTo(pointOption).release().perform();

        } catch (Exception e) {
            System.out.println("***** doSelectPaymentAndFXPaymentSubItem exception *****");
            e.printStackTrace();
        }
        try{
            //još jedan pokušaj odabira
            Thread.sleep(2000);
            // WebElement e = driver2.findElementById("menu-item-FOREIGN_PAYMENT");
            PointOption pointOption = new PointOption<>();
            pointOption.withCoordinates(realX, realY);
            new TouchAction(driver2).press(pointOption).waitAction(WaitOptions.waitOptions(Duration.ofMillis(475))).moveTo(pointOption).release().perform();

        }catch (Exception e) {
            System.out.println("doSelectPaymentAndFXPaymentSubItem menu-item-FOREIGN_PAYMENT cannot be found!");
            e.printStackTrace();
        }
    }

    public void doSelectFXPaymentSubItem() throws InterruptedException {
        Thread.sleep(2000);
        AndroidDriver driver2 = (AndroidDriver) driver;
        int realX=0;
        int realY=0;
        try {
            driver2.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            WebElement e = driver2.findElementById("menu-item-FOREIGN_PAYMENT");
            int y = e.getLocation().getY();
            int height = e.getSize().getHeight();
            realX = 300; //(x/3) + (width/2);
            realY = y+height/2+10;

            PointOption pointOption = new PointOption<>();
            pointOption.withCoordinates(realX, realY);
            new TouchAction(driver2).press(pointOption).waitAction(WaitOptions.waitOptions(Duration.ofMillis(475))).moveTo(pointOption).release().perform();

        } catch (Exception e) {
            System.out.println("***** doSelectPaymentAndFXPaymentSubItem*2* exception *****");
            e.printStackTrace();
        }
    }

    public void doSelectPaymentAndTransferToMyself(){
        AndroidDriver driver2 = (AndroidDriver) driver;
        iconicAppButton.click();
        runHomeMenu();
        tapOnElement(paymentsMainMenu);

        try {
            Thread.sleep(500);
            driver2.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

            WebElement e = driver2.findElementByXPath("//*[@text='Moji prijenosi']");
            MobileElement m = (MobileElement) driver.findElementByXPath("//*[@text='Moji prijenosi']");

            int x = e.getLocation().getX();
            int x2 = m.getLocation().getX();

            int width = e.getSize().getWidth();
            int y = e.getLocation().getY();
            int y2 = m.getLocation().getY();
            int height = e.getSize().getHeight();
            int realX = 300; //(x/3) + (width/2);
            int realY = y+height/2+10;

            System.out.println("x = " + x + ", y = " + y + ", width = " + width + ", realX = " + realX);
            System.out.println("x2 = " + x2 + ", y2 = " + y2 );

            PointOption pointOption = new PointOption<>();
            pointOption.withCoordinates(realX, realY);
            new TouchAction(driver2).press(pointOption).waitAction(WaitOptions.waitOptions(Duration.ofMillis(475))).moveTo(pointOption).release().perform();

        } catch (Exception e) {
            System.out.println("***** doSelectPaymentAndPaymentSubItem2 *****");
            e.printStackTrace();
        }
    }

    public void doSelectPaymentAndBeneficiaryList(){
        AndroidDriver driver2 = (AndroidDriver) driver;
        //wait.until(ExpectedConditions.visibilityOf(iconicAppButton));
        //iconicAppButton.click();
        try {
            Thread.sleep(6000);
        } catch(InterruptedException e) {
            System.out.println("got interrupted!");
        }
        runHomeMenu();
        wait.until(ExpectedConditions.visibilityOf(paymentsMainMenu));
        tapOnElement(paymentsMainMenu);

        try {
            Thread.sleep(500);
            driver2.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

            WebElement e = driver2.findElementByXPath("//*[@text='Primatelji i predlošci']");
            MobileElement m = (MobileElement) driver.findElementByXPath("//*[@text='Primatelji i predlošci']");

            //int x = e.getLocation().getX();
            //int x2 = m.getLocation().getX();

            int width = e.getSize().getWidth();
            int y = e.getLocation().getY();
            int y2 = m.getLocation().getY();
            int height = e.getSize().getHeight();
            int realX = 300; //(x/3) + (width/2);
            int realY = y+height/2+10;

            //System.out.println("x = " + x + ", y = " + y + ", width = " + width + ", realX = " + realX);
            //System.out.println("x2 = " + x2 + ", y2 = " + y2 );

            PointOption pointOption = new PointOption<>();
            pointOption.withCoordinates(realX, realY);
            new TouchAction(driver2).press(pointOption).waitAction(WaitOptions.waitOptions(Duration.ofMillis(475))).moveTo(pointOption).release().perform();

        } catch (Exception e) {
            System.out.println("***** doSelectPaymentAndBeneficiaryList *****");
            e.printStackTrace();
        }
    }

    public void doSelectBeneficiaryList(){
        AndroidDriver driver2 = (AndroidDriver) driver;
        System.out.println("*************************** doSelectBeneficiaryList ***************************");
        try {
            Thread.sleep(500);
            driver2.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

            WebElement e = driver2.findElementByXPath("//*[@text='Primatelji i predlošci']");
            MobileElement m = (MobileElement) driver.findElementByXPath("//*[@text='Primatelji i predlošci']");

            //int x = e.getLocation().getX();
            //int x2 = m.getLocation().getX();

            int width = e.getSize().getWidth();
            int y = e.getLocation().getY();
            int y2 = m.getLocation().getY();
            int height = e.getSize().getHeight();
            int realX = 300; //(x/3) + (width/2);
            int realY = y+height/2+10;

            //System.out.println("x = " + x + ", y = " + y + ", width = " + width + ", realX = " + realX);
            //System.out.println("x2 = " + x2 + ", y2 = " + y2 );

            PointOption pointOption = new PointOption<>();
            pointOption.withCoordinates(realX, realY);
            new TouchAction(driver2).press(pointOption).waitAction(WaitOptions.waitOptions(Duration.ofMillis(475))).moveTo(pointOption).release().perform();

        } catch (Exception e) {
            System.out.println("***** doSelectBeneficiaryList(2) *****");
            e.printStackTrace();
        }
    }

    public void doSelectPaymentAndPaymentList(){
        AndroidDriver driver2 = (AndroidDriver) driver;
        iconicAppButton.click();
        runHomeMenu();
        tapOnElement(paymentsMainMenu);
        String paymentListXpath = "//*[@text='Liste naloga']";
        int realX = 300;
        int realY = 0;
        try {
            Thread.sleep(500);
            driver2.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

            WebElement e = driver2.findElementByXPath(paymentListXpath);
            MobileElement m = (MobileElement) driver.findElementByXPath(paymentListXpath);

            //int x = e.getLocation().getX();
            //int x2 = m.getLocation().getX();

            int width = e.getSize().getWidth();
            int y = e.getLocation().getY();
            //int y2 = m.getLocation().getY();
            int height = e.getSize().getHeight();
            //int realX = 300; //(x/3) + (width/2);
            realY = y+height/2+10;

            //System.out.println("x = " + x + ", y = " + y + ", width = " + width + ", realX = " + realX);
            //System.out.println("x2 = " + x2 + ", y2 = " + y2 );

            PointOption pointOption = new PointOption<>();
            pointOption.withCoordinates(realX, realY);
            new TouchAction(driver2).press(pointOption).waitAction(WaitOptions.waitOptions(Duration.ofMillis(475))).moveTo(pointOption).release().perform();

        } catch (Exception e) {
            System.out.println("***** doSelectPaymentAndPaymentSubItem7 *****");
            e.printStackTrace();
        }

        try{
            Thread.sleep(2000);
            WebElement e = driver2.findElementByXPath(paymentListXpath);
            MobileElement m = (MobileElement) driver.findElementByXPath(paymentListXpath);
            PointOption pointOption = new PointOption<>();
            pointOption.withCoordinates(realX, realY);
            new TouchAction(driver2).press(pointOption).waitAction(WaitOptions.waitOptions(Duration.ofMillis(475))).moveTo(pointOption).release().perform();

        }catch (Exception e) {
            System.out.println("doSelectPaymentAndPaymentSubItem7 cannot be found!");
        }

    }

    public void doSelectPaymentSubItem(){
        System.out.println("*************************** doSelectPaymentSubItem ***************************");
        MobileElement element = (MobileElement) driver.findElementByXPath("//*[@text='Plaćanje']");
        new TouchAction(driver)
                .tap(point(300, element.getLocation().getY()+5))
                .perform();

    }

    public void pressQuickAccess(){
        Utils.fluentWaitforElement(driver, quickAccessButton, 10,2);
        quickAccessButton.click();
    }

    public void doLogOut() throws InterruptedException {
        //runHomeMenu();
        //Utils.fluentWaitforElement(driver, logoutButton, 10,2);
        //logoutButton.click();

        LoginPage login = new LoginPage(driver);

        while(!login.isVisible())
            driver.navigate().back();

        //boolean exists = fluentWaitforElement(backButton,30,5);
        //tapOnElement(backButton);

//        if (Utils.isElementPresent(By.xpath("//*[@text='ios-ic-closex']"),driver)){
//            driver.findElement(By.xpath("//*[@text='ios-ic-closex']")).click();
//        }
//
//        iconicAppButton.click();
//        runHomeMenu();
//
//        if (Utils.isElementPresent(logoutButton,driver)){
//            logoutButton.click();
//        }

    }

    public void skipSettingMainCompany(){
        boolean exists = fluentWaitforElement(setMainCompanyButton,20,2);
        if (exists){
            setMainCompanyButton.click();
        }
    }
    public boolean verifyLoginSuccess() {
        try {

            wait.until(ExpectedConditions.visibilityOf(homeMenu));
            homeMenu.isDisplayed();

            return true;

        } catch (NoSuchElementException e) {

            return false;

        }
    }



}
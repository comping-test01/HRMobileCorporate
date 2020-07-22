package com.ivs.tests;


import com.ivs.pages.ClientSelectPage;
import com.ivs.pages.HomePage;
import com.ivs.pages.LoginPage;
import com.ivs.pages.QuickAccessPage;
import com.ivs.util.ExcelUtil;
import com.ivs.util.Utils;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;


public class QuickAccessTest extends BaseTest {


    private Object [][] arrInputParams;
    private int intBrojac = 0, columns = 9;
    String text;



    @DataProvider(name = "testData")
    public Object[][] testData() {

        ExcelUtil objData = new ExcelUtil();
        arrInputParams = objData.GetParameters("QuickAccessMobileInputParameters.xlsx","Input1",columns);
        return arrInputParams;
    }

    @Test(dataProvider = "testData")
    public void QuickAccessTest(Object [] objInput) throws InterruptedException {
        MobileElement element;
        SoftAssert soft = new SoftAssert();
        driver = pageGen.getDriver();
        Utils utils = new Utils(driver);
        String companyName = objInput[0].toString();
        String elementsStr = Arrays.toString(Arrays.copyOfRange(objInput,1,6));
        String[] elementsAll = elementsStr.split("[\\[\\]]")[1].split(", ");

        String[] elements = Arrays.stream(elementsAll)
                .filter(value ->
                        value != null && value.length() > 0
                )
                .toArray(size -> new String[size]);


        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        LoginPage loginPage = new LoginPage(driver);

        try {
            loginPage.loginAndEnterPIN(masterPIN);
            ClientSelectPage clientSelectPage = new ClientSelectPage(driver);
            HomePage homePage = new HomePage(driver);
            QuickAccessPage quickAccessPage = new QuickAccessPage(driver);

            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

            // odabir clienta za slucaj kad izbaci clientSelectPage pri loginu
            if(clientSelectPage.isVisible()) {
                clientSelectPage.doSearchAndSelectClient(companyName);
                element = utils.fluentWaitforElement(By.xpath("//*[@text='Dobrodošli, ']"), 60, 2);
                text = element.getText();
                Assert.assertEquals(text, "Dobrodošli, ");
            }

            homePage.pressQuickAccess();

            quickAccessPage.findSelectedItems();
            quickAccessPage.edit(elements);

            Thread.sleep(500);

            quickAccessPage.checkSelectedItems(elements);

            arrInputParams[intBrojac][6] = "OK";
            arrInputParams[intBrojac][7] = "";

            intBrojac++;
            soft.assertTrue(true);
            soft.assertAll();

        } catch (Exception e) {

            arrInputParams[intBrojac][6] = "NOK";
            arrInputParams[intBrojac][7] = e.toString();
            e.printStackTrace();

            driver.findElement(By.xpath("//*[@text='Ionic App']")).click();
            driver.findElement(By.xpath("//*[@text='ic menu']")).click();
            Thread.sleep(1000);
            element = utils.fluentWaitforElement(By.xpath("//*[@text='ic logout']"), 30, 2);
            //element.click();
            tapOnElement(element);

            intBrojac++;
            soft.assertTrue(false);
            soft.assertAll();
        }


    }



    @AfterSuite
    public void closeOut() {
        WebElement element;

        ExcelUtil objData = new ExcelUtil();
        objData.SaveParameters("QuickAccessMobileInputParameters.xlsx","Input1",arrInputParams,columns,4,"android");

        driver.quit();
    }
}

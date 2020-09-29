package com.ivs.tests;

import com.ivs.pages.*;
import com.ivs.testrail.TestRailCase;
import com.ivs.testrail.TestRailListener;
import com.ivs.util.DataProviderSource;
import com.ivs.util.ExcelUtil;
import com.ivs.util.JSONDataProvider;
import org.json.simple.JSONObject;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import java.lang.reflect.Method;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

@Listeners(TestRailListener.class)
public class HRMobileCorporateBeneficiaryListTest extends BaseTest {

    @TestRailCase(testRailId = 3240)
    @Test(groups={"BeneficiaryList"}, dataProvider="fetchData_JSON", dataProviderClass= JSONDataProvider.class, enabled=true)
    public void beneficiaryListNewBeneficiaryTest(String rowID, String description, JSONObject testData) throws InterruptedException {


        driver = pageGen.getDriver();
        String legalEntityNameInput = testData.get("legalEntityNameInput").toString();
        String identifierNameInput = testData.get("identifierNameInput").toString();
        String beneficiaryIBANInput = testData.get("beneficiaryIBANInput").toString();
        String beneficiaryNameInput = testData.get("beneficiaryNameInput").toString();

        WebDriverWait wait = new WebDriverWait(driver, 10);

        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        LoginPage loginPage = new LoginPage(driver);
        ResourceBundle resourceBundle = ResourceBundle.getBundle("messages", Locale.getDefault());
        
        try {
            loginPage.loginAndEnterPIN(applicationPIN,language);
            ClientSelectPage clientSelectPage = new ClientSelectPage(driver);
            HomePage homePage = new HomePage(driver);

            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

            homePage.doSelectPaymentAndBeneficiaryList();
            BeneficiaryListPage beneficiaryListPage = new BeneficiaryListPage(driver);

            //DEBUG pinNumber exists for deletion purposes, to be removed
            String pinNumber = "789987";
            //beneficiaryListPage.deleteAddedBeneficiary(beneficiaryNameInput,pinNumber, wait);
            identifierNameInput = beneficiaryListPage.addBeneficiary(beneficiaryIBANInput, beneficiaryNameInput, wait);
            homePage.doSelectPaymentAndPaySomeone();
            PaySomeonePage paySomeonePage = new PaySomeonePage(driver);
            paySomeonePage.checkBeneficiary(beneficiaryNameInput, beneficiaryIBANInput, identifierNameInput, wait);

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Exception in test:" + e.getMessage());
        }

    }


    @AfterMethod
    public void logOut(Method method) throws InterruptedException {

        HomePage homePage = new HomePage(driver);
        homePage.doLogOut();

    }

}

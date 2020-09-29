package com.ivs.pages;

import com.ivs.util.Utils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

public class BeneficiaryListPage extends BasePage {

    public BeneficiaryListPage(AppiumDriver<MobileElement> driver) {
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    //@FindBy(xpath = "  //*[@class='android.widget.EditText' and (./preceding-sibling::* | ./following-sibling::*)[@text='ic input search']]")
    @FindBy(xpath = "//*[@class='android.widget.EditText' and ./parent::*[./parent::*[./parent::*[(./preceding-sibling::* | ./following-sibling::*)[./*[@class='android.view.View']]]]]]")
    MobileElement beneficiarySearchInput;

    @FindBy(xpath = "//*[@class='android.view.View' and ./parent::*[@class='android.widget.RadioButton']]")
    MobileElement beneficiaryNameSelectRadioBtn;

    @FindBy(xpath = "//*[@text='NOVI PRIMATELJ'] | //*[@text='NEW BENEFICIARY'] | //*[@class='android.widget.Button' and ./parent::*[(./preceding-sibling::* | ./following-sibling::*)[@text]]]")
    WebElement newBeneficiary;

    @FindBy(xpath = "//*[@class='android.widget.EditText' and ./parent::*[(./preceding-sibling::* | ./following-sibling::*)[@text='Ime korisnika*']]] | //*[@class='android.widget.EditText' and ./parent::*[(./preceding-sibling::* | ./following-sibling::*)[@text='Beneficiary Name*']]]")
    MobileElement nameInput;

    @FindBy(xpath = "//*[@class='android.widget.EditText' and ./parent::*[(./preceding-sibling::* | ./following-sibling::*)[@text='Nadimak korisnika']]] | //*[@class='android.widget.EditText' and ./parent::*[(./preceding-sibling::* | ./following-sibling::*)[@text='Beneficiary Nickname']]]")
    MobileElement nicknameInput;

    @FindBy(xpath = "//*[@text='DODAJ IDENTIFIKATOR'] | //*[@text='ADD IDENTIFIER']")
    MobileElement addIdentifier;

    @FindBy(xpath = "//*[@class='android.widget.EditText' and ./parent::*[(./preceding-sibling::* | ./following-sibling::*)[@text='Broj računa*']]] | //*[@class='android.widget.EditText' and ./parent::*[(./preceding-sibling::* | ./following-sibling::*)[@text='Account number*']]]")
    MobileElement IBANInput;

    @FindBy(xpath = "//*[@text='ic acc number off']")
    MobileElement arrow;

    @FindBy(xpath = "//*[@text='ic grey dots']")
    MobileElement grayDots;

    @FindBy(xpath = "//*[@class='android.widget.EditText' and ./parent::*[(./preceding-sibling::* | ./following-sibling::*)[@text='Vlasnik računa*']]] | //*[@class='android.widget.EditText' and ./parent::*[(./preceding-sibling::* | ./following-sibling::*)[@text='Account owner*']]]")
    MobileElement nameInput2;

    @FindBy(xpath = "//*[@text='Dodaj'] | //*[@text='Add']")
    MobileElement saveIdentifier;

    @FindBy(xpath = "//*[@text='Odustani']")
    MobileElement cancelSaveIdentifier;

    @FindBy(xpath = "//*[@text='POTVRDI']")
    MobileElement cancelSaveIdentifierConfirmation;

    @FindBy(xpath = "//*[@text='Spremi'] | //*[@text='Save']")
    MobileElement saveBeneficiary;

    @FindBy(xpath = "//*[@class='android.widget.EditText']")
    MobileElement search;

    @FindBy(xpath = "//*[@text='reset']")
    MobileElement clearSearch;

    final String threeDotsXpath = "//*[@text='ic grey dots']";
    //final String threeDotsXpath = "//*[@class='android.view.View' and ./parent::*[./parent::*[./parent::*[(./preceding-sibling::* | ./following-sibling::*)[@text]]]] and ./*[@class='android.widget.Button']]";
    @FindBy(xpath = threeDotsXpath)
    MobileElement threeDots;

    @FindBy(xpath = "//*[@text='Obriši'] | //*[@text='Delete']")
    MobileElement delete;

    @FindBy(xpath = "//*[@text='POTVRDI'] | //*[@text='CONFIRM']")
    MobileElement confirm;




    public void doSearchAndSelectBeneficiary(String beneficiaryName, String beneficiaryIBAN) throws Exception {
        //beneficiaryName = "ivana test";
        //strIBAN = "HR1223400093212780834";  //HR9423400093217033722
        //Utils.fluentWaitforElement(driver,driver.findElement(By.xpath("//*[@text='Odaberite primatelja' and @top='true']")), 30, 5);
        boolean wait = fluentWaitforElement(beneficiarySearchInput, 30, 5);
        Thread.sleep(5000);
        beneficiarySearchInput.sendKeys(beneficiaryName);
        //beneficiaryNameSelectRadioBtn.click();
        driver.findElement(By.xpath("//*[@class='android.widget.Button' and ./parent::*[@text='" + beneficiaryName + "']]")).click();
        Utils.fluentWaitforElement(driver,driver.findElement(By.xpath("//*[@text='Odaberite identifikator']")), 30, 5);

        MobileElement elementToClick = driver.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(new UiSelector().text(\"" + beneficiaryIBAN + "\"));"));
        elementToClick.click();

    }

    public String addBeneficiary(String iban, String beneficiaryName, WebDriverWait wait) throws InterruptedException {
        //WebDriverWait wait = new WebDriverWait(driver, 10);
        //Thread.sleep(500);
        wait.until(ExpectedConditions.elementToBeClickable(newBeneficiary)).click();

        //Thread.sleep(500);
        wait.until(ExpectedConditions.elementToBeClickable(addIdentifier)).click();

        wait.until(ExpectedConditions.elementToBeClickable(IBANInput)).click();

        IBANInput.sendKeys(iban);

        wait.until(ExpectedConditions.elementToBeClickable(arrow)).click();

        Thread.sleep(1500);
        String identifierSystemName = nameInput2.getText();

        wait.until(ExpectedConditions.elementToBeClickable(saveIdentifier)).click();

        wait.until(ExpectedConditions.elementToBeClickable(nameInput)).click();

        Thread.sleep(500);
        nameInput.sendKeys(beneficiaryName);
        wait.until(ExpectedConditions.elementToBeClickable(saveBeneficiary)).click();

        return identifierSystemName;
    }

    public void deleteAddedBeneficiary(String beneficiaryName, String pinNumber, WebDriverWait wait) throws Exception {
        //WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(search)).click();
        Thread.sleep(500);
        search.sendKeys(beneficiaryName);
        Thread.sleep(500);

        try {
            grayDots.isDisplayed();
            grayDots.click();
            wait.until(ExpectedConditions.elementToBeClickable(delete)).click();

            wait.until(ExpectedConditions.elementToBeClickable(confirm)).click();

            Thread.sleep(500);
            LoginPage login = new LoginPage(driver);
            login.enterPIN(pinNumber);
            wait.until(ExpectedConditions.elementToBeClickable(search)).click();

            Thread.sleep(500);
            try {
                clearSearch.click();
                Thread.sleep(500);
            }
            catch(org.openqa.selenium.NoSuchElementException e){

            }
        }
        catch(org.openqa.selenium.NoSuchElementException e){
            try {
                clearSearch.click();
                Thread.sleep(500);
            }
            catch (org.openqa.selenium.NoSuchElementException er){

            }
        }

    }


}

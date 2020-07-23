package com.ivs.pages;

import com.ivs.util.Utils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class BeneficiaryListPage extends BasePage{

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
    MobileElement newBeneficiary;

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

    final String threeDotsXpath = "//*[@text='ic grey dots']";
    //final String threeDotsXpath = "//*[@class='android.view.View' and ./parent::*[./parent::*[./parent::*[(./preceding-sibling::* | ./following-sibling::*)[@text]]]] and ./*[@class='android.widget.Button']]";
    @FindBy(xpath = threeDotsXpath)
    MobileElement threeDots;

    @FindBy(xpath = "//*[@text='Obriši']")
    MobileElement delete;

    @FindBy(xpath = "//*[@text='POTVRDI']")
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

    public void addBeneficiary(String iban, String identifierName, String beneficiaryName) throws InterruptedException {
        //Utils.fluentWaitforElement(driver,newBeneficiary, 10, 2);
        Thread.sleep(10000);
        newBeneficiary.click();
        Thread.sleep(1500);

        addIdentifier.click();
        Thread.sleep(1500);

        IBANInput.click();
        IBANInput.sendKeys(iban);
        Thread.sleep(1500);

        arrow.click();
        //za svaki slučaj, ako ne postoji taj racun u aplikaciji
        nameInput2.click();
        nameInput2.sendKeys(identifierName);
        //--------------------------------
        Thread.sleep(1500);
        saveIdentifier.click();
        Thread.sleep(1500);
        //provjeriti je li spremanje prošlo bez greške
        nameInput.click();
        nameInput.sendKeys(beneficiaryName);
        saveBeneficiary.click();    //provjera poruke
        Thread.sleep(5000);
        /*boolean errorMessageExists = fluentWaitforElementBoolean(By.xpath("//*[@text='Račun je već spremljen pod ovim primateljem']"), 10, 1);

        if (errorMessageExists){
            cancelSaveIdentifier.click();
            Thread.sleep(2000);
            cancelSaveIdentifierConfirmation.click();
            Thread.sleep(2000);
            cancelSaveIdentifier.click();
        }else{
            nameInput.click();
            nameInput.sendKeys(beneficiaryName);
            saveBeneficiary.click();    //provjera poruke
            Thread.sleep(5000);
        }*/

    }

    public void checkAddedBeneficiary(String beneficiaryName, String pinNumber) throws Exception {
        //Utils.fluentWaitforElement(driver,search, 10, 2);
        Thread.sleep(5000);
        boolean wait = fluentWaitforElement(search,20,1);
        search.click();
        Thread.sleep(1000);
        search.sendKeys(beneficiaryName);
        Thread.sleep(3000);
        List<MobileElement> elements = driver.findElements(By.xpath(threeDotsXpath));
        MobileElement grayDots = fluentWaitforElement(By.xpath("//*[@class='android.view.View' and ./*[@text='"+beneficiaryName+"']]/following-sibling::android.view.View/android.view.View"),40,1);
        grayDots.click();
        Thread.sleep(500);
        delete.click();
        Thread.sleep(500);
        confirm.click();
        Thread.sleep(1000);

        LoginPage login = new LoginPage(driver);
        login.enterPIN(pinNumber);
        Thread.sleep(5000);
    }

    public boolean checkIfNewBeneficiaryButtonExists(){
        boolean exists = fluentWaitforElement(newBeneficiary,20,5);
        return exists;
    }

    public boolean checkIfSearchButtonExists(int timeoutSec, int pollingSec){
        boolean found = fluentWaitforElement(search,timeoutSec,pollingSec);
        return found;
    }

}

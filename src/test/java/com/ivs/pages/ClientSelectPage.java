package com.ivs.pages;

import com.ivs.util.Utils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ClientSelectPage extends BasePage {

    public ClientSelectPage(AppiumDriver<MobileElement> driver) {
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }


    final String clientSearchXpath = "//*[@class='android.widget.EditText']";
    @FindBy(xpath = clientSearchXpath)
    MobileElement clientSearch;
    Utils utils = new Utils(driver);


    @FindBy(xpath ="//*[@class='android.view.View' and ./parent::*[@class='android.widget.RadioButton']]")
    MobileElement confirmClientSelect;




    public void doSearchAndSelectClient(String clientName) throws Exception {

        boolean clientSearchExists = fluentWaitforElement(clientSearch,60,5);
        if (!clientSearchExists){
            throw new Exception("ClientSearch control is not visible, can not search and select client!");
        }
        clientSearch.click();
        clientSearch.sendKeys(clientName);
        confirmClientSelect.click();
        //WebElement element = utils.fluentWaitforElement(By.xpath("//*[@text='" + clientName + "']"), 30, 2);
        //String text = element.getText();
        //Assert.assertEquals(text, clientName, "Promjena subjekta uspjela!");

    }

    public boolean isVisible(){
        //potrebno usporenje zbog provjere kod logina
        boolean wait = fluentWaitforElementBoolean(By.xpath(clientSearchXpath),60,2);
        System.out.println("clientSearchXpath.isVisible = " + wait);
        //System.out.println("Broj clientearch elemenata - " + driver.findElements(By.xpath(clientSearchXpath)).size());
        return driver.findElements(By.xpath(clientSearchXpath)).size() != 0;
    }

}




/*driver.findElement(By.xpath("//*[@class='android.widget.EditText']")).click();
        driver.findElement(By.xpath("//*[@class='android.widget.EditText']")).sendKeys(objInput[0].toString());
        driver.findElement(By.xpath("//*[@text='" + objInput[0].toString() + "' and @class='android.view.View']")).click();
        element = this.fluentWaitforElement(By.xpath("//*[@text='" + objInput[0].toString() + "']"), 30, 2);
        text = element.getText();
        Assert.assertEquals(text, objInput[0].toString(), "Promjena subjekta uspjela!");
 */



/*driver.findElement(By.xpath("//*[@class='android.widget.EditText']")).click();
        driver.findElement(By.xpath("//*[@class='android.widget.EditText']")).sendKeys(objInput[0].toString());

        driver.findElement(By.xpath("//*[@text='" + objInput[0].toString() + "' and @class='android.view.View']")).click();

        element = this.fluentWaitforElement(By.xpath("//*[@text='" + objInput[0].toString() + "']"), 30, 2);
        text = element.getText();
        Assert.assertEquals(text, objInput[0].toString(), "Promjena subjekta uspjela!");


 */
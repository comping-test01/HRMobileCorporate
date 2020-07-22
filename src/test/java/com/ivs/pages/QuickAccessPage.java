package com.ivs.pages;

import com.ivs.util.Utils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.apache.commons.lang3.ArrayUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.Arrays;

public class QuickAccessPage extends BasePage{


    public QuickAccessPage(AppiumDriver<MobileElement> driver) {

        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    @FindBy(xpath="//*[@text='close PBZ logo']")
    MobileElement greenBtn;

    @FindBy(xpath="//*[@text='Uredi brzi pristup' and ./parent::*[@text='Uredi brzi pristup']]")
    MobileElement edit;

    @FindBy(xpath="//*[@text and @class='android.widget.Button' and ./parent::*[(./preceding-sibling::* | ./following-sibling::*)[./*[./*[./*[./*[@class='android.widget.Image']]]]] and (./preceding-sibling::* | ./following-sibling::*)[./*[@class='android.view.View']]]]")
    MobileElement closeEdit;


    final String[] allItems = {"Računi", "Moji Prijenosi", "Plaćanje", "Kupoprodaja Deviza", "Scan & Pay", "Liste Naloga", "Primatelji I Predlošci", "Kartice"};
    // btn template
    //*[@text='Primatelji i predlošci' and ./parent::*[@text='Primatelji i predlošci']]
    //*[@text='Računi' and @class='android.widget.Button']
    boolean[] selected = new boolean[allItems.length];

    public void findSelectedItems(){
        Arrays.fill(selected, false);
        int index = 0;
        for(String item : allItems) // u ovom dijelu se prikazuju itemi samo s velikim prvim pocetnim slovom samo je Scan & Pay izuzetak
            if(item.equals("Scan & Pay"))
                selected[index++] = driver.findElements(By.xpath("//*[@text='"+item+"']")).size() != 0;
            else
                selected[index++] = driver.findElements(By.xpath("//*[@text='"+item.substring(0, 1).toUpperCase() + item.substring(1).toLowerCase()+"']")).size() != 0;
    }

    public void edit(String[] newItems) throws Exception {
        edit.click();
        // prije svega formatiranje
        for(int i = 0; i < newItems.length; i++)
            newItems[i] = format(newItems[i]);

        //prvo treba deselectati iteme jer najvise 5 itema moze biti selectano u isto vrijeme
        //pa bi to, u suprotnom, pravilo probleme
        for (String item : allItems){
            int allIndex = ArrayUtils.indexOf(allItems,item);
            int newIndex = ArrayUtils.indexOf(newItems,item);
            if(selected[allIndex] && newIndex == -1) //ako je selectan a ne bi trebao biti
                tapOnElement(driver.findElement(By.xpath("//*[@text='"+item+"' and @class='android.widget.Button']")));// driver.findElement(By.xpath("//*[@text='"+item+"' and @class='android.widget.Button']")).click();
        }

        //na kraju selectanje traženih itema
        for(String item: newItems)
            if(!selected[indexOf(item)])
                tapOnElement(driver.findElement(By.xpath("//*[@text='"+item+"' and @class='android.widget.Button']")));//driver.findElement(By.xpath("//*[@text='"+item+"' and @class='android.widget.Button']")).click();

        closeEdit.click();
    }

    public void checkSelectedItems(String[] items) throws Exception { //items - ono sto bi trebalo biti oznaceno
        Utils.fluentWaitforElement(driver, greenBtn, 10,2);
        greenBtn.click();

        // prije svega formatiranje
        for(int i = 0; i < items.length; i++)
            items[i] = format(items[i]);

        findSelectedItems();
        for(String item: items){
            if(!selected[indexOf(item)])
                throw new Exception("Check: Quick Access item ("+item + ") is not selected.");
        }

        greenBtn.click();
    }

    private int indexOf(String item) throws Exception {
        int index = ArrayUtils.indexOf(allItems, item);
        if(index == -1)
            throw new Exception("Quick Access item ("+item + ") does not exist.");
        return index;
    }

    //Postavlja pocetno slovo svake riječi u veliko
    private String format(String item){
        String[] words = item.split(" ");
        String formatted = "";
        for(String word : words)
            formatted += word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase() + " ";
        return formatted.substring(0, formatted.length() - 1); // uklanja space s kraja stringa koji je visak.
    }
}

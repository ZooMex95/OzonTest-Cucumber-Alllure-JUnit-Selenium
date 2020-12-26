package ru.homework.framework.pages;

import org.junit.Assert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.homework.framework.managers.ManagerPages;

import static ru.homework.framework.managers.DriverManager.*;

public class BasePage {
    protected ManagerPages app = ManagerPages.getManagerPages();

    protected WebDriverWait wait = new WebDriverWait(getDriver(), 10, 1000);

    protected JavascriptExecutor js = (JavascriptExecutor)getDriver();

    protected Actions action = new Actions(getDriver());

    public BasePage() {
        PageFactory.initElements(getDriver(), this);
    }

    protected void fillField(WebElement element, String value) {
        element.click();
        element.sendKeys(value);
        Assert.assertEquals("Текст в строке поиска не совпадает",
                value, element.getAttribute("value"));
        element.sendKeys(Keys.ENTER);
    }

    protected void waitChangeText(WebElement element, String oldText) {
        wait.until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElement(element, oldText)));
    }

    protected void scrollToElement(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    protected void scrollToCenterElement(WebElement element) {
        //Point p = element.getLocation();
        js.executeScript("scrollTo({top: " + (element.getLocation().y - 200) + "})");
        //js.executeScript("window.scroll(" + p.getX() + "," + (p.getY() + 300) + ");");
    }
}

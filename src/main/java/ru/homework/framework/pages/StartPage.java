package ru.homework.framework.pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class StartPage extends BasePage {
    @FindBy(xpath = "//input[@name='search']")
    private WebElement search;

    public SearchPage searchForProduct(String nameOfProduct) {
        search.sendKeys(Keys.ESCAPE);
        fillField(search,nameOfProduct);
        return app.getSearchPage();
    }
}

package ru.homework.framework.pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static ru.homework.framework.pages.Product.getNames;

public class CartPage extends BasePage {

    @FindBy(xpath = "//div[@data-widget='split']/div[contains(div,*) and not(@style)]")
    private List<WebElement> availableProducts;

    @FindBy(xpath = "//section[@data-widget='total']//span[contains(text(),'Ваша корзина')]")
    private WebElement cartText;

    @FindBy(xpath = "//div[@delete_button_name]")
    private WebElement deletePanel;

    @FindBy(xpath = "//div[@data-widget='emptyCart']/h1")
    private WebElement emptyCart;

    @FindBy(xpath = "//div[@class='modal-container']//button[not(@aria-label)]")
    private WebElement delete;

    public CartPage checkCart() {
        List<String> productNamesInCart = new ArrayList<>();
        for (int i = 0; i < availableProducts.size(); i++) {
            productNamesInCart.add(availableProducts.get(i).findElement(By.xpath(".//a/span")).getText());
        }
        Assert.assertTrue("Не все товары в корзине совпадают с сохраненными",
                  productNamesInCart.containsAll(getNames()));
        return this;
    }


    public CartPage checkText(String countOfItems) { //доделать
        WebElement element = cartText.findElement(By.xpath("./following::span"));
        Assert.assertTrue("Количество товаров в корзине не совпадает",
                element.getText().toLowerCase().contains(countOfItems.toLowerCase()));
        return this;
    }

    public CartPage deleteProducts() {
        if (deletePanel.findElement(By.xpath(".//input")).getAttribute("checked").equals("false")) {
            deletePanel.findElement(By.xpath(".//input")).click();
        }
        deletePanel.findElement(By.xpath(".//span")).click();
        delete.click();
        waitElementToBeVisible(emptyCart);
        Assert.assertEquals("Корзина не пуста", "Корзина пуста", emptyCart.getText().trim());
        return this;
    }

}

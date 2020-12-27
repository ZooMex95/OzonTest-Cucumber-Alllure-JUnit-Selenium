package ru.homework.framework.pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

import static ru.homework.framework.pages.Product.getNames;

public class CartPage extends BasePage {
    @FindBy(xpath = "//div[@data-widget='split']/div/div/a/span")
    private List<WebElement> productNames;

    @FindBy(xpath = "//div[@data-widget='unavailableSplit']/div/div/div/a/span")
    private List<WebElement> unavailableProducts;

    @FindBy(xpath = "//section[@data-widget='total']//span[contains(text(),'Ваша корзина')]")
    private WebElement cartText;

    public CartPage checkCart() {
        List<String> productNamesInCart = new ArrayList<>();
        for (int i = 0; i < productNames.size(); i++) {
            productNamesInCart.add(productNames.get(i).getText());
        }
        if (!SearchPage.flag) {
            for (int i = 0; i < unavailableProducts.size(); i++) {
                productNamesInCart.add(unavailableProducts.get(i).getText());
            }
        }
        Assert.assertTrue("Не все товары в корзине совпадают с сохраненными",
                  productNamesInCart.containsAll(getNames()));
        return this;
    }

    public CartPage checkText(String countOfItems) {
        WebElement element = cartText.findElement(By.xpath("./following::span"));
        System.out.println(element.getText());
        System.out.println(countOfItems);
//        Assert.assertTrue("Количество товаров в корзине не совпадает",
//                element.getText().toLowerCase().contains(countOfItems.toLowerCase()));
        //section[@data-widget='total']//span[contains(text(),'Ваша корзина')]
        /*
        Вопрос: "добавить в корзину" кнопка есть, но при добавлении товар кончился, какое количество товаров в корзине чекать
         */

        return this;
    }
}

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
    @FindBy(xpath = "//div[@data-widget='split']/div/div/a/span")
    private List<WebElement> productNames;

//    @FindBy(xpath = "//div[@data-widget='split']/div[contains(div,*)]")
//    private List<WebElement> availableProducts;

//    @FindBy(xpath = "//div[@data-widget='unavailableSplit']/div[contains(div,*)]") //div[@data-widget='split']/div/div/a/span/
//    private List<WebElement> unavailableProduct;

    @FindBy(xpath = "//div[@data-widget='unavailableSplit']/div/div/div/a/span")
    private List<WebElement> unavailableProducts;

    @FindBy(xpath = "//section[@data-widget='total']//span[contains(text(),'Ваша корзина')]")
    private WebElement cartText;

    @FindBy(xpath = "//div[@class='modal-content']//button")
    private WebElement deleteButton;

    private List<String> names = new ArrayList<>();

    public CartPage checkCart() {
        List<String> productNamesInCart = new ArrayList<>();
        for (int i = 0; i < productNames.size(); i++) {
            productNamesInCart.add(productNames.get(i).getText());
        }
        if (!SearchPage.flag) {
            try {
                for (int i = 0; i < unavailableProducts.size(); i++) {
                    productNamesInCart.add(unavailableProducts.get(i).getText());   //этим способом 26.4 сек
                }
            } catch (Exception ignored) {

            }

        }

//        System.out.println(getNames());
//        System.out.println(productNamesInCart);
        Assert.assertTrue("Не все товары в корзине совпадают с сохраненными",
                  productNamesInCart.containsAll(getNames()));
   //     checkProductNames();
        return this;
    }



    public CartPage checkText(String countOfItems) { //доделать
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

    public CartPage deleteProducts() {
        for (int i = 0; i < productNames.size(); i++) {
            WebElement delete = productNames.get(i).findElement(By.xpath("./../..//span[normalize-space()='Удалить']"));
            waitElementToBeClickable(delete);
            delete.click();
            deleteButton.click();
        }
        if (!SearchPage.flag) {
            try {
                for (int i = 0; i < unavailableProducts.size(); i++) {
                    WebElement delete = unavailableProducts.get(i).findElement(By.xpath("./../..//span[normalize-space()='Удалить']"));
                    waitElementToBeClickable(delete);
                    delete.click();
                    deleteButton.click();
                }
            } catch (Exception ignored) {
            }
        }
        return this;
    }

//    private void checkProductNames() {
//        List<String> productNamesInCart = new ArrayList<>();
//        for (int i = 0; i < availableProducts.size(); i++) {
//            try {
//                names.add(availableProducts.get(i).findElement(By.xpath(".//a/span")).getText());
//
//            } catch (Exception ignored) {
//
//            }                                         с этим методом 41.997 сек
//        }
//        if (!SearchPage.flag) {
//            for (int i = 0; i < unavailableProduct.size(); i++) {
//                try {
//                    names.add(unavailableProduct.get(i).findElement(By.xpath(".//a/span")).getText());
//                } catch (Exception ignored) {
//
//                }
//            }
//
//        }
//
//        System.out.println(getNames());
//        System.out.println(names);
//        Assert.assertTrue("Не все товары в корзине совпадают с сохраненными",
//                names.containsAll(getNames()));
//
//    }
}

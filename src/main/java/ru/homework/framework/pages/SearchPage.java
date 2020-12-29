
package ru.homework.framework.pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;


import java.util.List;

public class SearchPage extends BasePage {
    @FindBy(xpath = "//div[contains(@class, 'filter-block')]")
    private List<WebElement> filterBlocks; //Цена /div[contains(string(),'Цена')]

    @FindBy(xpath = "//label//span")
    private List<WebElement> checkboxes;

    @FindBy(xpath = "//div[@data-widget='searchResultsFiltersActive']//span")
    private List<WebElement> activeFilters;

    @FindBy(xpath = "//div[@style='grid-column-start: span 12;']/div/div")
    private List<WebElement> items;

    @FindBy(xpath = "//div[@data-widget='addressPopup']")
    private List<WebElement> addressPopup;

    @FindBy(xpath = "//a[@data-widget='cart']")
    private WebElement cartButton;



    public SearchPage fillFilters(String nameOfBlock, String value) {
        switch (nameOfBlock){
            case "Цена до":
                Assert.assertEquals("Цена \"до\" не совпадает", value, setPriceTo(value));
                break;

            case "Высокий рейтинг":
                getOnCheckbox(nameOfBlock);
                break;

            case "Беспроводные интерфейсы":
                getOnCheckbox(value);
                break;

            case "Брэнд":

                break;
        }
        return this;
    }


    public SearchPage addEvenProductToCart(int count) {
        int countOfProductsInCart = 0;

        for (int i = 1; countOfProductsInCart < count; i+=2) {
            WebElement currentProduct = items.get(i);
            if (addToCart(currentProduct, countOfProductsInCart)) {
                rememberProduct(currentProduct);
                countOfProductsInCart++;
            }
        }
        return this;
    }

    public CartPage getCartPage() {
        scrollToElement(cartButton);
        cartButton.click();
        return app.getCartPage();
    }

    private String setPriceTo(String price) {
        for (WebElement element : filterBlocks) {
            WebElement priceElement = element.findElement(By.xpath("./div"));
            if (priceElement.getText().trim().equalsIgnoreCase("цена")) {
                priceElement = element.findElement(By.xpath(".//input[@qa-id='range-to']"));
                priceElement.sendKeys(Keys.CONTROL + "a");
                priceElement.sendKeys(price + "\n");
                return priceElement.getAttribute("value");
            }
        }
        Assert.fail("Поле \"Цена\" не найдено");
        return "";
    }

    private SearchPage getOnCheckbox(String name) {
        for (WebElement checkbox: checkboxes) {
            if (checkbox.getText().toLowerCase().contains(name.toLowerCase())) {
                checkbox.click();
                waitForFilter(name);
                return this;
            }
        }
        Assert.fail("Чекбокс с названием: " + name + " не найден");
        return this;
    }

    private void waitFor1Sec(long value) {
        try {
            Thread.sleep(value);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private boolean addToCart(WebElement product, int countOfProducts) {
        if (isElementExistInWebElement("В корзину", product)) {
            WebElement addButton = product.findElement(By.xpath(".//div[text()='В корзину']/../..")); //проверка наличия кнопки
            waitElementToBeVisible(addButton);
            if (!isElementExistInWebElement("Express",product)) {  //ИЗМЕНЕНО
                addButton.click();
                wait.until(ExpectedConditions.textToBePresentInElement(
                        cartButton.findElement(By.xpath("./span[contains(@class,'caption')]")), String.valueOf(countOfProducts))); //перенести в addToCart
                return true;
            }

        }
        return false;
    }

    private void rememberProduct(WebElement element) {
        String currentProductName = element.findElement(By.xpath(".//a[contains(text(),*)]")).getText();
        String currentProductPrice = element.findElement(By.xpath("./div/a/div/span")).getText();
        Product.listOfProducts.add(new Product(currentProductName, currentProductPrice));
    }

    private void waitForFilter(String filter) {
        boolean flag = true;
        int iteration = 0;
        while (flag && iteration < 10) {
            for (WebElement element : activeFilters) {
                if (element.getText().toLowerCase().contains(filter.toLowerCase())) {
                    flag = false;
                    break;
                }
            }
            waitFor1Sec(500);
            iteration++;
        }
    }

}

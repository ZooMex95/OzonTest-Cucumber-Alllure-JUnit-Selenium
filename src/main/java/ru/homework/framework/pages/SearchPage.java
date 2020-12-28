package ru.homework.framework.pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
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

    static boolean flag = true;



    public SearchPage setPriceTo(String price) {
        for (WebElement element : filterBlocks) {
            WebElement priceElement = element.findElement(By.xpath("./div"));
            if (priceElement.getText().trim().equalsIgnoreCase("цена")) {
                priceElement = element.findElement(By.xpath(".//input[@qa-id='range-to']"));
                priceElement.sendKeys(Keys.CONTROL + "a");
                priceElement.sendKeys(price + "\n");
                Assert.assertEquals("Цена \"до\" не совпадает", price, priceElement.getAttribute("value"));
                return this;
            }
        }
        Assert.fail("Поле \"Цена\" не найдено");
        return this;
    }

    public SearchPage getOnCheckbox(String name) {
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

    public SearchPage addEvenProductToCart(int count) {
        int countOfProductsInCart = 0;

        for (int i = 1; countOfProductsInCart < count; i+=2) {
            WebElement currentProduct = items.get(i);
            rememberProduct(currentProduct);
            addToCart(currentProduct, flag);
            countOfProductsInCart++;
            wait.until(ExpectedConditions.textToBePresentInElement(
                    cartButton.findElement(By.xpath("./span[contains(@class,'caption')]")), String.valueOf(countOfProductsInCart)));
            //waitFor1Sec();
        }
        //System.out.println("В сеарч пейдж: \n" + Product.listOfProducts);
        return this;
    }

    public CartPage getCartPage() {
        scrollToElement(cartButton);
        cartButton.click();
        return app.getCartPage();
    }

//a[@data-widget='cart']/


    private void waitFor1Sec() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void addToCart(WebElement product, boolean flag) {
        WebElement addButton = product.findElement(By.xpath(".//div[text()='В корзину']/../.."));
        waitElementToBeClickable(addButton);
        if (isExpress(product) && flag) {
            addButton.click();
            fillAddressPopup();
            SearchPage.flag = false;
        } else {
            addButton.click();
        }
    }

    private boolean isExpress(WebElement element) {
        List<WebElement> express = element.findElements(By.xpath(".//section/div/span/span"));
        return express.stream().anyMatch(l-> l.getText().equals("Express"));

    }

    private void rememberProduct(WebElement element) {
        String currentProductName = element.findElement(By.xpath(".//a[contains(text(),*)]")).getText();
        String currentProductPrice = element.findElement(By.xpath("./div/a/div/span")).getText();
        Product.listOfProducts.add(new Product(currentProductName, currentProductPrice));
    }

    private void fillAddressPopup() {
        WebElement inputField = addressPopup.get(0)
                .findElement(By.xpath(".//input"));
        waitFor1Sec();
        inputField.sendKeys("триумфальная площадь 1");
        waitElementToBeClickable(inputField.findElement(By.xpath("./../../ul/li")));
        inputField.findElement(By.xpath("./../../ul/li")).click();
        waitElementToBeClickable(inputField.findElement(By.xpath("./../../../..//button")));
        inputField.findElement(By.xpath("./../../../..//button")).click();
        wait.until(ExpectedConditions.invisibilityOf(inputField));
        //waitFor1Sec();
    }

    private void waitForFilter(String filter) {
        boolean flag = true;

        while (flag) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (WebElement element : activeFilters) {
                if (element.getText().toLowerCase().contains(filter.toLowerCase())) {
                    flag = false;
                    break;
                }
            }
        }
    }

    //    public SearchPage addItemsToCart(int count) {
//        int countOfProduct = 0;
//        if (countOfFilters > 1) {
//            waitForFilter();
//        }
//        for (int i = 1; countOfProduct < count; i+=2) {
//            List<WebElement> itemButtons = items.get(i).findElements(By.xpath("./div/div/div/div/div/button"));
//            for (WebElement element: itemButtons) {
//                if (element.getText().equalsIgnoreCase("в корзину")) {
//                    try {
//                        scrollToCenterElement(element);
//                        element.click();
//                        waitFor1Sec();
//                    } catch (ElementClickInterceptedException e) {
//                        address.sendKeys("триумфальная площадь 1");
//                        address.findElement(By.xpath("./../../ul/li")).click();
//                        address.findElement(By.xpath("./../../../..//button")).click();
//
//                    }
//                }
//
//                }
//        }
//        return this;
//    }

//    public SearchPage addProductsToCart(int count) {
//        int countOfProduct = 0;
//        if (countOfFilters > 1) {
//          //  waitForFilter();
//        }
//        for (int i = 1; countOfProduct <= count; i+=2) {
//            WebElement itemButton = items.get(i).findElement(By.xpath("./div/div/div/div/div/button"));
//                    try {
//                        scrollToCenterElement(itemButton);
//                        itemButton.click();
//                        waitFor1Sec();
//                    } catch (ElementClickInterceptedException e) {
//                        address.sendKeys("триумфальная площадь 1");
//                        address.findElement(By.xpath("./../../ul/li")).click();
//                        address.findElement(By.xpath("./../../../..//button")).click();
//                        waitFor1Sec();
//
//                        //element.click();// StaleElementReferenceException, осле уборки арки с адресом элемент переотрисовывается
//
//                    }
//            WebElement itemName = items.get(i).findElement(By.xpath("./div/div/a"));
//            WebElement itemPrice = items.get(i).findElement(By.xpath("./div/a/div/span"));
//            Product.listOfProducts.add(new Product(itemName.getText(),
//                    Integer.parseInt(itemPrice.getText().replaceAll("\\D", ""))));
//            countOfProduct++;
//        }
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        Product.listOfProducts.forEach((n) -> System.out.println(n.name + " " + n.price));
//        //scrollToElement();
//        return this;
//    }


}

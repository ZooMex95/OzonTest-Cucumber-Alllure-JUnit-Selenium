
package ru.homework.framework.pages;

import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.homework.framework.managers.TestPropManager;


import java.util.List;
import java.util.concurrent.TimeUnit;

import static ru.homework.framework.managers.DriverManager.getDriver;
import static ru.homework.framework.utils.PropConst.IMPLICITLY_WAIT;

public class SearchPage extends BasePage {
    @FindBy(xpath = "//div[contains(@class, 'filter-block')]")
    private List<WebElement> filterBlocks;

    @FindBy(xpath = "//label//span")
    private List<WebElement> checkboxes;

    @FindBy(xpath = "//div[@data-widget='searchResultsFiltersActive']//span")
    private List<WebElement> activeFilters;

    @FindBy(xpath = "//div[@style='grid-column-start: span 12;']/div/div")
    private List<WebElement> items;

    @FindBy(xpath = "//div[contains(text(),'Бренды')]/following-sibling::div/span")
    private WebElement seeAllButton;

    @FindBy(xpath = "//div[contains(text(),'Бренды')]/..")
    private WebElement brands;

    @FindBy(xpath = "//a[@data-widget='cart']")
    private WebElement cartButton;

//    @FindBy(xpath = "//div[@data-widget='megaPaginator']//div[not(@style)]") // /a[text()='2']
//    private WebElement selectPage;

    @FindBy(xpath = "//div[@data-widget='megaPaginator']/div/div/div/div/a")
    private List<WebElement> pages;



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

            case "Бренд":
                setBrand(value);
                break;
        }
        return this;
    }

    public SearchPage addProductsToCart(String value) {
        if (value.equals("все")) {
            addEvenProductToCart(value);
        } else {
            addEvenProductToCart(Integer.parseInt(value));
        }
        return this;
    }

    public void addEvenProductToCart(String count) {
        int countOfProductsInCart = 0;
        int pageNumber = 1;
        boolean flag = false;
        do {
            for (int i = 1; i < items.size(); i += 2) {
                WebElement currentProduct = items.get(i);
                if (addToCart(currentProduct, countOfProductsInCart)) {
                    rememberProduct(currentProduct);
                    countOfProductsInCart++;
                }
            }
            pageNumber++;
            if (pages.size() >= pageNumber) {
                String className = pages.get(pageNumber - 1).getAttribute("className");
                pages.get(pageNumber - 1).click();
                flag = true;
                wait.until(ExpectedConditions.not(ExpectedConditions.attributeToBe(
                        pages.get(pageNumber - 1), "className", className)));
            } else {
                flag = false;
            }
        } while (flag);
//        do {
//            for (int i = 1; i < items.size(); i += 2) {
//                WebElement currentProduct = items.get(i);
//                if (addToCart(currentProduct, countOfProductsInCart)) {
//                    rememberProduct(currentProduct);
//                    countOfProductsInCart++;
//                }
//            }
//            pageNumber++;
//            if (flag) { //isNextPageExist(selectPage, String.valueOf(pageNumber))
//                changePage(pageNumber);
//                flag = false;
//            }
//        } while (isNextPageExist(selectPage, String.valueOf(pageNumber)));

    }


    public void addEvenProductToCart(int count) {

        int countOfProductsInCart = 0;
        for (int i = 1; countOfProductsInCart < count; i+=2) {
            WebElement currentProduct = items.get(i);
            if (addToCart(currentProduct, countOfProductsInCart)) {
                rememberProduct(currentProduct);
                countOfProductsInCart++;
            }
        }
    }



    public CartPage getCartPage() {
        scrollToElement(cartButton);
        cartButton.click();
        return app.getCartPage();
    }

    private void setBrand(String brandName) {
        int size = checkboxes.size();
        seeAllButton.click();
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//label//span"),size));
        if (isElementExistInWebElement(brandName, brands)) {
            brands.findElement(By.xpath(".//input[not(@type='checkbox')]")).sendKeys(brandName);
            brands.findElement(By.xpath(".//following::span[text()='" + brandName + "']")).click();
            waitForFilter(brandName);
            seeAllButton.click();
            return;
        }
        Assert.fail("Бренд: " + brandName + " не найден");
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

    private void getOnCheckbox(String name) {
//        for (WebElement checkbox: checkboxes) {
//            if (checkbox.getText().toLowerCase().contains(name.toLowerCase())) { //StaleElementReferenceException и периодически отмечает уцененный товар
//                checkbox.click();
//                waitForFilter(name);
//                return;
//            }
//        }
        for (int i = 0; i < checkboxes.size(); i++) {
            if (checkboxes.get(i).getText().toLowerCase().contains(name.toLowerCase())) {
                checkboxes.get(i).click();
                waitForFilter(name);
                return;
            }
        }
        Assert.fail("Чекбокс с названием: " + name + " не найден");
    }

    private boolean isNextPageExist(WebElement element, String value) {
        boolean flag = false;
        try {
            getDriver().manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
            scrollToElement(element.findElement(By.xpath("./a[text()='" + value + "']")));
            flag = true;
        } catch (NoSuchElementException ignored) {

        } finally {
            getDriver().manage().timeouts().implicitlyWait(Integer.parseInt(
                    TestPropManager.getTestPropManager().getProperty(IMPLICITLY_WAIT)), TimeUnit.SECONDS);

        }
        return flag;
    }

    private void waitFor1Sec(long value) {
        try {
            Thread.sleep(value);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private boolean addToCart(WebElement product, int countOfProducts) {
        if (!isElementExistInWebElement("Express",product) && !isElementExistInWebElement("Ozon Global", product)) {
            if (isElementExistInWebElement("В корзину", product)) { //поменял if местами
                WebElement addButton = product.findElement(By.xpath(".//div[text()='В корзину']/../.."));
                scrollToElement(addButton);
                waitElementToBeVisible(addButton);
                addButton.click();
                wait.until(ExpectedConditions.textToBePresentInElement(
                        cartButton.findElement(By.xpath("./span[contains(@class,'caption')]")), String.valueOf(countOfProducts)));
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
                    return; //было break
                }
            }
            waitFor1Sec(500);
            iteration++;
        }
        Assert.fail("Истекло время ожидания фильтра: " + filter);
    }


//    private void changePage(int value) {
//        String className = selectPage.findElement(By.xpath("./a[text()='" + value + "']")).getAttribute("className");
//        System.out.println(className);
//        selectPage.findElement(By.xpath("./a[text()='" + value + "']")).click();
//        wait.until(ExpectedConditions.not(ExpectedConditions.attributeToBe(
//                selectPage.findElement(By.xpath("./a[text()='" + value + "']")), "className", className
//        )));
//    }

}

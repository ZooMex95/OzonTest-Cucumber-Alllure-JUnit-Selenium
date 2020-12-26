package ru.homework.framework.pages;


import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


import java.util.ArrayList;
import java.util.List;

public class SearchPage extends BasePage {
    @FindBy(xpath = "//div[contains(@class, 'filter-block')]")
    private List<WebElement> filterBlocks; //Цена /div[contains(string(),'Цена')]

    @FindBy(xpath = "//div[contains(@class, 'filter-block')]/div[@value]")
    private List<WebElement> checkboxes;

    @FindBy(xpath = "//div[@data-widget='searchResultsFiltersActive']//span")
    private List<WebElement> activeFilters;

    @FindBy(xpath = "//div[@style='grid-column-start: span 12;']/div/div")
    private List<WebElement> items;

    @FindBy(xpath = "//div[@data-widget='addressPopup']//input")
    private WebElement address;

    private int countOfFilters = 1;


    public SearchPage useFilters(String nameOfBLock, String value) {
        switch (nameOfBLock.toLowerCase()) {
            case "цена до":
                setPriceTo(value);
                break;
            case "высокий рейтинг":
                setHighRating(nameOfBLock);
                break;
            case "nfc":
                setNfc();
                break;
            case "бренды":
                setBrand(nameOfBLock);
                break;
        }
        countOfFilters++;
        return this;
    }

    public SearchPage addProductsToCart(int count) {
        int countOfProduct = 0;
        if (countOfFilters > 1) {
            waitForFilter();
        }
        for (int i = 1; countOfProduct <= count; i+=2) {
            List<WebElement> itemButtons = items.get(i).findElements(By.xpath("//button/div/div"));
            for (WebElement element: itemButtons) {
                if (element.getText().equalsIgnoreCase("в корзину")) {
                    try {
                        scrollToCenterElement(element);
                        element.click();
                    } catch (ElementClickInterceptedException e) {
                        address.sendKeys("триумфальная площадь 1");
                        address.findElement(By.xpath("./../../ul/li")).click();
                        address.findElement(By.xpath("./../../../..//button")).click();

                        //element.click();// StaleElementReferenceException, осле уборки арки с адресом элемент переотрисовывается
                    }
                }
            }
            WebElement itemName = items.get(i).findElement(By.xpath("./div/div/a"));
            WebElement itemPrice = items.get(i).findElement(By.xpath("./div/a/div/span"));
            Product.listOfProducts.add(new Product(itemName.getText(),
                    Integer.parseInt(itemPrice.getText().replaceAll("\\D", ""))));
            countOfProduct++;
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Product.listOfProducts.forEach((n) -> System.out.println(n.name + " " + n.price));
        //scrollToElement();
        return this;
    }

    private void setPriceTo(String value) {
        for (WebElement element : filterBlocks) {
            WebElement priceElement = element.findElement(By.xpath("./div"));
            if (priceElement.getText().trim().equalsIgnoreCase("цена")) {     //div[contains(@class, 'filter-block')]   ./div[@unit='[object Object]']/div/div/input[@qa-id='range-to']
                priceElement = element.findElement(By.xpath(".//input[@qa-id='range-to']"));
                priceElement.sendKeys(Keys.CONTROL + "a");
                priceElement.sendKeys(value + "\n");
                Assert.assertEquals("Цена \"до\" не совпадает", value, priceElement.getAttribute("value"));
                break;
            }
        }
    }

    private void setHighRating(String nameOfBLock) {
        for (WebElement element : checkboxes) {
            WebElement highRatingElement = element.findElement(By.xpath(".//span"));
            if (highRatingElement.getText().trim().equalsIgnoreCase(nameOfBLock)) {
                highRatingElement = element.findElement(By.xpath(".//input"));
                action.moveToElement(highRatingElement).click().build().perform();
                break;
            }
        }
    }

    private void setNfc() {
        for (WebElement element : filterBlocks) {
            WebElement currentElement = element.findElement(By.xpath("./div"));
            if (currentElement.getText().trim().equalsIgnoreCase("Беспроводные интерфейсы")) {
                currentElement = currentElement.findElement(By.xpath("./..//input"));
                scrollToElement(currentElement);
                action.moveToElement(currentElement).click().build().perform();
                break;
            }
        }
//        try{
//            Thread.sleep(15000);
//        }catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    private void setBrand(String value) {


    }



    private void waitForFilter() {
        boolean flag = true;
        while (flag) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (countOfFilters == activeFilters.size() - 1) {
                flag = false;
            }

        }
    }
}

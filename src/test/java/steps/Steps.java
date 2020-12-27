package steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.ru.Когда;
import ru.homework.framework.managers.ManagerPages;

public class Steps {

    private ManagerPages app = ManagerPages.getManagerPages();

    @Когда("^Загружена стартовая страница$")
    public void getStartPage() {
        app.getStartPage();
    }

    @Когда("^Поиск по '(.*)'$")
    public void searchForProduct(String nameOfProduct) {
        app.getStartPage()
                .searchForProduct(nameOfProduct);
    }

    @Когда("^Ограничить цену до '(.*)'$")
    public void setPrice(String price) {
        app.getSearchPage()
                .setPriceTo(price);
    }

    @Когда("^Отметить чекбокс '(.*)'$")
    public void setCheckbox(String name) {
        app.getSearchPage()
                .getOnCheckbox(name);
    }

    @Когда("^wait$")
    public void waiter() {
        try {
            Thread.sleep(2000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Когда("^Добавить в корзину '(.*)' товаров$")
    public void addProductToCart(int count) {
        app.getSearchPage()
                .addEvenProductToCart(count);
    }

    @Когда("^Перейти в корзину и проверить добавленные товары$")
    public void getCartPage() {
        app.getSearchPage()
                .getCartPage()
                .checkCart();
    }

    @Когда("^Проверить текст '(.*)'$")
    public void checkCountOfItems(String countOfItems) {
        app.getCartPage()
                .checkText(countOfItems);
    }

}

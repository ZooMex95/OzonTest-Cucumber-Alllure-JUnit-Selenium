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

    @Когда("^Заполнить поля фильтр/значение$")
    public void fillFilters(DataTable dataTable) {
        dataTable.cells().forEach(
                raw -> app.getSearchPage().fillFilters(raw.get(0), raw.get(1))
        );
    }

    @Когда("^Добавить в корзину '(\\d+|все)' товар(?:ов|а|ы)$")
    public void addProductToCart(String count) {
        app.getSearchPage()
                .addProductsToCart(count);
    }

    @Когда("^Перейти в корзину и проверить добавленные товары$")
    public void getCartPage() {
        app.getSearchPage()
                .getCartPage()
                .checkCart();
    }

    @Когда("^Проверить количество товаров в корзине$")
    public void checkCountOfItems() {
        app.getCartPage()
                .checkText();
    }

    @Когда("^Удалить все товары из корзины и проверить ее$")
    public void deleteAllProducts() {
        app.getCartPage()
                .deleteProducts();
    }

    @Когда("^wait$")
    public void waiter() {
        try {
            System.out.println("WAIT!!!!");
            Thread.sleep(120000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

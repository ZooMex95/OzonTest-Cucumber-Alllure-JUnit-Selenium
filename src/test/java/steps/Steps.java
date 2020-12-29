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

    @Когда("^Добавить в корзину '(\\d+)' товаров$")
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

    @Когда("^Удалить все товары из корзины и проверить ее$")
    public void deleteAllProducts() {
        app.getCartPage()
                .deleteProducts();
    }

    @Когда("^wait$")
    public void waiter() {
        try {
            Thread.sleep(120000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

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
    public void searchForProduct(String nameOfProduct) throws InterruptedException {
        app.getStartPage()
                .searchForProduct(nameOfProduct);
        Thread.sleep(2000);
    }

    @Когда("^Использовать фильтр$")
    public void setPrice(DataTable dataTable) {
        dataTable.cells().forEach(
                raw -> {app.getSearchPage().useFilters(raw.get(0), raw.get(1));
                }
        );
    }

    @Когда("^Добавить в корзину '(.*)' товаров$")
    public void addProductToCart(int count) {
        app.getSearchPage()
                .addProductsToCart(count);
    }
}

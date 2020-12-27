package ru.homework.framework.managers;

import ru.homework.framework.pages.CartPage;
import ru.homework.framework.pages.SearchPage;
import ru.homework.framework.pages.StartPage;

public class ManagerPages {
    private static ManagerPages managerPages;

    StartPage startPage;

    SearchPage searchPage;

    CartPage cartPage;

    private ManagerPages() {

    }

    public static ManagerPages getManagerPages() {
        if (managerPages == null) {
            managerPages = new ManagerPages();
        }
        return managerPages;
    }

    public StartPage getStartPage() {
        if (startPage == null) {
            startPage = new StartPage();
        }
        return startPage;
    }

    public SearchPage getSearchPage() {
        if (searchPage == null) {
            searchPage = new SearchPage();
        }
        return searchPage;
    }

    public CartPage getCartPage() {
        if (cartPage == null) {
            cartPage = new CartPage();
        }
        return cartPage;
    }
}

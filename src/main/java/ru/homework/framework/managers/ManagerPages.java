package ru.homework.framework.managers;

import ru.homework.framework.pages.StartPage;

public class ManagerPages {
    private static ManagerPages managerPages;

    StartPage startPage;

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
}

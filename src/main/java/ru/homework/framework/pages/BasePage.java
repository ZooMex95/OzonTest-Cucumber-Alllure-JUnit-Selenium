package ru.homework.framework.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.homework.framework.managers.ManagerPages;

import static ru.homework.framework.managers.DriverManager.*;

public class BasePage {
    protected ManagerPages app = ManagerPages.getManagerPages();

    protected WebDriverWait wait = new WebDriverWait(getDriver(), 10, 1000);

    protected JavascriptExecutor js = (JavascriptExecutor)getDriver();

    public BasePage() {
        PageFactory.initElements(getDriver(), this);
    }
}

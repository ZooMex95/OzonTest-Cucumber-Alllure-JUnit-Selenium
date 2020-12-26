package ru.homework.framework.managers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import static ru.homework.framework.utils.PropConst.*;

public class DriverManager {

    private static WebDriver driver;

    private static TestPropManager props = TestPropManager.getTestPropManager();

    private DriverManager(){}

    public static void initDriver() {
        System.setProperty("webdriver.chrome.driver", props.getProperty(PATH_CHROME_DRIVER));
        driver = new ChromeDriver();

    }

    public static WebDriver getDriver() {
        if (driver == null) {
            initDriver();
        }

        return driver;
    }

    public static void quitDriver() {
        driver.quit();
        driver = null;
    }
}

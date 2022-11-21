package utils;

import org.openqa.selenium.WebDriver;

public class WebDriverManager {
    private static final ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<>();
    WebDriverFactory webDriverFactory = new WebDriverFactory();

    public void setupDriver(String driverName) {
        WebDriver driver = webDriverFactory.createWebDriver(driverName);
        if (threadLocalDriver.get() == null) {
            threadLocalDriver.set(driver);
        }
        System.out.println("Before Test Thread ID: " + Thread.currentThread().getId());
    }

    //get thread-safe driver
    public static WebDriver getDriver() {
        return threadLocalDriver.get();
    }

    public static void closeDriver() {
        getDriver().quit();
        if (threadLocalDriver.get() != null) {
            threadLocalDriver.remove();
        }
    }
}

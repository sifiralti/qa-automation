package ui.pages;

import com.qa.utils.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    private final WebDriver driver = DriverFactory.get();

    private final By username = By.id("username");
    private final By password = By.id("password");
    private final By loginBtn = By.cssSelector("button[type='submit']");

    public void open() {
        driver.get("https://the-internet.herokuapp.com/login");
    }

    public void login(String user, String pass) {
        driver.findElement(username).sendKeys(user);
        driver.findElement(password).sendKeys(pass);
        driver.findElement(loginBtn).click();
    }
}

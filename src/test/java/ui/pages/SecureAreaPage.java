package ui.pages;

import com.qa.utils.DriverFactory;
import org.openqa.selenium.By;

public class SecureAreaPage {
    private final By flash = By.id("flash");

    public String successMessage() {
        return DriverFactory.get().findElement(flash).getText();
    }
}

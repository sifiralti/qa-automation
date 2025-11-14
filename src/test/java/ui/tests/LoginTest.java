package ui.tests;

import com.qa.core.BaseUiTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import ui.pages.LoginPage;
import ui.pages.SecureAreaPage;

public class LoginTest extends BaseUiTest {

    @Test
    public void validLogin_shouldShowSuccess() {
        LoginPage login = new LoginPage();
        login.open();
        login.login("tomsmith", "SuperSecretPassword!");
        SecureAreaPage secure = new SecureAreaPage();
        Assert.assertTrue(secure.successMessage().contains("You logged into a secure area!"));

    }
}

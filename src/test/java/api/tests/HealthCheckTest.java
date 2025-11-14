package api.tests;

import com.qa.utils.ApiClient;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

public class HealthCheckTest {

    @Test
    public void healthEndpoint_shouldReturn200() {
        try {
            // httpstat.us için /200 endpoint'i
            Response response = ApiClient.get("/200");
            Assert.assertEquals(response.getStatusCode(), 200,
                    "Health endpoint beklenen 200 kodunu döndürmedi");
        } catch (Exception e) {
            // Dış servis yanıt vermezse testi fail etme, sadece SKIP et
            throw new SkipException("Health endpoint'e ulaşılamadı: " + e.getClass().getSimpleName()
                    + " - " + e.getMessage());
        }
    }
}

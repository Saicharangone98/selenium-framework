package api;

import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.BaseApiTest;

import static io.restassured.RestAssured.given;

public class CustomerApiTest extends BaseApiTest {

    @Test
    public void validateResponseForValidCustomer() {
        Response response = given()
                .baseUri(BASE_URL)
                .when()
                .get("/customers/12212");
        Assert.assertEquals(response.getStatusCode(), 200, "Bad response");

        XmlPath responseBody = response.getBody().xmlPath();
        Assert.assertEquals(responseBody.getString("customer.id"), "12212", "Id not matched");
        Assert.assertEquals(responseBody.getString("customer.firstName"), "John", "First Name not matched");
        Assert.assertEquals(responseBody.getString("customer.lastName"), "Smith", "Last Name not matched");
        Assert.assertEquals(responseBody.getString("customer.ssn"), "622-11-9999", "ssn not matched");

    }

    @Test
    public void validateResponseForInvalidCustomer(){
        Response response = given()
                .baseUri(BASE_URL)
                .when()
                .get("/customers/99999");

        Assert.assertEquals(response.getStatusCode(), 400, "Invalid response");

        String responseBody = response.getBody().asString();
        Assert.assertEquals(responseBody,"Could not find customer #99999","Invalid response");

    }
}

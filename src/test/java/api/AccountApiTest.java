package api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AccountApiTest extends BaseApi{

    @Test
    public void testGetCustomerAccounts() {
        int customerId = 12212;

        Response response = RestAssured.given()
                .spec(getRequestSpec())
                .pathParam("customerId",customerId)
                .when()
                .get("/customers/{customerId}/accounts")
                .then()
                .spec(getResponseSpec(200))
                .extract().response();

        int accountId = response.jsonPath().getInt("[0].id");
        Assert.assertTrue(accountId>0,"Account number should be positive");
    }
}

package api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

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

    @Test
    public void testCustomerAccountsJsonSchema() {
        int customerId = 12212;

        Response response = RestAssured.given()
                .spec(getRequestSpec())
                .pathParam("customerId",customerId)
                .when()
                .get("/customers/{customerId}/accounts")
                .then()
                .spec(getResponseSpec(200))
                .body(matchesJsonSchemaInClasspath("schemas/account-schema.json"))
                .extract().response();

        int accountId = response.jsonPath().getInt("[1].id");
        Assert.assertTrue(accountId>0,"Account number should be positive");
    }
}

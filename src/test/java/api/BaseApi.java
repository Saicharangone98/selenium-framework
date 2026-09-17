package api;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.*;
import io.restassured.specification.*;

public class BaseApi {
    public static RequestSpecification getRequestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri("https://parabank.parasoft.com/parabank/services/bank")
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .log(LogDetail.URI)
                .log(LogDetail.METHOD)
                .build();
    }

    public static ResponseSpecification getResponseSpec(int expectedStatuscode) {
        return new ResponseSpecBuilder()
                .expectStatusCode(expectedStatuscode)
                .log(LogDetail.STATUS)
                .build();
    }
}

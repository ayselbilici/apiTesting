import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class apiTest {

    @Test
    public void getRequest() {
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.baseURI = "https://gorest.co.in/public/v1/users";

        Response response = given()
                .when()
                .get("")
                .then()
                .extract()
                .response();

        int statusCode = response.getStatusCode();
        Assert.assertEquals(200, statusCode);
        String responseBody = response.getBody().asString();
        JsonPath jsonPath = new JsonPath(responseBody);

        List<Map<String, ?>> dataList = jsonPath.getList("data");
        for (Map<String, ?> data : dataList) {
            String id = String.valueOf(data.get("id"));
            if (id.length() != 4) {
                System.out.println("Wrong ID: " + id);
            }
        }
        System.out.println("Response Body: " + responseBody);
    }

    @Test
    public void postRequest() {
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.baseURI = "http://gorest.co.in/public/v1/users";

        String requestBody = "{\"email\":\"test.test@test.com\", \"name\":\"test\", \"gender\":\"female\", \"status\":\"active\"}";
        String accessToken = "1db9c9b6c959682be7c96f74ca532c3cb0bd331f46b86a92602f8d319481b6f5";
        String contentType = "application/json";

        Response response = given()
                .header("Access-token", accessToken)
                .header("Content-Type", contentType)
                .body(requestBody)
                .when()
                .post("")
                .then()
                .extract()
                .response();

        int statusCode = response.getStatusCode();
        Assert.assertEquals(200, statusCode);
        String responseBody = response.getBody().asString();

        System.out.println("Response Body: " + responseBody);
    }

    @Test
    public void failedPostRequest() {
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.baseURI = "http://gorest.co.in/public/v1/users";

        String requestBody = "{\"email\":\"test.test@test.com\", \"name\":\"test\", \"gender\":\"female\", \"status\":\"active\"}";
        String accessToken = "1db9c9b6c959682be7c96f74ca532c3cb0bd331f46b86a92602f8d319481b6f5";
        String contentType = "application/json";
        String responseBodyMessage= "{\"message\":\"has already been taken\"}";

        Response response = given()
                .header("Access-token", accessToken)
                .header("Content-Type", contentType)
                .body(requestBody)
                .when()
                .post("")
                .then()
                .extract()
                .response();

        int statusCode = response.getStatusCode();
        Assert.assertEquals(200, statusCode);
        String responseBody = response.getBody().asString();

        System.out.println("Response Body: " + responseBody);
        Assert.assertEquals(responseBodyMessage,responseBody);

    }

}


import org.junit.Test;

import static io.restassured.RestAssured.given;

public class GetTests {

    @Test
    public void collectionResourceOK() {
        given().header("content-type", "application/json")
                .param("status", "pending")
                .get("https://petstore.swagger.io/v2/pet/findByStatus?status=pending")
                .then().log().body()
                .statusCode(200);
    }
}

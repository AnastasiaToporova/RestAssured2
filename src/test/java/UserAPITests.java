import com.dto.*;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class UserAPITests {
    private static RequestSpecification spec;
    private PostTests post = new PostTests();

    @BeforeClass
    public static void initSpec() {
        spec = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri("https://petstore.swagger.io/v2/user/")
                .addFilter(new ResponseLoggingFilter())
                .addFilter(new RequestLoggingFilter())
                .build();
    }

    private UserDto createDummyUser() {
        return new UserDto()
                .setLastname("Jonson")
                .setId(1140)
                .setPassword("password")
                .setUserName("hkbbkjb")
                .setEmail("mike@epam.com")
                .setPhone("0987896756")
                .setUserStatus(1)
                .setFirstName("Mike");
    }

    private ApiResponseDto login(String locationHeader, String username, String password) {
        return given()
                .spec(spec)
                .queryParam("username", username)
                .queryParam("password", password)
                .when()
                .get(locationHeader)
                .then()
                .statusCode(200)
                .extract().as(ApiResponseDto.class);
    }
    private UserDto getUserByUserName(String username) {
        return given()
                .spec(spec)
               .pathParam("username",username)
                .when()
                .get("{username}")
                .then()
                .statusCode(200)
                .extract().as(UserDto.class);
    }

    @Test
    public void checkUserApiResponse() throws InterruptedException {
       UserDto newUser = createDummyUser();
        Thread.sleep(2000);
       ApiResponseDto apiResponseDto = login("login", newUser.getUserName(), newUser.getPassword());
       String text = post.createResource("", newUser, spec);
        UserDto retrievedUser = getUserByUserName(newUser.getUserName());
    }
}

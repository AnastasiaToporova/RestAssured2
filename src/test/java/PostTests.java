import com.dto.Category;
import com.dto.PetDto;
import com.dto.Tags;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.awaitility.Awaitility;
import org.hamcrest.Matchers;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

public class PostTests {

    private static RequestSpecification spec;
    private static ResponseSpecification respec;
    private static Long MAX_TIMEOUT = 60000l;

    @BeforeClass
    public static void initSpec() {
        spec = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri("https://petstore.swagger.io/v2/pet/")
                .addFilter(new ResponseLoggingFilter())
                .addFilter(new RequestLoggingFilter())
                .build();
        respec = new ResponseSpecBuilder().expectResponseTime(Matchers.lessThan(MAX_TIMEOUT)).build();
    }

    @Test
    public void createPetAndCheckExistence() {
        PetDto newPet = createDummyPet();

        String petResourceLocation = createResource("", newPet, spec);
        PetDto retrievedPet = getResource(String.valueOf(newPet.getId()), PetDto.class, spec);
        assertEqualPet(newPet, retrievedPet);
        newPet.setName("kevin");
        assertEquals(putResource(newPet, spec).getStatusCode(), 200);
        assertEquals("Check that pet is deleted", deleteRequest(spec, newPet.getId()).getStatusCode(), 200);
        // PetDto deletedPet = getResource(String.valueOf(newPet.getId()), PetDto.class, spec);
        assertEquals(getPet(spec, newPet.getId()).statusCode(), 404);
    }

    private PetDto createDummyPet() {
        String[] photoUrl = new String[]{"string"};
        Tags tag = new Tags();

        tag.setId(6);
        tag.setName("doggy");
        Tags[] tags = new Tags[]{tag};
        Category category = new Category();
        category.setId(9);
        category.setName("dog");
        return new PetDto()
                .setName("miki")
                .setId(898)
                .setPhotoUrls(photoUrl)
                .setCategory(category)
                .setStatus("available")
                .setTags(tags);
    }

    public Response putResource(Object bodyPayload, RequestSpecification spec) {
        //  Awaitility.await().atMost(5, TimeUnit.SECONDS).until(() -> this.getStatus() == 200)
        return given()
                .spec(spec)
                .body(bodyPayload)
                .when()
                .put("")
                .then()
                .spec(respec)
                .extract().response();


    }

    public Response deleteRequest(RequestSpecification spec, int idPet) {
        return given()
                .spec(spec)
                .header("Content-type", "application/json")
                .when()
                .delete(String.valueOf(idPet))
                .then()
                .spec(respec)
                .extract().response();


    }

    public Response getPet(RequestSpecification spec, int idPet) {

        return given()
                .spec(spec)
                .header("Content-type", "application/json")
                .when()
                .get(String.valueOf(idPet))
                .then()
                .spec(respec)
                .extract().response();
    }

    public String createResource(String path, Object bodyPayload, RequestSpecification spec) {

        return given()
                .spec(spec)
                .body(bodyPayload)
                .when()
                .post(path)
                .then()
                .spec(respec)
                .statusCode(200)
                .extract().header("location");
    }

    public <T> T getResource(String locationHeader, Class<T> responseClass, RequestSpecification spec) {
        return given()
                .spec(spec)
                .when()
                .get(locationHeader)
                .then()
                .spec(respec)
                .statusCode(200)
                .extract().as(responseClass);
    }

    private void assertEqualPet(PetDto newPet, PetDto retrievedPet) {
        assertThat(retrievedPet.getId()).isEqualTo(newPet.getId());
        assertThat(retrievedPet.getName()).isEqualTo(newPet.getName());
        assertThat(retrievedPet.getStatus()).isEqualTo(newPet.getStatus());
    }
}


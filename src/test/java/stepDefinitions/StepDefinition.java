package stepDefinitions;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;


public class StepDefinition extends Utils {

	ResponseSpecification resSpec;
	RequestSpecification res;
	Response response;
	JsonPath js;
	static String place_id;

	TestDataBuild data = new TestDataBuild();

	@Given("^Add Place Payload with \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
	public void add_place_payload(String name, String language, String address) throws IOException {

		res = given().spec(requestSpecification())
				.body(data.addPlacePayload(name, language, address));
	}

	@Given("DeletePlace payload")
	public void delete_place_payload() throws IOException {
		res = given().spec(requestSpecification())
				.body(data.deletePlacePayload(place_id));

	}



	@When("^user calls \"([^\"]*)\" with \"([^\"]*)\" http request$")
	public void user_calls_with_post_http_request(String resource, String method) {

		APIResources resourceAPI = APIResources.valueOf(resource);
		System.out.println(resourceAPI.getResource());

		resSpec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();

		if(method.toUpperCase().equals("POST")) {
			response = res.when().post(resourceAPI.getResource());
		} else if(method.toUpperCase().equals("GET")) {
			response = res.when().get(resourceAPI.getResource());
		} else if(method.toUpperCase().equals("DELETE")) {
			response = res.when().delete(resourceAPI.getResource());
		}
	
		/* se quiser usar switch:
	 	switch (method.toUpperCase()) {
		case "GET":
			response = res.when().get(resourceAPI.getResource());
			break;
		case "DELETE":
			response = res.when().delete(resourceAPI.getResource());
			break;
		default:
			response = res.when().post(resourceAPI.getResource());
			break;
		}*/

	}
	@Then("^the API call should be successfull with response \"([^\"]*)\"$")
	public void the_api_call_should_be_successfull_with_response_200(int expectedResponse) throws Throwable {
		assertEquals(response.getStatusCode(),expectedResponse);
	}

	@And("^the \"([^\"]*)\" in the response body should be \"([^\"]*)\"$")
	public void the_status_in_the_response_body_should_be_ok(String returnParameter, String expectedResult) throws Throwable {
		assertEquals(getJsonPath(response, returnParameter).toString(),expectedResult);
	}

	@Then("verify place_Id created maps to \"([^\"]*)\" using \"([^\"]*)\"")
	public void verify_place_Id_created_maps_to_using_getPlaceAPI(String expectedName, String resource) throws IOException {

		place_id = getJsonPath(response, "place_id");
		res = given().spec(requestSpecification()).queryParam("place_id", place_id);
		user_calls_with_post_http_request(resource, "GET");
		assertEquals(getJsonPath(response, "name").toString(), expectedName);

	}

}
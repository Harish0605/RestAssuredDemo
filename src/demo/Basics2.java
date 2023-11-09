package demo;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.Assert;

import files.ReUsableMethods;
import files.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class Basics2 {

	public static void main(String[] args) throws IOException {
		// Validate if Add Place Api is working as expected

		// Given,When,Then
		// Given - all input details
		// When - submit the API - resource and http methods
		// Then - validate the response
		// log().all() this is used to see the logs in the output

		// Instead of sending payload - we can send the json file
		// convert the json file content to String so that we can send it in the body
		// to do that first convert convert to bytes and then bytes to String format
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = given().log().all().queryParam("key", "qaclick123").header("contentType", "application/json")
				.body(payload.addPlace()).when().post("maps/api/place/add/json").then().log().all().assertThat()
				.statusCode(200).body("scope", equalTo("APP")).header("Server", "Apache/2.4.52 (Ubuntu)").extract()
				.response().asString();
		System.out.println(response);

//		RestAssured.baseURI = "https://rahulshettyacademy.com";
//		String response = given().log().all().queryParam("key", "qaclick123").header("contentType", "application/json")
//				.body(new String(Files.readAllBytes(Paths.get("D:\\Automation\\API\\addplace.json")))).when()
//				.post("maps/api/place/add/json").then().log().all().assertThat().statusCode(200)
//				.body("scope", equalTo("APP")).header("Server", "Apache/2.4.52 (Ubuntu)").extract().response()
//				.asString();
//		System.out.println(response);

		JsonPath js1 = ReUsableMethods.rawToJson(response); // for parsing Json
		String placeId = js1.getString("place_id");
		System.out.println(placeId);

		// Add place -> Update place with New Address -> Get Place to validate if New
		// Address is present in response

		// Update place
		String newAddress = "Africa New Address";
		given().log().all().queryParam("key", "qaclick123").header("contentType", "application/json")
				.body("{\r\n" + "\"place_id\":\"" + placeId + "\",\r\n" + "\"address\":\"" + newAddress + "\",\r\n"
						+ "\"types\": \"renigunta shop\",\r\n" + "\"key\":\"qaclick123\"\r\n" + "}\r\n" + "")
				.when().put("maps/api/place/update/json").then().log().all().assertThat().statusCode(200)
				.body("msg", equalTo("Address successfully updated"));

		// Get Place
		String getPlaceResponse = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeId)
				.when().get("maps/api/place/get/json").then().log().all().assertThat().statusCode(200).extract()
				.response().asString();

		JsonPath js2 = ReUsableMethods.rawToJson(getPlaceResponse);
		String actualAddress = js2.getString("address");
		System.out.println(actualAddress);

		// TestNG
		Assert.assertEquals(actualAddress, newAddress);

	}

}

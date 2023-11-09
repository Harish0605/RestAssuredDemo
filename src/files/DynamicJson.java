package files;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class DynamicJson {

	@Test(dataProvider = "BooksData")
	public static void addBook(String isbn, int aisle) {
		RestAssured.baseURI = "https://rahulshettyacademy.com";

		String respaddBook = given().header("Content-Type", "application/json")
				.body(payload.addBookPayload(isbn, aisle)).when().post("/Library/Addbook.php").then().assertThat()
				.statusCode(200).body("Msg", equalTo("successfully added")).extract().response().asString();

		JsonPath js1 = ReUsableMethods.rawToJson(respaddBook);
		String id = js1.get("ID");
		String bookAddedMsg = js1.get("Msg");
		System.out.println("Harish your book is added : " + bookAddedMsg);

		// Delete Book
		String respDelBook = given().header("Content-Type", "application/json").body(payload.delBookPayload(id)).when()
				.post("Library/DeleteBook.php").then().extract().response().asString();
		JsonPath js2 = ReUsableMethods.rawToJson(respDelBook);
		String delMsg = js2.get("msg");
		System.out.println(delMsg);
	}

	@DataProvider(name = "BooksData")
	public Object[][] getData() {
		return new Object[][] { { generateUniqueIsbn(), generateUniqueAisle() },
				{ generateUniqueIsbn(), generateUniqueAisle() }, { generateUniqueIsbn(), generateUniqueAisle() } };
	}

	private String generateUniqueIsbn() {
		// Implement logic to generate a unique ISBN
		// For example, you can use a timestamp, random number, or any unique
		// identifier.
		return "ISBN_" + System.currentTimeMillis();
	}

	private int generateUniqueAisle() {
		// Implement logic to generate a unique aisle number
		// For example, you can use a random number or other unique logic.
		return (int) (Math.random() * 1000); // Generates a random aisle number.
	}
}

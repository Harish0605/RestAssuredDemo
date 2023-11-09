package demo;

import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files.ReUsableMethods;
import files.libraryPayload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class LibraryPractice {

	@Test(dataProvider = "BooksData")
	public static void libraryCollection(String isbn, int aisle, String authorName, String bookName) {
		// Add Book
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		// Name variable
		String addBookResp = given().header("Content-Type", "application/json")
				.body(libraryPayload.addBookPayload(isbn, aisle, authorName, bookName)).when()
				.post("/Library/Addbook.php").then().assertThat().statusCode(200).extract().response().asString();
		JsonPath js1 = ReUsableMethods.rawToJson(addBookResp);
		String addedMsg = js1.get("Msg");
		Assert.assertEquals(addedMsg, "successfully added");
		String id = js1.get("ID");
		System.out.println("ID: " + id);
		System.out.println("Author Name: " + authorName);
		// Get Book details using AuthorName
		given().param("AuthorName", authorName).when().get("/Library/GetBook.php").then().assertThat().statusCode(200);

		// Get Book details using ID
		given().param("ID", id).when().get("/Library/GetBook.php").then().assertThat().statusCode(200);

		// Delete Book
		String delBookResp = given().header("Content-Type", "application/json").body(libraryPayload.delBookPayload(id))
				.when().post("/Library/DeleteBook.php").then().assertThat().statusCode(200).extract().response()
				.asString();
		JsonPath js2 = ReUsableMethods.rawToJson(delBookResp);
		String deletedMsg = js2.get("msg");
		System.out.println(deletedMsg);
		Assert.assertEquals(deletedMsg, "book is successfully deleted");
	}

	@DataProvider(name = "BooksData")
	public static Object[][] generateData() {
		return new Object[][] { { generateIsbn(), generateAisle(), generateAuthorName(), generateBookName() },
				{ generateIsbn(), generateAisle(), generateAuthorName(), generateBookName() },
				{ generateIsbn(), generateAisle(), generateAuthorName(), generateBookName() } };
	}

	private static String generateIsbn() {
		return "HB" + System.currentTimeMillis();
	}

	private static int generateAisle() {
		return (int) (Math.random() * 100);
	}

	private static String generateAuthorName() {
		return "AuthorName" + System.currentTimeMillis();
	}

	private static String generateBookName() {
		return "BookName" + System.currentTimeMillis();
	}

}

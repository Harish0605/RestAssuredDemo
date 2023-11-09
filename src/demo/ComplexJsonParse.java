package demo;

import org.testng.Assert;

import files.payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

	public static void main(String[] args) {
		JsonPath js = new JsonPath(payload.coursePrice());

		// 1. Print No of courses returned by API
		int coursesCount = js.getInt("courses.size()"); // size() method is from JsonPath and can be applied only on
														// Array
		System.out.println(coursesCount);

		// 2.Print Purchase Amount
		int purchaseAmount = js.getInt("dashboard.purchaseAmount"); // to access child elements of node
		System.out.println(purchaseAmount);

		// 3. Print Title of the first course
		String firstCourseTitle = js.getString("courses[0].title");
		System.out.println(firstCourseTitle);

		// 4. Print All course titles and their respective Prices
		for (int i = 0; i < coursesCount; i++) {
			String courseTitle = js.get("courses[" + i + "].title");
			System.out.println(courseTitle);
			int price = js.get("courses[" + i + "].price");
			System.out.println(price);
//			System.out.println(js.getMap("courses[" + i + "]").get("title"));
//			System.out.println(js.getMap("courses[" + i + "]").get("price"));
		}

		// 5. Print no of copies sold by RPA Course
		for (int i = 0; i < coursesCount; i++) {
			String courseTitle = js.get("courses[" + i + "].title");
			if (courseTitle.equalsIgnoreCase("RPA")) {
				int copies = js.get("courses[" + i + "].copies");
				System.out.println("Copies sold of " + courseTitle + " are " + copies);
				// System.out.println(js.getMap("courses[2]").get("copies"));
				break;
			}
		}

		// 6. Verify if Sum of all Course prices matches with Purchase Amount
		int actualAmount = 0;
		for (int i = 0; i < coursesCount; i++) {
			int copies = js.get("courses[" + i + "].copies");
			int price = js.get("courses[" + i + "].price");
//			int price = (int) js.getMap("courses[" + i + "]").get("price");
//			int copies = (int) js.getMap("courses[" + i + "]").get("copies");
			actualAmount = actualAmount + Math.multiplyExact(price, copies);
		}
		Assert.assertEquals(actualAmount, purchaseAmount);

	}
}

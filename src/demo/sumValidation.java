package demo;

import org.testng.Assert;
import org.testng.annotations.Test;

import files.ReUsableMethods;
import files.payload;
import io.restassured.path.json.JsonPath;

public class sumValidation {

	@Test
	public void sumOfCourses() {
		// 6. Verify if Sum of all Course prices matches with Purchase Amount
		JsonPath js = ReUsableMethods.rawToJson(payload.coursePrice());
		int purchaseAmount = js.getInt("dashboard.purchaseAmount");
		int coursesCount = js.get("courses.size()");
		int actualAmount = 0;
		for (int i = 0; i < coursesCount; i++) {
			int copies = js.get("courses[" + i + "].copies");
			int price = js.get("courses[" + i + "].price");
			actualAmount = actualAmount + price * copies;
		}
		System.out.println(actualAmount);
		Assert.assertEquals(actualAmount, purchaseAmount);
	}
}

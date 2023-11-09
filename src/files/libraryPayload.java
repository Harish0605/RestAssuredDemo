package files;

public class libraryPayload {

	public static String addBookPayload(String isbn, int aisle, String authorName, String bookName) {
		return "{\r\n" + "\"name\":\"" + bookName + "\",\r\n" + "\"isbn\":\"" + isbn + "\",\r\n" + "\"aisle\":\""
				+ aisle + "\",\r\n" + "\"author\":\"" + authorName + "\"\r\n" + "}";

	}

	public static String delBookPayload(String id) {
		return "{\r\n" + " \r\n" + "\"ID\" : \"" + id + "\"\r\n" + " \r\n" + "} ";
	}

}

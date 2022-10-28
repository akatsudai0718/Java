package etc;

public class Validation {

	private static final String MAIL = "^[a-zA-Z0-9_+-]+(.[a-zA-Z0-9_+-]+)*@([a-zA-Z0-9][a-zA-Z0-9-]*[a-zA-Z0-9]*\\.)+[a-zA-Z]{2,}$";
	private static final String PASSWORD = "^[0-9a-zA-Z]{8,24}+$";

	private static boolean result = false;

	public static boolean getResult () {
		return result;
	}

	public static void setResult (boolean r) {
		result = r;
	}

	public static String moldText (String text) {
		text = trimContent(text);

		if (text.isEmpty()) {
			setResult(false);
			return null;
		}
		setResult(true);
		return text;
	}

	private static String trimContent (String text) {
		String tString = text.replaceFirst("^[\\h]+", "").replaceFirst("[\\h]+$", "");
		return tString;
	}

	public static boolean compareText (String text1, String text2) {
		return text1.equals(text2);
	}

	public static boolean isNumeric (String str) {
		return str.matches("^[0-9]+$") && !str.isEmpty();
	}

	public static boolean Mail(String str) {
		return str.matches(MAIL);
	}

	public static boolean Password(String str) {
		return str.matches(PASSWORD);
	}
}
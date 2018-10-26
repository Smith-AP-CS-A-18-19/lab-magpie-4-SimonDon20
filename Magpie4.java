/**
 * A program to carry on conversations with a human user.
 * This version:
 *<ul><li>
 * 		Uses advanced search for keywords
 *</li><li>
 * 		Will transform statements as well as react to keywords
 *</li></ul>
 * @author Laurie White
 * @version April 2012
 *
 */
public class Magpie4 {
	/**
	 * Get a default greeting
	 * @return a greeting
	 */
	public String getGreeting() {
		return "Hello, let's talk.";
	}

	/**
	 * Gives a response to a user statement
	 *
	 * @param statement
	 *            the user statement
	 * @return a response based on the rules given
	 */
	public String getResponse(String statement) {
		String response = "";
		if (statement.length() == 0) {
			response = "Say something, please.";
		} else if (findKeyword(statement, "no") >= 0) {
			response = "Why so negative?";
		} else if (findKeyword(statement, "mother") >= 0
				   || findKeyword(statement, "father") >= 0
				   || findKeyword(statement, "sister") >= 0
				   || findKeyword(statement, "brother") >= 0)
		{
			response = "Tell me more about your family.";
		}
		// Responses which require transformations
		else if (findKeyword(statement, "I want to", 0) >= 0) {
			response = transformIWantToStatement(statement);
		} else {
			// Look for a two word (you <something> me)
			// pattern
			int psn = findKeyword(statement, "you", 0);

			if (psn >= 0
					&& findKeyword(statement, "me", psn) >= 0)
			{
				response = transformYouMeStatement(statement);
			} else {
				response = getRandomResponse();
			}
		}
		if (findKeyword(statement, "I want", 0) >= 0) {
				response = transformIWantToStatement(statement);
		}
		return response;
	}

	/**
	 * Take a statement with "I want to <something>." and transform it into
	 * "What would it mean to <something>?"
	 * @param statement the user statement, assumed to contain "I want to"
	 * @return the transformed statement
	 */
	private String transformIWantToStatement(String statement) {
		//  Remove the final period, if there is one
		statement = statement.trim();
		String lastChar = statement.substring(0, statement.length() - 1);
		if (lastChar.equals(".")) {
			statement = statement.substring(0, statement.length() - 1);
		}
		int psn = findKeyword(statement, "I want to", 0);
		String restOfStatement = statement.substring(psn + 9).trim();
		return "What would it mean to " + restOfStatement + "?";
	}

	private String transformIWantStatement(String statement) {
		statement = statement.trim();
		String lastChar = statement.substring(0, statement.length() - 1);
		if (lastChar.equals(".")) {
			statement = statement.substring (0, statement.length() - 1);
		}
		int psn = findKeyword (statement, "I want", 0);
		String restOfStatement = statement.substring(psn + 6).trim();
		return "Would you really be happy if you had " + restOfStatement + "?";
	}

	private String transformYouMeStatement(String statement) {
		statement = statement.trim();
		String lastChar = statement.substring(0, statement.length() - 1);
		if (lastChar.equals(".")) {
			statement = statement.substring (0, statement.length() - 1);
		}
		int psnYou = findKeyword (statement, "you", 0);
		int psnMe = findKeyword (statement, "me", psnOfYou + 3);
		String restOfStatement = statement.substring(psnYou + 3, psnMe).trim();
		return "What makes you think that I  " + restOfStatement + " you?";
	}
}

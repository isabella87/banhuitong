package com.bht.banhuitong.shared;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * FieldVerifier validates that the name the user enters is valid.
 * </p>
 * <p>
 * This class is in the <code>shared</code> packing because we use it in both
 * the client code and on the server. On the client, we verify that the name is
 * valid before sending an RPC request so the user doesn't have to wait for a
 * network round trip to get feedback. On the server, we verify that the name is
 * correct to ensure that the input is correct regardless of where the RPC
 * originates.
 * </p>
 * <p>
 * When creating a class that is used on both the client and the server, be sure
 * that all code is translatable and does not use native JavaScript. Code that
 * is note translatable (such as code that interacts with a database or the file
 * system) cannot be compiled into client side JavaScript. Code that uses native
 * JavaScript (such as Widgets) cannot be run on the server.
 * </p>
 */
public class FieldVerifier {

	private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*?[a-zA-Z])(?=.*?[0-9])(?=.*?[^0-9a-zA-Z]).{8,18}$", Pattern.CASE_INSENSITIVE);
    private static final Pattern PASSWORD_PATTERN_2 = Pattern.compile("^[0-9a-zA-Z][0-9a-zA-Z\\\\!@#$&%*()+={}\\[\\]<>?]+$", Pattern.CASE_INSENSITIVE);
  
    
  /**
   * Verifies that the specified name is valid for our service.
   *
   * In this example, we only require that the name is at least four
   * characters. In your application, you can use more complex checks to ensure
   * that usernames, passwords, email addresses, URLs, and other fields have the
   * proper syntax.
   *
   * @param name the name to validate
   * @return true if valid, false if invalid
   */
  public static boolean isValidName(String name) {
    if (name == null) {
      return false;
    }
    return name.length() > 3;
  }
  
  public static boolean isValidCaptchaCode(String captchCode) {
	  if (captchCode == null) {
		  return false;
	  }
	  return !captchCode.isEmpty();
  }
  
  public static boolean isValidPassword(String password) {
	  
	  if (!test(PASSWORD_PATTERN, password) || !test(PASSWORD_PATTERN_2, password)) {
          return false;
      }
	  return true;
  }
  
  public static boolean test(
          final Pattern pattern,
          CharSequence value
  ) {
      if (value == null) {
          value = "";
      }

      final Matcher m = pattern.matcher(value);
      return m.matches() && m.start() == 0 && m.end() == value.length();
  }
  
}

package com.example.exercise.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class UserValidator {

	private static final String EMAIL_REGEX = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";

	public static boolean validateEmail(String email) {
		if (StringUtils.isBlank(email)) {
			return false;
		} else {
			Pattern pattern = Pattern.compile(EMAIL_REGEX,
					Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(email.trim());

			return matcher.matches();
		}
	}
}

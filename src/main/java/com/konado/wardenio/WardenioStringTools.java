package com.konado.wardenio;

import java.util.Random;

public class WardenioStringTools {

	public static String generateRandomCode(int targetStringLength) {

		int leftLimit = 97; // letter 'a'
		int rightLimit = 122; // letter 'z'

		return new Random().ints(leftLimit, rightLimit + 1).limit(targetStringLength).collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
	}
}

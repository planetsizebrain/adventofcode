package be.planetsizebrain.advent.day5;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.digest.DigestUtils;

public class Exercise2 {

	public static void main(String[] args) throws URISyntaxException, IOException, NoSuchAlgorithmException {
		StringBuilder password = new StringBuilder("        ");
		int index = 0;
		int nbrFound = 0;

		while (nbrFound < 8) {
			String text = "ffykfhsq" + index++;
			String hash = DigestUtils.md5Hex(text);

			if (hash.startsWith("00000")) {
				char c = hash.charAt(5);
				if (Character.isDigit(c)) {
					int position = Integer.parseInt("" + hash.charAt(5));
					if (position < 8 && password.charAt(position) == ' ') {
						password.setCharAt(position, hash.charAt(6));
						nbrFound++;
					}
				}
			}
		}

		System.out.println(password.toString());
	}
}
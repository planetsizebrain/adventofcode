package be.planetsizebrain.advent.day5;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.codec.digest.DigestUtils;

import com.google.common.io.Resources;

public class Exercise1 {

	public static void main(String[] args) throws URISyntaxException, IOException, NoSuchAlgorithmException {
		StringBuffer password = new StringBuffer();
		int index = 0;

		while (password.length() < 8) {
			String text = "ffykfhsq" + index++;
			String hash = DigestUtils.md5Hex(text);

			if (hash.startsWith("00000")) {
				password.append(hash.charAt(5));
			}
		}

		System.out.println(password.toString());
	}
}
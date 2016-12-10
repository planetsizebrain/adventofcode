package be.planetsizebrain.advent.day7;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import com.google.common.io.Resources;

public class Exercise2 {

	public static void main(String[] args) throws URISyntaxException, IOException {
		URL url = Resources.getResource("ipaddresses.txt");
		Stream<String> stream = Files.lines(Paths.get(url.toURI()));

		Long validCount = stream
				.filter(Exercise2::supportsSSL)
				.count();

		System.out.println(validCount);
	}

	private static boolean supportsSSL(String ipAddress) {
		List<String> supernetParts = new ArrayList<>();
		List<String> hypernetParts = new ArrayList<>();
		getParts(ipAddress, supernetParts, hypernetParts);

		List<String> supernetAbas = getABAs(supernetParts);
		List<String> hypernetAbas = getABAs(hypernetParts);

		return containsMatch(supernetAbas, hypernetAbas);
	}

	private static void getParts(String ipAddress, List<String> supernetParts, List<String> hypernetParts) {
		StringBuilder part = new StringBuilder();
		for (int i = 0; i < ipAddress.length(); i++) {
			char c = ipAddress.charAt(i);
			switch (c) {
				case '[': {
					supernetParts.add(part.toString());
					part = new StringBuilder();
					break;
				}
				case ']': {
					hypernetParts.add(part.toString());
					part = new StringBuilder();
					break;
				}
				default: part.append(c);
			}
		}
		supernetParts.add(part.toString());
	}

	private static List<String> getABAs(List<String> parts) {
		List<String> abas = new ArrayList<>();
		for (String part : parts) {
			if (part.length() > 2) {
				for (int i = 0; i < part.length() - 2; i++) {
					String subPart = part.substring(i, i + 3);
					String reverseSubPart = new StringBuilder(subPart).reverse().toString();

					if ((subPart.charAt(0) != subPart.charAt(1)) && subPart.equals(reverseSubPart)) {
						abas.add(subPart);
					}
				}
			}
		}

		return abas;
	}

	private static boolean containsMatch(List<String> supernetAbas, List<String> hypernetAbas) {
		return supernetAbas.stream()
				.anyMatch(aba -> hypernetAbas.contains(getBAB(aba)));
	}

	private static String getBAB(String aba) {
		StringBuilder bab = new StringBuilder();

		bab.append(aba.charAt(1));
		bab.append(aba.charAt(0));
		bab.append(aba.charAt(1));

		return bab.toString();
	}
}
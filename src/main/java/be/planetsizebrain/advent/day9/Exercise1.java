package be.planetsizebrain.advent.day9;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.io.Resources;

public class Exercise1 {

	public static void main(String[] args) throws URISyntaxException, IOException {
		URL url = Resources.getResource("compressed.txt");
		Stream<String> stream = Files.lines(Paths.get(url.toURI()));

		String compressed = stream.collect(Collectors.joining());
		StringBuilder uncompressed = new StringBuilder();
		StringBuilder markerBuilder = null;
		String marker = "";
		int index = 0;

		while (index < compressed.length()) {
			char c = compressed.charAt(index);
			switch (c) {
				case '(': {
					markerBuilder = new StringBuilder();
					break;
				}
				case ')': {
					if (markerBuilder != null) {
						marker = markerBuilder.toString();
						markerBuilder = null;
					}
					break;
				}
			}

			if (markerBuilder != null) {
				markerBuilder.append(c);
				index++;
			} else {
				if (marker.length() > 0) {
					int nbrOfChars = Integer.parseInt(marker.substring(1, marker.indexOf('x')));
					int multiply = Integer.parseInt(marker.substring(marker.indexOf('x') + 1));
					String part = compressed.substring(index + 1, index + 1 + nbrOfChars);

					for (int i = 0; i < multiply; i++) {
						uncompressed.append(part);
					}

					marker = "";
					index = index + nbrOfChars + 1;
				} else {
					uncompressed.append(c);
					index++;
				}
			}
		}

		System.out.println(uncompressed);
		System.out.println(uncompressed.length());
	}
}
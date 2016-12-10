package be.planetsizebrain.advent.day6;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.io.Resources;

public class Exercise2 {

	public static void main(String[] args) throws URISyntaxException, IOException {
		URL url = Resources.getResource("message.txt");
		Stream<String> stream = Files.lines(Paths.get(url.toURI()));

		SortedMap<String, Long>[] frequencies = new SortedMap[8];

		stream.forEach(message -> mapFrequencies(message, frequencies));

		StringBuilder decryptedMessage = new StringBuilder();
		for (SortedMap map : frequencies) {
			decryptedMessage.append(getMostFrequentCharacter(map));
		}

		System.out.println(decryptedMessage.toString());
	}

	private static String getMostFrequentCharacter(SortedMap<String, Long> map) {
		return map.entrySet().stream()
				.sorted(Map.Entry.comparingByValue())
				.limit(1)
				.map(Map.Entry::getKey)
				.collect(Collectors.joining());
	}

	private static void mapFrequencies(String message, SortedMap<String, Long>[] frequencies) {
		for (int i = 0; i < 8; i++) {
			String character = Character.toString(message.charAt(i));
			SortedMap<String, Long> map = frequencies[i];

			if (map == null) {
				map = new TreeMap<>();
				frequencies[i] = map;
			}

			Long count = map.get(character);
			if (count == null) {
				map.put(character, 1L);
			} else {
				map.put(character, count + 1);
			}
		}
	}
}
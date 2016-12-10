package be.planetsizebrain.advent.day4;

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

public class Exercise1 {

	public static void main(String[] args) throws URISyntaxException, IOException {
		URL url = Resources.getResource("rooms.txt");
		Stream<String> stream = Files.lines(Paths.get(url.toURI()));

		long sectorIdCount = stream
				.filter(Exercise1::isAValidRoom)
				.map(Exercise1::extractSectorId)
				.collect(Collectors.summingLong(Long::longValue));

		System.out.println(sectorIdCount);
	}

	private static boolean isAValidRoom(String roomName) {
		String[] parts = roomName.split("-");

		SortedMap<String, Long> frequencies = new TreeMap<>();
		for (int i = 0; i < parts.length - 1; i++) {
			String part = parts[i];
			for (int j = 0; j < part.length(); j++) {
				String character = Character.toString(part.charAt(j));

				Long count = frequencies.get(character);
				if (count == null) {
					frequencies.put(character, 1L);
				} else {
					frequencies.put(character, count + 1);
				}
			}
		}

		String checksum = parts[parts.length - 1].trim();
		checksum = checksum.substring(checksum.indexOf('[') + 1, checksum.length() - 1);
		String calculatedChecksum = frequencies.entrySet().stream()
				.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
				.limit(5)
				.map(Map.Entry::getKey)
				.collect(Collectors.joining());

		return checksum.equals(calculatedChecksum);
	}

	private static long extractSectorId(String roomName) {
		int start = roomName.lastIndexOf('-');
		int end = roomName.indexOf('[');

		return Long.parseLong(roomName.substring(start + 1, end));
	}
}
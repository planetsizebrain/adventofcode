package be.planetsizebrain.advent.day4;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.io.Resources;

public class Exercise2 {

	public static void main(String[] args) throws URISyntaxException, IOException {
		URL url = Resources.getResource("rooms.txt");
		Stream<String> stream = Files.lines(Paths.get(url.toURI()));

		Optional<Long> sectorId = stream
				.filter(Exercise2::isAValidRoom)
				.map(Exercise2::extractSectorId)
				.findFirst();

		System.out.println(sectorId);
	}

	private static boolean isAValidRoom(String roomName) {
		String[] parts = roomName.split("-");

		String text = Arrays.stream(parts)
				.limit(parts.length - 1)
				.collect(Collectors.joining(" "));

		long rotations = extractSectorId(roomName);
		text = rotate(text, rotations);

		return text.equals("northpole object storage");
	}

	private static long extractSectorId(String roomName) {
		int start = roomName.lastIndexOf('-');
		int end = roomName.indexOf('[');

		return Long.parseLong(roomName.substring(start + 1, end));
	}

	private static String rotate(String text, long rotations) {
		long shift = rotations % 26;

		StringBuffer sb = new StringBuffer();
		int len = text.length();
		for (int i = 0; i < len; i++){
			if (text.charAt(i) == ' ') {
				sb.append(' ');
			} else {
				char c = (char) (text.charAt(i) + shift);
				if (c > 'z') {
					sb.append((char) (text.charAt(i) - (26 - shift)));
				} else {
					sb.append((char) (text.charAt(i) + shift));
				}
			}
		}

		return sb.toString();
	}
}
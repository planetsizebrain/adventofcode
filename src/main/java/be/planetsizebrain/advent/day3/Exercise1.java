package be.planetsizebrain.advent.day3;

import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import com.google.common.base.Charsets;
import com.google.common.collect.Collections2;
import com.google.common.io.Resources;

public class Exercise1 {

	public static void main(String[] args) throws IOException, URISyntaxException {
		URL url = Resources.getResource("triangles.txt");
//		String text = Resources.toString(url, Charsets.UTF_8);

		Stream<String> stream = Files.lines(Paths.get(url.toURI()));

		long count = stream
				.filter(Exercise1::isValidTriangle)
				.count();

		System.out.println(count);
	}

	private static boolean isValidTriangle(String values) {
		List<Long> lengths = new ArrayList<>();
		lengths.add(Long.valueOf(values.substring(0, 5).trim()));
		lengths.add(Long.valueOf(values.substring(5, 10).trim()));
		lengths.add(Long.valueOf(values.substring(10).trim()));

		Collection<List<Long>> permutations = Collections2.permutations(lengths);

		long filtered = permutations.stream()
				.filter(Exercise1::isValidTriangle)
				.count();

		return filtered == permutations.size();
	}

	private static boolean isValidTriangle(List<Long> sides) {
		return sides.get(0) + sides.get(1) > sides.get(2);
	}
}
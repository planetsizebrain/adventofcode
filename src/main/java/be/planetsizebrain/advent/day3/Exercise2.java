package be.planetsizebrain.advent.day3;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.io.Resources;

public class Exercise2 {

	public static void main(String[] args) throws IOException, URISyntaxException {
		URL url = Resources.getResource("triangles.txt");
		Stream<String> stream = Files.lines(Paths.get(url.toURI()));

		List<List<Long>> swapped = swap(stream);

		long count = swapped.stream()
				.filter(Exercise2::isValidTriangle)
				.count();

		System.out.println(count);
	}

	private static List<List<Long>> swap(Stream<String> lines) {
		List<Long> column1 = new ArrayList<>();
		List<Long> column2 = new ArrayList<>();
		List<Long> column3 = new ArrayList<>();

		lines.forEach(line -> {
			column1.add(Long.valueOf(line.substring(0, 5).trim()));
			column2.add(Long.valueOf(line.substring(5, 10).trim()));
			column3.add(Long.valueOf(line.substring(10).trim()));
		});

		List<Long> allValues = new ArrayList<>();
		allValues.addAll(column1);
		allValues.addAll(column2);
		allValues.addAll(column3);

		List<List<Long>> swapped = new ArrayList<>();
		for (int i = 0; i < column1.size(); i++) {
			List<Long> line = new ArrayList<>();
			line.addAll(allValues.subList(i * 3, (i * 3) + 3));
			swapped.add(line);
		}

		return swapped;
	}

	private static boolean isValidTriangle(List<Long> lengths) {
		Collection<List<Long>> permutations = Collections2.permutations(lengths);

		long filtered = permutations.stream()
				.filter(Exercise2::checkSides)
				.count();

		return filtered == permutations.size();
	}

	private static boolean checkSides(List<Long> sides) {
		return sides.get(0) + sides.get(1) > sides.get(2);
	}
}
package be.planetsizebrain.advent.day8;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import com.google.common.io.Resources;

public class Exercise1 {

	public static void main(String[] args) throws URISyntaxException, IOException {
		URL url = Resources.getResource("screencommands.txt");
		Stream<String> stream = Files.lines(Paths.get(url.toURI()));

		boolean[][] screen = new boolean[6][50];

		stream
				.forEach(command -> executeCommand(command, screen));
//				.forEach(command -> printScreen(screen, command));

		System.out.println(count(screen));
	}

	private static void executeCommand(String command, boolean[][] screen) {
		if (command.startsWith("rect")) {
			int x = Integer.parseInt(command.substring(5, command.indexOf('x')));
			int y = Integer.parseInt(command.substring(command.indexOf('x') + 1));

			fill(x, y, screen);
		} else {
			if (command.contains("row")) {
				int y = Integer.parseInt(command.substring(13, command.indexOf(" by")));
				int n = Integer.parseInt(command.substring(command.indexOf("by ") + 3));

				rotateRow(y, n, screen);
			} else {
				int x = Integer.parseInt(command.substring(16, command.indexOf(" by")));
				int n = Integer.parseInt(command.substring(command.indexOf("by ") + 3));

				rotateColumn(x, n, screen);
			}
		}
	}

	private static void fill(int x, int y, boolean[][] screen) {
		for (int i = 0; i < y; i++) {
			for (int j = 0; j < x; j++) {
				screen[i][j] = true;
			}
		}
	}

	private static void rotateRow(int y, int n, boolean[][] screen) {
		boolean[] row = screen[y];
		n = n % 50;

		boolean[] temp = new boolean[row.length];
    	System.arraycopy(row, row.length - n, temp, 0, n);
		System.arraycopy(row, 0, row, n, row.length - n);
		System.arraycopy(temp, 0, row, 0, n);
	}

	private static void rotateColumn(int x, int n, boolean[][] screen) {
		n = n % 6;
		for (int rotations = 0; rotations < n; rotations++) {
			boolean[] pixels = new boolean[6];
			for (int i = 0; i < 6; i++) {
				pixels[i] = screen[i][x];
			}

			for (int i = 0; i < pixels.length; i++) {
				screen[i][x] = pixels[i == 0 ? 5 : i - 1];
			}
		}
	}

	private static void printScreen(boolean[][] screen, String command) {
		System.out.println("--------------------------------------------------");
		System.out.println(command);
		for (int i = 0; i < screen.length; i++) {
			boolean line[] = screen[i];
			for (int j = 0; j < line.length; j++) {
				System.out.print(line[j] ? '#' : '.');
			}
			System.out.println("");
		}
		System.out.println("--------------------------------------------------");
	}

	private static int count(boolean[][] screen) {
		int count = 0;
		for (int i = 0; i < screen.length; i++) {
			boolean line[] = screen[i];
			for (int j = 0; j < line.length; j++) {
				if (line[j]) {
					count++;
				}
			}
		}

		return count;
	}
}
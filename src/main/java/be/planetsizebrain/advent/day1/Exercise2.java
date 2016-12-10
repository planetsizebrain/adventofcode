package be.planetsizebrain.advent.day1;

import java.util.*;

public class Exercise2 {

	private String solve(String input) {
		int x = 0;
		int y = 0;
		Set<Coordinate> visitedCoordinates = new HashSet<>();
		Heading currentHeading = Heading.NORTH;
		String[] parts = input.split(", ");

		for (String part : parts) {
			Direction direction = Direction.getDirection(part);
			currentHeading = getHeading(currentHeading, direction);
			int distance = Integer.parseInt(part.substring(1));

			Optional<Coordinate> coordinate = addIntermediateCoordinates(new Coordinate(x, y), currentHeading, distance, visitedCoordinates);
			if (coordinate.isPresent()) {
				x = coordinate.get().x;
				y = coordinate.get().y;
				break;
			}

			switch (currentHeading) {
				case NORTH: y += distance; break;
				case EAST: x += distance; break;
				case SOUTH: y -= distance; break;
				case WEST: x -= distance; break;
			}
		}

		return Integer.toString(Math.abs(x) + Math.abs(y));
	}

	private Optional<Coordinate> addIntermediateCoordinates(Coordinate start, Heading heading, int distance, Set<Coordinate> coordinates) {
		Coordinate coordinate = new Coordinate(start);
		for (int i = 0; i < distance; i++) {
			switch (heading) {
				case NORTH: coordinate = new Coordinate(coordinate.x, coordinate.y + 1); break;
				case EAST: coordinate = new Coordinate(coordinate.x + 1, coordinate.y); break;
				case SOUTH: coordinate = new Coordinate(coordinate.x, coordinate.y - 1); break;
				case WEST: coordinate = new Coordinate(coordinate.x - 1, coordinate.y); break;
			}

			if (!coordinates.add(coordinate)) {
				return Optional.of(coordinate);
			}
		}

		return Optional.empty();
	}

	private Heading getHeading(Heading currentHeading, Direction direction) {
		switch (currentHeading) {
			case NORTH: return direction == Direction.LEFT ? Heading.WEST : Heading.EAST;
			case EAST: return direction == Direction.LEFT ? Heading.NORTH : Heading.SOUTH;
			case SOUTH: return direction == Direction.LEFT ? Heading.EAST : Heading.WEST;
			case WEST: return direction == Direction.LEFT ? Heading.SOUTH : Heading.NORTH;
		}

		return null;
	}


	public static void main(String[] args) {
		String input = "R1, R1, R3, R1, R1, L2, R5, L2, R5, R1, R4, L2, R3, L3, R4, L5, R4, R4, R1, L5, L4, R5, R3, L1, R4, R3, L2, L1, R3, L4, R3, L2, R5, R190, R3, R5, L5, L1, R54, L3, L4, L1, R4, R1, R3, L1, L1, R2, L2, R2, R5, L3, R4, R76, L3, R4, R191, R5, R5, L5, L4, L5, L3, R1, R3, R2, L2, L2, L4, L5, L4, R5, R4, R4, R2, R3, R4, L3, L2, R5, R3, L2, L1, R2, L3, R2, L1, L1, R1, L3, R5, L5, L1, L2, R5, R3, L3, R3, R5, R2, R5, R5, L5, L5, R2, L3, L5, L2, L1, R2, R2, L2, R2, L3, L2, R3, L5, R4, L4, L5, R3, L4, R1, R3, R2, R4, L2, L3, R2, L5, R5, R4, L2, R4, L1, L3, L1, L3, R1, R2, R1, L5, R5, R3, L3, L3, L2, R4, R2, L5, L1, L1, L5, L4, L1, L1, R1";

		Exercise2 exercise1 = new Exercise2();
		System.out.println(exercise1.solve(input));
	}

	private class Coordinate {

		public int x;
		public int y;

		public Coordinate(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public Coordinate(Coordinate coordinate) {
			this.x = coordinate.x;
			this.y = coordinate.y;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			Coordinate that = (Coordinate) o;
			return x == that.x &&
					y == that.y;
		}

		@Override
		public int hashCode() {
			return Objects.hash(x, y);
		}

		@Override
		public String toString() {
			return "{" + x + "," + y + '}';
		}
	}

	private enum Heading {
		NORTH,
		EAST,
		SOUTH,
		WEST;
	}

	private enum Direction {

		LEFT("L"),
		RIGHT("R");

		private String direction;

		Direction(String direction) {
			this.direction = direction;
		}

		public static Direction getDirection(String instruction) {
			String firstLetter = instruction.substring(0, 1);

			switch (firstLetter) {
				case "L" : return LEFT;
				case "R" : return RIGHT;
			}

			return null;
		}

	}
}
package be.planetsizebrain.advent.day1;

public class Exercise1 {

	private String solve(String input) {
		int x = 0;
		int y = 0;
		Heading currentHeading = Heading.NORTH;
		String[] parts = input.split(", ");

		for (String part : parts) {
			Direction direction = Direction.getDirection(part);
			currentHeading = getHeading(currentHeading, direction);
			int distance = Integer.parseInt(part.substring(1));

			switch (currentHeading) {
				case NORTH: y += distance; break;
				case EAST: x += distance; break;
				case SOUTH: y -= distance; break;
				case WEST: x -= distance; break;
			}
		}

		return Integer.toString(Math.abs(x) + Math.abs(y));
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

		Exercise1 exercise1 = new Exercise1();
		System.out.println(exercise1.solve(input));
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
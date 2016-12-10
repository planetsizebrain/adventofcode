package be.planetsizebrain.advent.day10;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.io.Resources;

public class Exercise1 {

	public static void main(String[] args) throws URISyntaxException, IOException {
		URL url = Resources.getResource("bots.txt");
		Stream<String> stream = Files.lines(Paths.get(url.toURI()));

		Map<Integer, Bot> bots = new HashMap<>();
		List<Command> commands = stream
				.map(line -> parseCommand(line))
				.collect(Collectors.toList());

		commands.stream()
				.filter(command -> command instanceof DecisionCommand)
				.forEach(command -> createBot((DecisionCommand) command, bots));

		commands.stream()
				.filter(command -> command instanceof ValueCommand)
				.forEach(command -> updateBotValue((ValueCommand) command, bots));
	}

	private static void updateBotValue(ValueCommand command, Map<Integer, Bot> bots) {
		Bot bot = bots.get(command.bot);
		bot.processValue(command.value, bots);
	}

	private static void createBot(DecisionCommand command, Map<Integer, Bot> bots) {
		bots.put(command.getBot(), new Bot(command.bot, command.isLowBot, command.lowBot, command.isHighBot, command.highBot));
	}

	private static Command parseCommand(String line) {
		Pattern decisionPattern = Pattern.compile("bot ([0-9]+) gives low to (bot|output) ([0-9]+) and high to (bot|output) ([0-9]+)");
		Matcher decisionMatcher = decisionPattern.matcher(line);
		if (decisionMatcher.find()) {
			int bot = Integer.parseInt(decisionMatcher.group(1));
			boolean isLowBot = decisionMatcher.group(2).equals("bot");
			int low = Integer.parseInt(decisionMatcher.group(3));
			boolean isHighBot = decisionMatcher.group(4).equals("bot");
			int high = Integer.parseInt(decisionMatcher.group(5));

			return new DecisionCommand(bot, isLowBot, low, isHighBot, high);
		}

		Pattern valuePattern = Pattern.compile("value ([0-9]+) goes to bot ([0-9]+)");
		Matcher valueMatcher = valuePattern.matcher(line);
		if (valueMatcher.find()) {
			int value = Integer.parseInt(valueMatcher.group(1));
			int bot = Integer.parseInt(valueMatcher.group(2));

			return new ValueCommand(bot, value);
		}

		return null;
	}

	private static class Bot {

		private List<Integer> values = new ArrayList<>();

		public int nr;
		public boolean isLowBot;
		public int lowBot;
		public boolean isHighBot;
		public int highBot;

		public Bot(int nr, boolean isLowBot, int lowBot, boolean isHighBot, int highBot) {
			this.nr = nr;
			this.isLowBot = isLowBot;
			this.lowBot = lowBot;
			this.isHighBot = isHighBot;
			this.highBot = highBot;
		}

		public void processValue(int value, Map<Integer, Bot> bots) {
			values.add(value);
			Collections.sort(values);

			if (values.size() == 2) {
				if (values.contains(17) && values.contains(61)) {
					System.out.println(nr);
				}

				int low = values.remove(0);
				if (isLowBot) {
					bots.get(lowBot).processValue(low, bots);
				}
				int high = values.remove(values.size() - 1);
				if (isHighBot) {
					bots.get(highBot).processValue(high, bots);
				}
			}
		}
	}

	private interface Command {

		int getBot();
	}

	private static class DecisionCommand implements Command {

		public int bot;
		public boolean isLowBot;
		public int lowBot;
		public boolean isHighBot;
		public int highBot;

		public DecisionCommand(int bot, boolean isLowBot, int lowBot, boolean isHighBot, int highBot) {
			this.bot = bot;
			this.isLowBot = isLowBot;
			this.lowBot = lowBot;
			this.isHighBot = isHighBot;
			this.highBot = highBot;
		}

		@Override
		public int getBot() {
			return bot;
		}

		@Override
		public String toString() {
			return "DecisionCommand{" +
					"bot=" + bot +
					", isLowBot=" + isLowBot +
					", lowBot=" + lowBot +
					", isHighBot=" + isHighBot +
					", highBot=" + highBot +
					'}';
		}
	}

	private static class ValueCommand implements Command {

		public int bot;
		public int value;

		public ValueCommand(int bot, int value) {
			this.bot = bot;
			this.value = value;
		}

		@Override
		public int getBot() {
			return bot;
		}
	}
}
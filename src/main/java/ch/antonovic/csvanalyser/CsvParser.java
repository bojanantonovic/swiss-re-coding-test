package ch.antonovic.csvanalyser;

public class CsvParser {

	private CsvParser() {
		// private constructor to prevent instantiation
	}

	static Entry parseLine(final String line) {
		final var parts = line.split(",");
		final var managerIdAsString = parts.length == 5 ? parts[4] : null;
		return new Entry( //
				Integer.parseInt(parts[0].trim()), //
				parts[1].trim(), //
				parts[2].trim(), //
				Long.parseLong(parts[3].trim()), //
				getManagerId(managerIdAsString));
	}

	private static Integer getManagerId(final String managerIdAsString) {
		if (managerIdAsString == null) {
			return null;
		}
		final var trimValue = managerIdAsString.trim();
		return trimValue.isEmpty() ? null : Integer.parseInt(trimValue);
	}
}

package ch.antonovic.csvanalyser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class CsvProcessor {
	private CsvProcessor() {
		// private constructor to prevent instantiation
	}

	private static final Logger LOGGER = LogManager.getLogger(CsvProcessor.class);

	public static void analyse(final Path path) throws IOException {
		LOGGER.info("Processing CSV file: {}", path.toAbsolutePath());
		final var entries = Files.lines(path) //
				.skip(1) // assuming the first line is a header
				.map(CsvParser::parseLine) // parse each line into an Entry object
				.toList();
		LOGGER.info("Parsed entries: {}", entries);
		for (final var entry : entries) {
			CsvAnalyser.verifySalaryComparedToSubordinates(entry, entries);
		}

		// verify reporting line
		reportTooLongReportingLine(entries);
	}

	static void reportTooLongReportingLine(final List<Entry> entries) {
		for (final var entry : CsvAnalyser.findSubordinatesWithTooLongReportingLines(entries)) {
			System.out.println("Employee " + entry + " has a too long reporting line ");
		}
	}
}

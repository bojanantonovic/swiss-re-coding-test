package ch.antonovic.csvanalyser;

import java.io.IOException;
import java.nio.file.Path;

public class Main {
	public static void main(final String[] args) throws IOException {
		final var path = Path.of("src", "main", "resources", "main.csv");
		CsvProcessor.analyse(path);
	}
}

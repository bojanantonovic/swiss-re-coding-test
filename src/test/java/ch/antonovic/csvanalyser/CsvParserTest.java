package ch.antonovic.csvanalyser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class CsvParserTest {

	@Test
	void parseLine_CEO_nullManagerId() {
		// When
		final var line = "123,Joe,Doe,60000,";
		final var entry = CsvParser.parseLine(line);

		// Then
		assertEquals(123, entry.id());
		assertEquals("Joe", entry.firstName());
		assertEquals("Doe", entry.lastName());
		assertEquals(60000, entry.salary());
		assertNull(entry.managerId());
	}

	@Test
	void parseLine_normalEmployee_everythingOk() {
		// When
		final var line = "124,Martin,Chekov,45000,123";
		final var entry = CsvParser.parseLine(line);

		// Then
		assertEquals(124, entry.id());
		assertEquals("Martin", entry.firstName());
		assertEquals("Chekov", entry.lastName());
		assertEquals(45000, entry.salary());
		assertEquals(123, entry.managerId());
	}
}

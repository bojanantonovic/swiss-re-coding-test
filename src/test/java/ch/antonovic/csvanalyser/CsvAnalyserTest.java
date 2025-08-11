package ch.antonovic.csvanalyser;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.OptionalDouble;

import static org.junit.jupiter.api.Assertions.*;

class CsvAnalyserTest {

	private static final Entry DAGOBERT_DUCK = new Entry(1, "Dagobert", "Duck", 50000L, null);
	private static final Entry DONALD_DUCK = new Entry(2, "Donald", "Duck", 30000L, 1);
	private static final Entry DAISY_DUCK = new Entry(3, "Daisy", "Duck", 20000L, 1);
	private static final Entry TICK_DUCK = new Entry(4, "Tick", "Duck", 30000L, 2);
	private static final Entry TRICK_DUCK = new Entry(5, "Trick", "Duck", 30000L, 4);
	private static final Entry TRUCK_DUCK = new Entry(6, "Trick", "Duck", 30000L, 5);
	private static final Entry MICKEY_MOUSE = new Entry(7, "Mickey", "Mouse", 30000L, 6);
	private static final Entry GOOFY_GOOF = new Entry(8, "Goofy", "Goof", 30000L, 7);

	@Test
	void computeAverageOfSubordinates_onePersonCompany_noAverage() {
		// When
		final OptionalDouble result = CsvAnalyser.computeAverageOfSubordinates(DAGOBERT_DUCK, List.of(DAGOBERT_DUCK));

		// Then
		assertFalse(result.isPresent());
	}

	@Test
	void computeAverageOfSubordinates_twoSubordinates_noAverage() {
		// When
		final OptionalDouble result = CsvAnalyser.computeAverageOfSubordinates(DAGOBERT_DUCK, List.of(DAGOBERT_DUCK, DONALD_DUCK, DAISY_DUCK));

		// Then
		assertTrue(result.isPresent());
		assertEquals(25000d, result.getAsDouble(), 0.001);
	}

	@Test
	void computeReportingLine_DagobertDuck_0() {
		assertEquals(0, CsvAnalyser.computeReportingLine(DAGOBERT_DUCK, List.of(DAGOBERT_DUCK)));
	}

	@Test
	void computeReportingLine_DonaldDuck_1() {
		assertEquals(1, CsvAnalyser.computeReportingLine(DONALD_DUCK, List.of(DAGOBERT_DUCK, DONALD_DUCK)));
	}

	@Test
	void computeReportingLine_TickDuck_2() {
		assertEquals(2, CsvAnalyser.computeReportingLine(TICK_DUCK, List.of(DAGOBERT_DUCK, DONALD_DUCK, TICK_DUCK)));
	}

	@Test
	void findSubordinatesWithTooLongReportingLines_hugeList_twoPeople() {
		// When
		final List<Entry> result = CsvAnalyser.findSubordinatesWithTooLongReportingLines(List.of(DAGOBERT_DUCK, DONALD_DUCK, TICK_DUCK, TRICK_DUCK, TRUCK_DUCK, MICKEY_MOUSE, GOOFY_GOOF));

		// Then
		assertEquals(2, result.size());
		assertTrue(result.contains(MICKEY_MOUSE));
		assertTrue(result.contains(GOOFY_GOOF));
	}
}

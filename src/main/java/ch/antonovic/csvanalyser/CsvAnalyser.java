package ch.antonovic.csvanalyser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.OptionalDouble;

public class CsvAnalyser {

	private static final int MAXIMAL_REPORTING_LINE_LENGTH = 4;

	private CsvAnalyser() {
		// private constructor to prevent instantiation
	}

	private static final double MINIMUM_SALARY_FACTOR = 1.2;
	private static final double MAXIMUM_SALARY_FACTOR = 1.5;

	private static final Logger LOGGER = LogManager.getLogger(CsvAnalyser.class);

	static void verifySalaryComparedToSubordinates(final Entry potentialManager, final List<Entry> entries) {
		final var averageOfSubordinates = computeAverageOfSubordinates(potentialManager, entries);
		LOGGER.debug("Average salary of subordinates for manager {}: {}", potentialManager, averageOfSubordinates);
		if (averageOfSubordinates.isPresent()) {
			final var averageSalaryOfSubordinates = averageOfSubordinates.getAsDouble();
			if (potentialManager.salary() < averageSalaryOfSubordinates * MINIMUM_SALARY_FACTOR) {
				LOGGER.info("Manager {} earns too few", potentialManager);
			}
			if (potentialManager.salary() > averageSalaryOfSubordinates * MAXIMUM_SALARY_FACTOR) {
				LOGGER.info("Manager {} earns too much", potentialManager);
			}
		} else {
			LOGGER.debug("No subordinates found for {}", potentialManager);
		}
	}

	static OptionalDouble computeAverageOfSubordinates(final Entry potentialManager, final List<Entry> entries) {
		return entries.stream() //
				.filter(e -> e.managerId() != null) // filter out entries without a manager
				.filter(e -> potentialManager.id() == e.managerId()) //
				.map(Entry::salary) //
				.mapToDouble(Double::valueOf) //
				.average();
	}

	static List<Entry> findSubordinatesWithTooLongReportingLines(final List<Entry> entries) {
		return entries.stream() //
				.filter(e -> computeReportingLine(e, entries) > MAXIMAL_REPORTING_LINE_LENGTH) // filter employees with a too long reporting line
				.toList();
	}

	static int computeReportingLine(final Entry employee, final List<Entry> entries) {
		if (employee.managerId() == null) {
			return 0; // no manager, reporting line is 0
		}
		final var manager = entries.stream() //
				.filter(e -> e.id() == employee.managerId()) //
				.findFirst();
		return 1 + computeReportingLine(manager.get(), entries); // 1 for the current employee plus the reporting line of the manager
	}
}

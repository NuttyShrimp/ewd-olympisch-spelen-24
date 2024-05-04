package org.hogent.olympisch_spelen_24.utils;

import org.hogent.olympisch_spelen_24.domain.Competition;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class CompetitionValidation implements Validator {
    private final LocalDate EARLIEST_START_DATE = LocalDate.of(2024, 7, 27);
    private final LocalDate LATEST_START_DATE = LocalDate.of(2024, 8, 10);
    private final LocalTime EARLIEST_START_TIME = LocalTime.of(8, 0);
    private final LocalTime LATEST_START_TIME = LocalTime.of(17, 0);

    @Override
    public boolean supports(Class<?> clazz) {
        return Competition.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Competition competition = (Competition) target;

        LocalDateTime startDateTime = competition.getTime();
        if (startDateTime == null) {
            return;
        }
        LocalDate startDate = startDateTime.toLocalDate();
        LocalTime startTime = startDateTime.toLocalTime();
        if (startDate.isBefore(EARLIEST_START_DATE)) {
            errors.rejectValue("time", "dateTooEarly.Competition.time", String.format("The competition date should start after %s", EARLIEST_START_DATE));
        }
        if (startDate.isAfter(LATEST_START_DATE)) {
            errors.rejectValue("time", "dateTooLate.Competition.time", String.format("The competition date should start before %s", LATEST_START_DATE));
        }
        if (startTime.isBefore(EARLIEST_START_TIME)) {
            errors.rejectValue("time", "timeTooEarly.Competition.time", String.format("The competition time should start after %s", EARLIEST_START_TIME));
        }
        if (startTime.isAfter(LATEST_START_TIME)) {
            errors.rejectValue("time", "timeTooLate.Competition.time", String.format("The competition time should start before %s", EARLIEST_START_TIME));
        }

        // Olympicnr1 validation
        Long olympicNr1 = competition.getOlympicNr1();
        if (olympicNr1 == null) {
            return;
        }
        if (String.valueOf(olympicNr1).toString().length() != 5) {
            errors.rejectValue("olympicNr1", "length.Competition.olympicNr1", "Olympic Nr 1 should be exactly 5 characters");
        }
        // Shouldn't start with a zero(0)
        if (olympicNr1 < 10000) {
            errors.rejectValue("olympicNr1", "startWithZero.Competition.olympicNr1", "Olympic Nr 1 may not start with a zero(0)");
        }
        // First and last digit should differ
        long olympicNr1Digit1 = (olympicNr1 - (olympicNr1 % 10000)) / 10000;
        long olympicNr1Digit5 = olympicNr1 % 10;
        if (olympicNr1Digit1 == olympicNr1Digit5) {
            errors.rejectValue("olympicNr1", "sameOuterDigits.Competition.olympicNr1", "Olympic Nr 1 may not start and end with the same digit");
        }

        // Olympicnr2 validation
        Long olympicNr2 = competition.getOlympicNr2();
        if (olympicNr2 == null) {
            return;
        }
        // Should be in between olympicnr1 - 1000 & olympicnr1 + 1000
        if (olympicNr2 < olympicNr1 - 1000) {
            errors.rejectValue("olympicNr2", "tooSmall.Competition.olympicNr2", "Olympic Nr 2 should be within a range of 1000 of the olympicNr1");
        }
        if (olympicNr2 > olympicNr1 + 1000) {
            errors.rejectValue("olympicNr2", "tooLarge.Competition.olympicNr2", "Olympic Nr 2 should be within a range of 1000 of the olympicNr1");
        }

    }
}

package com.safetyNet.safetyNetAlerts.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.regex.Pattern;

public final class AgeCalculator {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private static final Pattern DATE_PATTERN = Pattern.compile("\\d{2}/\\d{2}/\\d{4}");

    private AgeCalculator() {}

    public static int calculate(String birthdate) {
        if (!DATE_PATTERN.matcher(birthdate).matches()) {
            throw new IllegalArgumentException("Invalid date format. Expected format: MM/dd/yyyy");
        }

        LocalDate birthdateDate = LocalDate.parse(birthdate, DATE_FORMATTER);
        LocalDate currentDate = LocalDate.now();

        return (int) ChronoUnit.YEARS.between(birthdateDate, currentDate);
    }

    public static boolean isChild(int age) {
        return age <= 18;
    }
}

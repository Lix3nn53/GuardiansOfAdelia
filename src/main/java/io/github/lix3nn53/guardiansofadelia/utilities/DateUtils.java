package io.github.lix3nn53.guardiansofadelia.utilities;

import java.time.LocalDate;
import java.time.temporal.ChronoField;

public class DateUtils {

    /**
     * @return Index starts from 1 to 7
     */
    public static int getDayOfTheWeek() {
        LocalDate now = LocalDate.now();

        return now.get(ChronoField.DAY_OF_WEEK);
    }

    public static LocalDate getFirstDayOfTheWeek() {
        LocalDate now = LocalDate.now();

        return now.with(ChronoField.DAY_OF_WEEK, 1);
    }

    public static boolean isSameDay(LocalDate localDate1, LocalDate localDate2) {
        return localDate1.isEqual(localDate2);
    }

    public static boolean isDateInCurrentWeek(LocalDate targetDate) {
        LocalDate now = LocalDate.now();
        int year = now.getYear();
        int week = now.get(ChronoField.ALIGNED_WEEK_OF_YEAR);

        int targetYear = targetDate.getYear();
        int targetWeek = targetDate.get(ChronoField.ALIGNED_WEEK_OF_YEAR);

        return week == targetWeek && year == targetYear;
    }
}

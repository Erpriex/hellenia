package fr.erpriex.hellenia.utils;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class TimeUtils {

    public static String formatTime(int seconds) {
        if (seconds == 0) return "0 seconde";

        int minutes = seconds / 60;
        int hours = minutes / 60;
        int days = hours / 24;
        int years = days / 365;

        seconds %= 60;
        minutes %= 60;
        hours %= 24;
        days %= 365;

        StringBuilder sb = new StringBuilder();

        if (years > 0) sb.append(years).append(" an").append(years > 1 ? "s" : "").append(", ");
        if (days > 0) sb.append(days).append(" jour").append(days > 1 ? "s" : "").append(", ");
        if (hours > 0) sb.append(hours).append(" heure").append(hours > 1 ? "s" : "").append(", ");
        if (minutes > 0) sb.append(minutes).append(" minute").append(minutes > 1 ? "s" : "").append(", ");
        if (seconds > 0) sb.append(seconds).append(" seconde").append(seconds > 1 ? "s" : "").append(", ");

        if (sb.length() >= 2) sb.setLength(sb.length() - 2);

        return sb.toString();
    }

    public static String formatHumanDate(OffsetDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy 'à' HH'h'mm", Locale.FRENCH);
        return dateTime.format(formatter);
    }

}

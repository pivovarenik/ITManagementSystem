package org.util;

public class AgeParser {
    public static int parseageOrDefault(String text, int defaultAge) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            System.out.println("Некорректный формат возраста: " + text);
            return defaultAge;
        }
    }
}

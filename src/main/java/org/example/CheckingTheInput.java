package org.example;

import java.time.LocalDate;

public class CheckingTheInput {
    protected static String regex = "[0-9]+";

    protected static int year() {
        LocalDate current_date = LocalDate.now();
        int year = current_date.getYear();
        return year;
    }

    public static boolean check(String part2, String part3, String[] parts) {

        String[] data = part2.split("\\.");

        boolean checkSum = part3.matches(regex);
        boolean checkNumberOfObjects = parts.length == 3;
        boolean checkNumberOfData = data.length == 3;
        boolean checkData = false;

        Integer pat1 = Integer.valueOf(data[0]);
        Integer pat2 = Integer.valueOf(data[1]); //месяц
        Integer pat3 = Integer.valueOf(data[2]); //число

        if (pat2 < 13 && pat2 > 0) {
            if (pat3 < 32 && pat3 > 0) {
                if (pat1 <= year()) {
                    checkData = true;
                }
            }
        }

        if (checkSum == true && checkNumberOfObjects == true && checkNumberOfData == true && checkData == true) {
            return true;
        } else {
            return false;
        }
    }
}

package org.example;

import org.json.simple.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class sad {
    Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws ParseException {
        Scanner scanner = new Scanner(System.in);


        String ss ="{\"date\":\"2022.12.22\",\"sum\":\"200\",\"title\":\"Сыр\"}\n";

        FinanceCategory financeCategory = new FinanceCategory();

        financeCategory.countingExpenses(ss);
    }
}

package org.example;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.json.simple.JSONObject;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class FinanceCategory {

    static ArrayList<String> product = new ArrayList<String>();
    static ArrayList<String> category = new ArrayList<String>();

    public static void countingExpenses(String сheque) {

        String[] s = new String[0];

//        StringBuilder jsonString = new StringBuilder(сheque);
//        JSONObject jsonObj = new JSONObject(jsonString.toString());
        String[] meaning = сheque.split("\",\"");


        for (int i = 0; i < meaning.length; i++) {

            s = meaning[i].split("\":\"");

        }

        for (int i = 0; i < 6; i++) {
            System.out.println(s[i]);
        }


//        String pat1 = meaning[0];
//        String pat2 = meaning[1];
//        String pat3 = meaning[2];


//        System.out.println(pat1);
//        System.out.println(pat2);
//        System.out.println(pat3);
//        JSONObject jsonObj = new JSONObject.quote(jsonString.toString());
    }

    public static void main() throws CsvValidationException, IOException {

        CSVReader reader = new CSVReader(new FileReader("categories.tsv"));

        String[] nextLine;

        while ((nextLine = reader.readNext()) != null) {
            String[] parts = Arrays.toString(nextLine).split("\t");

            product.add(parts[0]);
            category.add(parts[1]);
        }

        for (int i = 0; i < product.size(); i++) {

            product.set(i, product.get(i).replaceAll("\\[", "").replaceAll("\\]", ""));
            category.set(i, category.get(i).replaceAll("\\[", "").replaceAll("\\]", ""));
//            System.out.println("product: " + product.get(i));
//            System.out.println("category: " + category.get(i));
        }
    }
}

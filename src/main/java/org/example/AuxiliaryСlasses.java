package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import org.json.simple.JSONObject;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class AuxiliaryСlasses {
    protected static String other = "другое";
    protected static Map<String, String> productCategory = new HashMap<>();
    protected static Map<String, Integer> priceCategory = new HashMap<>();

    protected static int realTimeYear() {
        LocalDate current_date = LocalDate.now();
        int year = current_date.getYear();
        return year;
    }

    protected static int realTimeMonth() {
        LocalDate current_date = LocalDate.now();
        int month = current_date.getMonthValue();
        return month;
    }

    protected static int realTimeDay() {
        LocalDate current_date = LocalDate.now();
        int day = current_date.getDayOfMonth();
        return day;
    }

    public static boolean checkingTheEnteredData(String part2, Integer part3, String[] parts) {

        String[] data = part2.split("\\.");

        boolean checkNumberOfObjects = parts.length == 3;
        boolean checkNumberOfData = data.length == 3;
        boolean checkData = false;

        Integer pat1 = Integer.valueOf(data[0]);
        Integer pat2 = Integer.valueOf(data[1]); //месяц
        Integer pat3 = Integer.valueOf(data[2]); //число

        if (pat2 < 13 && pat2 > 0) {
            if (pat3 < 32 && pat3 > 0) {
                if (pat1 <= realTimeYear()) {
                    checkData = true;
                }
            }
        }

        if (checkNumberOfObjects == true && checkNumberOfData == true && checkData == true) {
            return true;
        } else {
            return false;
        }
    }

    public static void parsingTheDataTable() throws CsvValidationException, IOException {
        ArrayList<String> product = new ArrayList<String>();
        ArrayList<String> category = new ArrayList<String>();

        CSVReader reader = new CSVReader(new FileReader("categories.tsv"));

        String[] nextLine;

        while ((nextLine = reader.readNext()) != null) {
            String[] parts = Arrays.toString(nextLine).split("\t");

            product.add(parts[0]);
            category.add(parts[1]);
        }

        for (int i = 0; i < product.size(); i++) {
            String prod = product.get(i).replaceAll("\\[", "").replaceAll("\\]", "");
            String cat = category.get(i).replaceAll("\\[", "").replaceAll("\\]", "");

            priceCategory.put(cat, 0);
            productCategory.put(prod, cat);
        }
        priceCategory.put("другое", 0);
    }

    public static void addingToTheShoppingList(String product, String data, Integer sum) {

        String purchaseсPeriod = product + "," + data + "," + sum;

        String[] employee = purchaseсPeriod.split(",");

        try (CSVWriter writer = new CSVWriter(new FileWriter("ListProducts.csv", true))) {

            writer.writeNext(employee);
            System.out.println("В список успешно добавлена покупка: " + Arrays.toString(employee));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String maximumValueCategory(Map<String, Integer> sranvenie) {

        ArrayList<Integer> MeaningСategories = new ArrayList<>();

        String[] categories = sranvenie.keySet().toArray(new String[0]);

        for (int x = 0; x < sranvenie.size(); x++) {
            MeaningСategories.add(sranvenie.get(categories[x]));
        }
        int maxValue = Collections.max(MeaningСategories);

        String categ = "";
        for (String k : sranvenie.keySet()) {
            if (sranvenie.get(k).equals(maxValue)) {
                categ = k;
                break;
            }
        }
        return categ;
    }

    public static String arrayСategory() throws IOException {
        String row;

        Map<String, Integer> MaxCat = new HashMap<>();
        Map<String, Integer> MaxYearCat = new HashMap<>();
        Map<String, Integer> MaxMonthCat = new HashMap<>();
        Map<String, Integer> MaxDayCat = new HashMap<>();

        MaxCat.putAll(priceCategory);
        MaxYearCat.putAll(priceCategory);
        MaxMonthCat.putAll(priceCategory);
        MaxDayCat.putAll(priceCategory);

        BufferedReader csvReader = new BufferedReader(new FileReader("ListProducts.csv"));
        while ((row = csvReader.readLine()) != null) {
            String[] cheque = row.split(",");

            String product = cheque[0].replace("\"", "");
            String data = cheque[1].replace("\"", "");
            int sum = Integer.parseInt(cheque[2].replace("\"", ""));

            String[] lineData = data.replace(".", " ").split(" ");

            Integer year = Integer.valueOf(lineData[0]); //год
            Integer month = Integer.valueOf(lineData[1]); //месяц
            Integer day = Integer.valueOf(lineData[2]); //число

            if (productCategory.containsKey(product) == true) {
                MaxCat.put(productCategory.get(product), MaxCat.get(productCategory.get(product)) + sum);
            } else {
                MaxCat.put(other, MaxCat.get(other) + sum);
            }

            if (realTimeYear() == year) {
                if (productCategory.containsKey(product) == true) {
                    MaxYearCat.put(productCategory.get(product), MaxYearCat.get(productCategory.get(product)) + sum);
                } else {
                    MaxYearCat.put(other, MaxYearCat.get(other) + sum);
                }
            }

            if (realTimeYear() == year && realTimeMonth() == month) {
                if (productCategory.containsKey(product) == true) {
                    MaxMonthCat.put(productCategory.get(product), MaxMonthCat.get(productCategory.get(product)) + sum);
                } else {
                    MaxMonthCat.put(other, MaxMonthCat.get(other) + sum);
                }
            }
            if (realTimeYear() == year && realTimeMonth() == month && realTimeDay() == day) {
                if (productCategory.containsKey(product) == true) {
                    MaxDayCat.put(productCategory.get(product), MaxDayCat.get(productCategory.get(product)) + sum);
                } else {
                    MaxDayCat.put(other, MaxDayCat.get(other) + sum);
                }
            }
        }
        csvReader.close();

        JSONObject conclusion = new JSONObject();
        JSONObject conclusionMaxYear = new JSONObject();
        JSONObject conclusionMaxMonth = new JSONObject();
        JSONObject conclusionMaxDay = new JSONObject();
        JSONObject conclusionMaxCategory = new JSONObject();

        conclusionMaxYear.put("category", maximumValueCategory(MaxYearCat));
        conclusionMaxYear.put("sum", MaxYearCat.get(maximumValueCategory(MaxYearCat)));

        conclusionMaxMonth.put("category", maximumValueCategory(MaxMonthCat));
        conclusionMaxMonth.put("sum", MaxMonthCat.get(maximumValueCategory(MaxMonthCat)));

        conclusionMaxDay.put("category", maximumValueCategory(MaxDayCat));
        conclusionMaxDay.put("sum", MaxDayCat.get(maximumValueCategory(MaxDayCat)));

        conclusionMaxCategory.put("category", maximumValueCategory(MaxCat));
        conclusionMaxCategory.put("sum", MaxCat.get(maximumValueCategory(MaxCat)));

        conclusion.put("maxYearCategory", conclusionMaxYear);
        conclusion.put("maxMonthCategory", conclusionMaxMonth);
        conclusion.put("maxDayCategory", conclusionMaxDay);
        conclusion.put("maxCategory", conclusionMaxCategory);

        String ret = conclusion.toString();

        return ret;
    }
}

package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.exceptions.CsvValidationException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        try (java.net.ServerSocket server = new java.net.ServerSocket(org.example.ServerSocket.PORT)) {
            System.out.println("Сервер запущен.");

            AuxiliaryСlasses auxiliaryСlasses = new AuxiliaryСlasses();
            AuxiliaryСlasses.parsingTheDataTable();


            while (true) {

                try (Socket socket = server.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                     PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

                    String LineJson = in.readLine();

                    ObjectMapper mapper = new ObjectMapper();
                    Map<String, Object> map = mapper.readValue(LineJson, Map.class);

                    String product = (String) map.get("title");
                    String date = (String) map.get("date");
                    Integer sum = (Integer) map.get("sum");
                    
                    auxiliaryСlasses.addingToTheShoppingList(product, date, sum);
                    out.println(auxiliaryСlasses.arrayСategory());
                }
            }
        } catch (IOException e) {
            System.out.println("Не могу стартовать сервер");
            e.printStackTrace();
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }
    }
}

package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opencsv.exceptions.CsvValidationException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

public class ServerPersonalFinanceManager {

    protected static String CANN = "Некоректный ввод.";

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(org.example.ServerSocket.PORT)) {
            System.out.println("Сервер производит запуск: \n" + "Loading…  ██████████ 100%\n" + "Сервер запущен.");

            AuxiliaryСlasses auxiliaryСlasses = new AuxiliaryСlasses();
            AuxiliaryСlasses.parsingTheDataTable();

            try (Socket socket = server.accept(); BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

                final String name = in.readLine();
                System.out.println("Зарегестрировался пользователь: " + name);
                out.println(String.format("Привет %s, твой порт %d", name, socket.getPort()));

                int x = 0;

                while (x == 0) {
                    int actionInt = Integer.parseInt(in.readLine());

                    switch (actionInt) {
                        case 1:
                            String LineJson = in.readLine();

                            ObjectMapper mapper = new ObjectMapper();
                            Map<String, String> map = mapper.readValue(LineJson, Map.class);

                            String product = map.get("title");
                            String date = map.get("date");
                            Integer sum = Integer.parseInt(map.get("sum"));

                            auxiliaryСlasses.addingToTheShoppingList(product, date, sum);
                            break;
                        case 2:
                            out.println(auxiliaryСlasses.arrayСategory());
                            break;
                        case 3:
                            System.out.println("Сервер закончил свою работу.");
                            x++;
                            break;
                        default:
                            out.println("Некоректный ввод.");
                            break;
                    }
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

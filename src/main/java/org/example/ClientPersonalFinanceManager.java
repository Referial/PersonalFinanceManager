package org.example;

import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import static org.example.ServerSocket.HOST;
import static org.example.ServerSocket.PORT;

public class ClientPersonalFinanceManager {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        try (Socket socket = new Socket(HOST, PORT);
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            JSONObject file = new JSONObject();

            String part1;
            String part2;
            Integer part3;

            System.out.println("Введите через пробел: Название продукта, дата покупки в формате - год.месяц.число " +
                    "и сумму покупки.");
            String line = scanner.nextLine();
            String[] parts = line.split(" ");

            if (parts.length == 3) {
                part1 = parts[0]; //Название товара
                part2 = parts[1]; //Дата
                part3 = Integer.valueOf(parts[2]); //Сумма

                boolean chek = AuxiliaryСlasses.checkingTheEnteredData(part2, part3, parts);

                if (chek == true) {
                    file.put("title", part1);
                    file.put("date", part2);
                    file.put("sum", part3);

                    String strJson = file.toString();

                    writer.println(String.format(strJson));
                }
            } else {
                System.out.println("Некоректный ввод.");
            }
            System.out.println(reader.readLine());
            System.out.println(reader.readLine());
            System.out.println(reader.readLine());
            System.out.println(reader.readLine());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

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
            System.out.println("Введите имя пользователя:");

            writer.println(scanner.nextLine());
            System.out.println(reader.readLine());

            while (true) {

                System.out.println("Выберите: \n" + "1. Добавить покупку. \n" + "2. Выйти из приложения.");
                int action = scanner.nextInt();
                String ac = Integer.toString(action);
                writer.println(ac);

                switch (action) {
                    case 1:
                        JSONObject file = new JSONObject();

                        String part1;
                        String part2;
                        String part3;

                        System.out.println("Введите через пробел: Название продукта, дата покупки в формате - год.месяц.число " +
                                "и сумму покупки.");
//                String line = scanner.nextLine();
                        String line = "Сыр 2022.12.22 200";
                        String[] parts = line.split(" ");

                        if (parts.length == 3) {
                            part1 = parts[0]; //Название товара
                            part2 = parts[1]; //Дата
                            part3 = parts[2]; //Сумма

                            boolean chek = CheckingTheInput.check(part2, part3, parts);

                            if (chek == true) {
                                file.put("title", part1);
                                file.put("date", part2);
                                file.put("sum", part3);

                                String strJson = file.toString();
                                System.out.println(strJson);

                                writer.println(String.format(strJson));
                            }
                        } else {
                            System.out.println("Некоректный ввод.");
                        }
                        break;
                    case 2:
                        System.out.println("все заеьись 2");
                        break;
                    default:
                        System.out.println(reader.readLine());
                        break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

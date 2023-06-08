package org.example;

import com.opencsv.exceptions.CsvValidationException;
import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;

public class ServerPersonalFinanceManager {

    protected static String CANN = "Некоректный ввод.";

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(org.example.ServerSocket.PORT)) {
            System.out.println("Сервер производит запуск: \n" + "Loading…  ██████████ 100%\n"
                    + "Сервер запущен.");

            FinanceCategory financeCategory = new FinanceCategory();
            financeCategory.main();

            try (Socket socket = server.accept();
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

                final String name = in.readLine();
                System.out.println("Зарегестрировался пользователь: " + name);
                out.println(String.format("Привет %s, твой порт %d", name, socket.getPort()));

                while (true) {
                    String action = in.readLine();
                    int actionInt = Integer.parseInt(action.trim());

                    switch (actionInt) {
                        case 1:
                            String check = in.readLine();
                            System.out.println(check);
                            financeCategory.countingExpenses(check);
                            break;
                        case 2:
                            System.out.println("все заеьись 2");
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

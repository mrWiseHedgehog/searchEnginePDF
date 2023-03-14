import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);


        try (Socket clientSocket = new Socket(Main.HOST, Main.PORT);
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

            String serverConnect = in.readLine();
            System.out.println(serverConnect);
            String inputWord = scanner.nextLine();
            out.println(inputWord);
            String makeJSON = in.readLine();
            System.out.println(getJson(makeJSON));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getJson(String json) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.setPrettyPrinting().create();
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = jsonParser.parse(json);
        return gson.toJson(jsonElement);
    }
}
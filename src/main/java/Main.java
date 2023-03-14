import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import static java.lang.System.out;


public class Main {
    public static final String HOST = "localhost";
    public static final int PORT = 8989;

    public static void main(String[] args) {

        try {
            BooleanSearchEngine searchEngine = new BooleanSearchEngine(new File("pdfs"));
            try (ServerSocket server = new ServerSocket(PORT)) {
                out.println("Server " + server.getInetAddress() + " online " + " used port is " + PORT);

                while (true) {

                    try (Socket clientSocket = server.accept(); // Opening port for getting connect clients
                         PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                         BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));) {
                        System.out.println("Connected client " + clientSocket.getPort());
                        out.println("Для поиска введите слово...");
                        String word = in.readLine();
                        List<PageEntry> page = searchEngine.search(word);
                        out.println(listToJson(page));
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        } catch (Exception exception) {
            out.println(exception.getMessage());
        }
    }

    public static String listToJson(List<PageEntry> list) {
        Type listType = new TypeToken<List<PageEntry>>() {
        }.getType();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.toJson(list, listType);
    }
}
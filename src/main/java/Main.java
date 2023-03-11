import java.io.File;
import java.util.function.ToDoubleBiFunction;

public class Main {
    public static void main(String[] args) throws Exception {
        BooleanSearchEngine engine = new BooleanSearchEngine(new File("pdfs"));
        System.out.println(engine.search("data"));
        /*TODO
        * здесь создайте сервер, который отвечал бы на нужные запросы
        * слушать он должен порт 8989
        * отвечать на запросы /{word} -> возвращённое значение метода search(word) в JSON-формате
        **/

    }
}

//TODO Создать класс клиента для работы с сервером
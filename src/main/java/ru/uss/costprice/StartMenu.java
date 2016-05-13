package ru.uss.costprice;

import ru.uss.costprice.model.Jewel;
import ru.uss.costprice.parsing.Parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vadelic on 11.05.2016.
 */
public class StartMenu {
    private static Map<String, List<Jewel>> dataBase = new HashMap<>();
    private static final String PATH = "/main/resources/list_series.csv";

    static {
        try {
            dataBase = (Parser.getSkuFromCsv(Files.newInputStream(Paths.get(PATH))));
        } catch (IOException e) {
            System.out.println("Error loading BD");
        }
    }


    public static void main(String[] args) {

        CommandExecutor.Operation operation = null;

        do {
            operation = CommandExecutor.getOperation();
            CommandExecutor.exec(operation);

        } while (CommandExecutor.Operation.EXIT != operation);
    }
}

package ru.uss.costprice;

import ru.uss.costprice.command.Command;
import ru.uss.costprice.command.CommandGetSerialOfSku;
import ru.uss.costprice.command.CommandGetPriseCalculate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.EnumMap;

import static ru.uss.costprice.CmdHelper.Operation.*;

/**
 * Created by vadelic on 13.05.2016.
 */

public class CmdHelper {
    //    10-02-1200-10333
//    TODO http://stackoverflow.com/questions/5915425/programmatically-reading-static-resources-from-my-java-webapp
    public static void main(String[] args) {
        Operation operation;
        do {
            operation = CmdHelper.getOperation();
            exec(operation);

        } while (Operation.EXIT != operation);
    }

     enum Operation {
        LOAD,
         PRINT_SERIAL_NUMBER_OF_SKU,
         PRINT_LIST_SKU,
        EXIT
    }
    private CmdHelper() {
    }

    private static EnumMap<Operation, Command> allOperation = new EnumMap<>(Operation.class);
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    static {
        allOperation.put(LOAD, null);
        allOperation.put(PRINT_SERIAL_NUMBER_OF_SKU, new CommandGetSerialOfSku());
        allOperation.put(PRINT_LIST_SKU, new CommandGetPriseCalculate());
        allOperation.put(EXIT, null);
    }

     private static Operation getOperation() {
        int choose;

        while (true) {
            try {
                System.out.println("Choose command:");
                System.out.format("%d. Load data base\n", LOAD.ordinal());
                System.out.format("%d. Show all serials given SKU\n", PRINT_SERIAL_NUMBER_OF_SKU.ordinal());
                System.out.format("%d. Show given list SKU\n", PRINT_LIST_SKU.ordinal());
                System.out.format("%d. Exit\n", EXIT.ordinal());

                choose = Integer.parseInt(readLine());
                if ((choose < 0) || (choose > Operation.values().length - 1))
                    throw new NumberFormatException();
                break;
            } catch (NumberFormatException e) {
                System.out.println("invalid command");
            }
        }
        return Operation.values()[choose];
    }

    private static void exec(Operation operation) {
        Command cmd = allOperation.get(operation);
        if (cmd == null)
            System.out.println("Operation is disabled");
        else cmd.execute();
    }

    public static String readLine() {
        String result = null;
        try {
            result = reader.readLine();
        } catch (IOException e) {
            System.out.println("IO error, try again");
        }
        return result;
    }
}

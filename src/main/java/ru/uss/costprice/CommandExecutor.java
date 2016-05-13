package ru.uss.costprice;

import ru.uss.costprice.Command.Command;
import ru.uss.costprice.Command.PrintSkuCost;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.EnumMap;

import static ru.uss.costprice.CommandExecutor.Operation.*;

/**
 * Created by vadelic on 13.05.2016.
 */

class CommandExecutor {
    static enum Operation {
        LOAD,
        PRINT_SKU_COST,
        EXIT
    }

    private static EnumMap<Operation, Command> allOperation = new EnumMap<>(CommandExecutor.Operation.class);
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    static {
        allOperation.put(LOAD, null);
        allOperation.put(PRINT_SKU_COST, new PrintSkuCost());
        allOperation.put(EXIT, null);
    }

    public static Operation getOperation() {
        int choose = 0;

        while (true) {
            try {
                System.out.println("Choose command:");
                System.out.format("%d. Load data base\n", LOAD.ordinal());
                System.out.format("%d. Show cost price SKU\n", PRINT_SKU_COST.ordinal());
                System.out.format("\n%d. Exit\n> ", EXIT.ordinal());

                choose = Integer.parseInt(reader.readLine());
                if ((choose < 0) || (choose > Operation.values().length - 1))
                    throw new NumberFormatException();

                break;
            } catch (IOException e) {
                System.out.println("IO error, try again");
            } catch (NumberFormatException e) {
                System.out.println("invalid command");
            }
        }
        return Operation.values()[choose];
    }

    public static void exec(Operation operation) {
        Command cmd = allOperation.get(operation);
        if (cmd == null)
            System.out.println("Operation is disabled");
        else cmd.execute();
    }

    private CommandExecutor() {
    }
}

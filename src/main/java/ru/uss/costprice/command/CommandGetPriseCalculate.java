package ru.uss.costprice.command;

import ru.uss.costprice.CmdHelper;
import ru.uss.costprice.exeptions.IncorrectFormatSku;
import ru.uss.costprice.parsing.Parser;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Вадим on 16.05.2016.
 */
public class CommandGetPriseCalculate implements Command {
    @Override
    public void execute() {
        Set<String> skuList = new HashSet<>();
        System.out.println("Enter list SKU. If nothing is entered - it will print all items");
        do {
            try {
                String string = CmdHelper.readLine();
                if (string.equals(""))
                    break;
                String sku = Parser.getSkuFromString(string);
                skuList.add(sku);
            } catch (IncorrectFormatSku e) {
                System.out.println("invalid SKU");
            }
        } while (true);
        if (!skuList.isEmpty()) {
            // PriceManager.getInstance().printListSku(skuList);
        } else{
            System.out.println("request is empty");
        }


    }
}

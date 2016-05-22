package ru.uss.costprice.command;

import ru.uss.costprice.CmdHelper;
import ru.uss.costprice.PriceManager;
import ru.uss.costprice.exeptions.IncorrectFormatSku;
import ru.uss.costprice.parsing.Parser;


/**
 * Created by Вадим on 13.05.2016.
 */


public class CommandGetSerialOfSku implements Command {

    @Override
    public void execute() {

        try {
            System.out.println("Enter 16dgt of SKU:");
            String sku = Parser.getSkuFromString(CmdHelper.readLine());

            PriceManager.getInstance().printSerials(sku);

        } catch (IncorrectFormatSku e) {
            System.out.println("invalid SKU");
        } catch (NullPointerException e) {
            System.out.println("no have sn");
        }

    }
}

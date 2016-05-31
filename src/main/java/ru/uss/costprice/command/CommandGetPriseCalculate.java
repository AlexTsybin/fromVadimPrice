package ru.uss.costprice.command;

import ru.uss.costprice.CmdHelper;
import ru.uss.costprice.dao.CostPriceDao;
import ru.uss.costprice.dao.mySql.MySqlDaoFactory;
import ru.uss.costprice.exeptions.IncorrectFormatSku;
import ru.uss.costprice.model.BasisCalculation;
import ru.uss.costprice.Parser;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Вадим on 16.05.2016
 */
public class CommandGetPriseCalculate implements Command {
    @Override
    public void execute() {
        Set<String> skuList = new HashSet<>();
        System.out.println("Enter list SKU. If nothing is entered - it will print all items");

        try {
            String string = CmdHelper.readLine();
            List<String> sku = Parser.getSkuFromString(string);
            skuList.addAll(sku);
        } catch (IncorrectFormatSku e) {
            System.out.println("invalid SKU");
        }

        if (!skuList.isEmpty()) {
            List<BasisCalculation> result = new ArrayList<>();
            MySqlDaoFactory factory = new MySqlDaoFactory();

            try (Connection connection = factory.getConnection()) {
                CostPriceDao dao = factory.getCostPriceDao(connection);
                result = dao.getBasisSku(skuList.toArray(new String[skuList.size()]));

            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println(result);
            // PriceManager.getInstance().printListSku(skuList);
        } else {
            System.out.println("request is empty");
        }


    }
}

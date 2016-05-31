package ru.uss.costprice.command;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import ru.uss.costprice.CmdHelper;
import ru.uss.costprice.Parser;
import ru.uss.costprice.dao.CostPriceDao;
import ru.uss.costprice.dao.DaoFactory;
import ru.uss.costprice.dao.mySql.MySqlDaoFactory;
import ru.uss.costprice.model.Jewel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Komyshenets on 30.05.2016
 */
public class CommandLoadCsv implements Command {
    @Override
    public void execute() {
        try {
            System.out.println("Enter CSV path:");
            Path path = Paths.get(CmdHelper.readLine());
            List<Jewel> jewelList = Parser.getSkuFromCsv(Files.newInputStream(path));

            System.out.println("Start loading...");
            DaoFactory factory = new MySqlDaoFactory();
            try (Connection connection = factory.getConnection()) {
                CostPriceDao dao = factory.getCostPriceDao(connection);

                for (Jewel j : jewelList) {
                    try {
                        dao.addJewel(j);
                    } catch (Exception  e) {
                        System.out.println("Ex: "+j.getSerialNumber());
                    }
                }

            } catch (SQLException e) {
                System.out.println("Connect error");
            }

        } catch (IOException e) {
            System.out.println("Invalid Path");
        }
    }
}

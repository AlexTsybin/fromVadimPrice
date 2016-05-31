package ru.uss.costprice.servlets;

import ru.uss.costprice.Parser;
import ru.uss.costprice.dao.CostPriceDao;
import ru.uss.costprice.dao.DaoFactory;
import ru.uss.costprice.dao.mySql.MySqlDaoFactory;
import ru.uss.costprice.model.Jewel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
@WebServlet(name = "LoadServlet", urlPatterns = "/load")
public class LoadServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String pathString = request.getParameter("csvPath");
            Path path = Paths.get(pathString);
            List<Jewel> jewelList = Parser.getSkuFromCsv(Files.newInputStream(path));


            DaoFactory factory = new MySqlDaoFactory();
            try (Connection connection = factory.getConnection()) {
                CostPriceDao dao = factory.getCostPriceDao(connection);

                for (Jewel j : jewelList) {
                    try {
                        dao.addJewel(j);
                    } catch (Exception e) {
                        System.out.println(j.getSerialNumber());
                    }
                }

            } catch (SQLException e) {
                System.out.println("Connect error");
            }

        } catch (IOException e) {
            System.out.println("Invalid Path");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}

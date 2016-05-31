package ru.uss.costprice.servlets;

import ru.uss.costprice.dao.CostPriceDao;
import ru.uss.costprice.dao.mySql.MySqlDaoFactory;
import ru.uss.costprice.exeptions.IncorrectFormatSku;
import ru.uss.costprice.model.BasisCalculation;
import ru.uss.costprice.Parser;
import ru.uss.costprice.model.StockPrice;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Komyshenets on 29.05.2016
 */
@WebServlet(name = "PriceServlet", urlPatterns = "/price")
public class PriceServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String listSku = request.getParameter("sku");
        MySqlDaoFactory factory = new MySqlDaoFactory();
        try (Connection connection = factory.getConnection()) {

            List<String> sku = Parser.getSkuFromString(listSku);
            CostPriceDao dao = factory.getCostPriceDao(connection);

            List<BasisCalculation> result = dao.getBasisSku(sku.toArray(new String[sku.size()]));

            request.setAttribute("skuList", result);
        } catch (IncorrectFormatSku | SQLException e) {
            e.printStackTrace();
        }

        request.setAttribute("stock", StockPrice.getLastPrice());
        request.getRequestDispatcher("WEB-INF/pricelist.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("skuList", new ArrayList<>());
        request.getRequestDispatcher("WEB-INF/pricelist.jsp").forward(request, response);
    }
}

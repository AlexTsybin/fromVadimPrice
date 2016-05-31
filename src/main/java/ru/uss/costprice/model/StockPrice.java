package ru.uss.costprice.model;

import ru.uss.costprice.dao.CostPriceDao;
import ru.uss.costprice.dao.DaoFactory;
import ru.uss.costprice.dao.mySql.MySqlDaoFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * Created by Вадим on 18.05.2016.
 */
public class StockPrice {
    private Date date;
    private double usd;
    private double gold;
    private double kr17UsdPrice;
    private double kr57UsdPrice;
    private double kr57bigUsdPrice;
    private double preciousUsdPrice;
    private static List<StockPrice> historyStock = new ArrayList<>();

    public void setDate(Date date) {
        this.date = date;
    }

    public void setUsd(double usd) {
        this.usd = usd;
    }

    public void setGold(double gold) {
        this.gold = gold;
    }

    public void setKr17UsdPrice(double kr17UsdPrice) {
        this.kr17UsdPrice = kr17UsdPrice;
    }

    public void setKr57UsdPrice(double kr57UsdPrice) {
        this.kr57UsdPrice = kr57UsdPrice;
    }

    public void setKr57bigUsdPrice(double kr57bigUsdPrice) {
        this.kr57bigUsdPrice = kr57bigUsdPrice;
    }

    public void setPreciousUsdPrice(double preciousUsdPrice) {
        this.preciousUsdPrice = preciousUsdPrice;
    }

    public double getUsd() {
        return usd;
    }

    public double getGold() {
        return gold;
    }

    public double getKr17UsdPrice() {
        return kr17UsdPrice;
    }

    public double getKr57UsdPrice() {
        return kr57UsdPrice;
    }

    public double getKr57bigUsdPrice() {
        return kr57bigUsdPrice;
    }

    public double getPreciousUsdPrice() {
        return preciousUsdPrice;
    }

    public Date getDate() {
        return date;
    }

    public static StockPrice getLastPrice() {
        if (historyStock.isEmpty()) {
            //TODO load from DB
            DaoFactory factory = new MySqlDaoFactory();
            try (Connection connection = factory.getConnection()) {
                CostPriceDao dao = factory.getCostPriceDao(connection);
                historyStock = dao.getPriceHistory();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return historyStock.stream().max((o1, o2) -> o1.date.compareTo(o2.date)).get();
    }
}

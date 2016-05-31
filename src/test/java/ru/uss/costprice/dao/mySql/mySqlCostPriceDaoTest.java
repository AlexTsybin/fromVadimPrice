package ru.uss.costprice.dao.mySql;

import org.junit.Assert;
import org.junit.Test;
import ru.uss.costprice.dao.CostPriceDao;
import ru.uss.costprice.model.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Komyshenets on 28.05.2016.
 */
public class MySqlCostPriceDaoTest {
    @Test
    public void getPriceHistory() throws Exception {
        List<StockPrice> result ;
        MySqlDaoFactory factory = new MySqlDaoFactory();
        try (Connection connection = factory.getConnection()) {
            CostPriceDao dao = factory.getCostPriceDao(connection);
            result = dao.getPriceHistory();
        }
        Assert.assertTrue(result.size()>0);
    }

    @Test
    public void addJewel() {
        boolean count = false;
        MySqlDaoFactory factory = new MySqlDaoFactory();
        try (Connection connection = factory.getConnection()) {
            CostPriceDao dao = factory.getCostPriceDao(connection);

            List<Gemstone> gem = new ArrayList<>();
            gem.add(new Gemstone(1, TypeStone.AMETHYST, null, 1, null, 1.0));
            count = dao.addJewel(new Jewel("test0", "sku", 1.0, 1.0, LocalDate.now(), gem));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Assert.assertTrue(count);
    }

    @Test
    public void getBasisSerial() throws Exception {
        List<BasisCalculation> result;
        MySqlDaoFactory factory = new MySqlDaoFactory();
        try (Connection connection = factory.getConnection()) {
            CostPriceDao dao = factory.getCostPriceDao(connection);
            result = dao.getBasisSerial("10-02-1200-10333");
        }
        Assert.assertEquals(result.size(), 3);
    }

    @Test
    public void getQuerySkuList() throws Exception {
        MySqlCostPriceDao mySqlCostPriceDao = new MySqlCostPriceDao(null);
        String result = mySqlCostPriceDao.getQuerySkuList();
        Assert.assertNotNull(result);
    }

    @Test
    public void flipArraySkuToLine() throws Exception {
        MySqlCostPriceDao mySqlCostPriceDao = new MySqlCostPriceDao(null);
        String result = mySqlCostPriceDao.flipArraySkuToLine("aa", "bb");
        Assert.assertEquals(result.length(), 9);
    }

    @Test
    public void getBasisSku() throws Exception {
        List<BasisCalculation> result;
        MySqlDaoFactory factory = new MySqlDaoFactory();
        try (Connection connection = factory.getConnection()) {
            CostPriceDao dao = factory.getCostPriceDao(connection);
            result = dao.getBasisSku("20-02-1000-24162", "10-02-1200-10333");
        }
        Assert.assertEquals(result.size(), 2);
    }

}
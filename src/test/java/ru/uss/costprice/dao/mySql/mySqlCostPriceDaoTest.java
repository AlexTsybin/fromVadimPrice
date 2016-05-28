package ru.uss.costprice.dao.mySql;

import org.junit.Assert;
import org.junit.Test;
import ru.uss.costprice.dao.CostPriceDao;
import ru.uss.costprice.model.BasisCalculation;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Komyshenets on 28.05.2016.
 */
public class MySqlCostPriceDaoTest {
    @Test
    public void getBasisSerial() throws Exception {
        List<BasisCalculation> result;
        MySqlDaoFactory factory = new MySqlDaoFactory();
        try (Connection connection = factory.getConnection()) {
            CostPriceDao dao = factory.getCostPriceDao(connection);
            result = dao.getBasisSerial( "10-02-1200-10333");
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
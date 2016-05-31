package ru.uss.costprice.model;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Komyshenets on 31.05.2016.
 */
public class StockPriceTest {
    @Test
    public void getLastPrice() throws Exception {
        StockPrice lastItem = StockPrice.getLastPrice();
        System.out.println(lastItem.getKr17UsdPrice());
        Assert.assertTrue(lastItem != null);
    }

}
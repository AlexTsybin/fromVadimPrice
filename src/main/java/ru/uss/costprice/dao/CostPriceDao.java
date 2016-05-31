package ru.uss.costprice.dao;

import ru.uss.costprice.model.BasisCalculation;
import ru.uss.costprice.model.Jewel;
import ru.uss.costprice.model.StockPrice;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Komyshenets on 28.05.2016.
 */
public interface CostPriceDao {
    List<StockPrice> getPriceHistory();

    boolean addJewel(Jewel jewel) throws SQLException;

    List<BasisCalculation> getBasisSerial(String sku);

    List<BasisCalculation> getBasisSku(String... sku);
}

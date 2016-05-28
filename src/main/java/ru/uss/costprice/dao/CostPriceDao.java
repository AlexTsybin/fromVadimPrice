package ru.uss.costprice.dao;

import ru.uss.costprice.model.BasisCalculation;
import ru.uss.costprice.model.Jewel;

import java.util.List;

/**
 * Created by Komyshenets on 28.05.2016.
 */
public interface CostPriceDao {

     void addJewel(Jewel jewel);

     List<BasisCalculation> getBasisSerial(String sku);

     List<BasisCalculation> getBasisSku(String... sku);
}

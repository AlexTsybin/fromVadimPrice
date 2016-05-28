package ru.uss.costprice.dao;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Komyshenets on 28.05.2016.
 */
public interface DaoFactory {
    Connection getConnection() throws SQLException;
    CostPriceDao getCostPriceDao(Connection connection);

}

package ru.uss.costprice.dao.mySql;

import ru.uss.costprice.dao.CostPriceDao;
import ru.uss.costprice.dao.DaoFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Komyshenets on 28.05.2016.
 */
public class MySqlDaoFactory implements DaoFactory {
    private final String USER = "root";//Логин пользователя
    private final String PASSWORD = "root";//Пароль пользователя
    private final String URL = "jdbc:mysql://localhost:3306/jewelry_factory";//URL адрес
    private final String driver = "com.mysql.jdbc.Driver";//Имя драйвера

    public MySqlDaoFactory() {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    @Override
    public CostPriceDao getCostPriceDao(Connection connection) {
        return new MySqlCostPriceDao(connection);
    }


}

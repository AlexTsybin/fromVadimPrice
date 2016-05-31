package ru.uss.costprice.dao.mySql;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import ru.uss.costprice.dao.CostPriceDao;
import ru.uss.costprice.model.BasisCalculation;
import ru.uss.costprice.model.Gemstone;
import ru.uss.costprice.model.Jewel;
import ru.uss.costprice.model.StockPrice;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Komyshenets on 28.05.2016.
 */
class MySqlCostPriceDao implements CostPriceDao {
    private final Connection connection;

    MySqlCostPriceDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<StockPrice> getPriceHistory() {
        List<StockPrice> result = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM stok_price ORDER BY 'date' DESC")) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                StockPrice stockPrice = new StockPrice();
                stockPrice.setDate(rs.getDate("date"));
                stockPrice.setGold(rs.getDouble("gold"));
                stockPrice.setUsd(rs.getDouble("usd"));
                stockPrice.setKr17UsdPrice(rs.getDouble("kr_17"));
                stockPrice.setKr57UsdPrice(rs.getDouble("kr_57"));
                stockPrice.setKr57bigUsdPrice(rs.getDouble("kr_57b"));
                stockPrice.setPreciousUsdPrice(rs.getDouble("prec"));
                result.add(stockPrice);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public boolean addJewel(Jewel jewel) throws SQLException {
        connection.setAutoCommit(false);
        try (PreparedStatement psJew = connection.prepareStatement(
                "INSERT INTO `jewelry_factory`.`jewel_item`" +
                        "(`serial_n`,`sku`,`date_release`,`w_net`,`w_gross`)" +
                        "VALUES  (?,?,?,?,?)")
        ) {
            psJew.setString(1, jewel.getSerialNumber());
            psJew.setString(2, jewel.getSku());
            if (jewel.getReleaseDate() != null) {
                psJew.setDate(3, Date.valueOf(jewel.getReleaseDate()));
            } else {
                psJew.setNull(3, Types.DATE);
            }
            psJew.setDouble(4, jewel.getNetWeight());
            psJew.setDouble(5, jewel.getGrossWeight());
            psJew.executeUpdate();

            for (Gemstone gemstone : jewel.getStones()) {
                try (PreparedStatement psGem = connection.prepareStatement(
                        "INSERT INTO `jewelry_factory`.`gemstone_set`\n" +
                                "(`s_n`,`count_stone`,`stone_id`,`shape_id`,`size`,`quality`,`weight`)\n" +
                                "VALUES (?,?,?,?,?,?,?);")
                ) {
                    psGem.setString(1, jewel.getSerialNumber());
                    psGem.setInt(2, gemstone.getCount());
                    psGem.setInt(3, gemstone.getType().ordinal() + 1);
                    if (gemstone.getCut() != null) {
                        psGem.setInt(4, gemstone.getCut().ordinal() + 1);
                    } else {
                        psGem.setNull(4, Types.INTEGER);
                    }
                    psGem.setDouble(5, gemstone.getSize());
                    psGem.setString(6, gemstone.getQuality());
                    psGem.setDouble(7, gemstone.getWeightCt());
                    psGem.executeUpdate();
                }
            }
            connection.commit();
            return true;
        } catch (Exception e) {
            connection.rollback();
            e.printStackTrace();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    @Override
    public List<BasisCalculation> getBasisSerial(String serialN) {
        List<BasisCalculation> basisCalcList = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(getQuerySerialList())) {
            ps.setString(1, serialN);
            ResultSet rs = ps.executeQuery();
            basisCalcList = parseResultSet(rs, "serial_n");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return basisCalcList;
    }

    @Override
    public List<BasisCalculation> getBasisSku(String... sku) {
        List<BasisCalculation> basisCalcList = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(getQuerySkuList().replace("?", flipArraySkuToLine(sku)))) {
            ResultSet rs = ps.executeQuery();
            basisCalcList = parseResultSet(rs, "sku");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return basisCalcList;
    }

    private List<BasisCalculation> parseResultSet(ResultSet resultSet, String id) throws SQLException {
        List<BasisCalculation> basisCalcList = new ArrayList<>();
        while (resultSet.next()) {
            BasisCalculation basis = new BasisCalculation();
            basis.setId(resultSet.getString(id));
            basis.setW_gross(resultSet.getDouble("w_gross"));
            basis.setW_net(resultSet.getDouble("w_net"));
            basis.setCount_stone(resultSet.getInt("count_stone"));
            basis.setKR_17(resultSet.getDouble("KR_17"));
            basis.setKR_57s(resultSet.getDouble("KR_57s"));
            basis.setPrecious(resultSet.getDouble("PREC"));
            basis.setKR_57b(resultSet.getDouble("KR_57b"));
            basisCalcList.add(basis);
        }
        return basisCalcList;
    }

    private String getQuerySerialList() {
        return "SELECT \n" +
                "date_release\n" +
                ", serial_n\n" +
                ", w_gross\n" +
                ", w_net\n" +
                ", count_stone\n" +
                ", SUM(KR_17) as 'KR_17'\n" +
                ", SUM(KR_57s) as 'KR_57s'\n" +
                ", SUM(PREC) as 'PREC'\n" +
                ", SUM(KR_57b) as 'KR_57b'\n" +
                "FROM(\n" +
                "\n" +
                "SELECT \n" +
                "j.date_release\n" +
                ", j.serial_n\n" +
                ", j.w_gross\n" +
                ", j.w_net\n" +
                ", g.count_stone\n" +
                ", (\n" +
                "SELECT g.weight\n" +
                "FROM shape_cut sh\n" +
                "WHERE g.shape_id = sh.id and sh.type=\"KR_17\") as 'KR_17'\n" +
                ",(\n" +
                "SELECT g.weight\n" +
                "FROM shape_cut sh\n" +
                "WHERE g.shape_id = sh.id and sh.type=\"KR_57\" and g.size<2.5) as 'KR_57s'\n" +
                ",(\n" +
                "SELECT g.weight\n" +
                "FROM stone_type st\n" +
                "WHERE g.stone_id = st.id and st.type in (\"SAPPHIRE\",\"EMERALD\",\"RUBY\")) as 'PREC'\n" +
                ",(\n" +
                "SELECT g.weight\n" +
                "FROM shape_cut sh\n" +
                "WHERE g.shape_id = sh.id and sh.type=\"KR_57\" and g.size>=2.5) as 'KR_57b'\n" +
                "\n" +
                "FROM gemstone_set g\n" +
                "JOIN jewel_item j ON j.serial_n = g.s_n\n" +
                "\n" +
                "WHERE j.sku= ? \n" +
                ") \n" +
                "as splited\n" +
                "group by serial_n\n" +
                "ORDER BY date_release DESC";
    }

    String getQuerySkuList() {
        return "SELECT * FROM(\n" +
                "SELECT \n" +
                "sku\n" +
                ", w_gross\n" +
                ", w_net\n" +
                ", count_stone\n" +
                ", SUM(KR_17) as 'KR_17'\n" +
                ", SUM(KR_57s) as 'KR_57s'\n" +
                ", SUM(PREC) as 'PREC'\n" +
                ", SUM(KR_57b) as 'KR_57b'\n" +
                "FROM(\n" +
                "\n" +
                "SELECT \n" +
                "j.sku\n" +
                ", j.date_release\n" +
                ", j.serial_n\n" +
                ", j.w_gross\n" +
                ", j.w_net\n" +
                ", g.count_stone\n" +
                ", (\n" +
                "SELECT g.weight\n" +
                "FROM shape_cut sh\n" +
                "WHERE g.shape_id = sh.id and sh.type='KR_17') as 'KR_17'\n" +
                ",(\n" +
                "SELECT g.weight\n" +
                "FROM shape_cut sh\n" +
                "WHERE g.shape_id = sh.id and sh.type='KR_57' and g.size<2.5) as 'KR_57s'\n" +
                ",(\n" +
                "SELECT g.weight\n" +
                "FROM stone_type st\n" +
                "WHERE g.stone_id = st.id and st.type in ('SAPPHIRE','EMERALD','RUBY')) as 'PREC'\n" +
                ",(\n" +
                "SELECT g.weight\n" +
                "FROM shape_cut sh\n" +
                "WHERE g.shape_id = sh.id and sh.type='KR_57' and g.size>=2.5) as 'KR_57b'\n" +
                "\n" +
                "FROM gemstone_set g\n" +
                "JOIN jewel_item j ON j.serial_n = g.s_n\n" +
                "\n" +
                "WHERE j.sku IN (?) \n" +
                ") \n" +
                "as splited group by serial_n ORDER BY date_release DESC\n" +
                ") as sku_list group by sku\n";

    }

    String flipArraySkuToLine(String... sku) {
        return "\"" + String.join("\",\"", sku) + "\"";
    }
}

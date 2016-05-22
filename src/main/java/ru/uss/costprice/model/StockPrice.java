package ru.uss.costprice.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by Вадим on 18.05.2016.
 */
public class StockPrice {
    private static final String PATH = "src/main/resources/stock.properties";
    private LocalDate date;
    private int usd;
    private int gold;
    private int kr17UsdPrice;
    private int kr57UsdPrice;
    private int kr57bigUsdPrice;
    private int preciousUsdPrice;

    private static List<StockPrice> historyStock = new ArrayList<>();

    static {
        loadFromProperty();
    }

    private static void loadFromProperty() {
        try {
            Properties properties = new Properties();
            properties.load(Files.newInputStream(Paths.get(PATH)));
            int usd = (int) properties.get("usd");
            int gold = (int) properties.get("gold");
            int kr17 = (int) properties.get("kr17");
            int kr57 = (int) properties.get("kr57");
            int kr57big = (int) properties.get("kr57big");
            int precious = (int) properties.get("precious");
            new StockPrice(usd, gold, kr17, kr57, kr57big, precious);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getUsd() {
        return usd;
    }

    public int getGold() {
        return gold;
    }

    public int getKr17UsdPrice() {
        return kr17UsdPrice;
    }

    public int getKr57UsdPrice() {
        return kr57UsdPrice;
    }

    public int getKr57bigUsdPrice() {
        return kr57bigUsdPrice;
    }

    public int getPreciousUsdPrice() {
        return preciousUsdPrice;
    }

    public LocalDate getDate() {
        return date;
    }

    public static StockPrice getLastPrice() {
        return historyStock.stream().max((o1, o2) -> o1.date.compareTo(o2.date)).get();
    }

    public StockPrice(int usd, int gold, int kr17UsdPrice, int kr57UsdPrice, int kr57bigUsdPrice, int preciousUsdPrice) {
        this.usd = usd;
        this.gold = gold;
        this.kr17UsdPrice = kr17UsdPrice;
        this.kr57UsdPrice = kr57UsdPrice;
        this.kr57bigUsdPrice = kr57bigUsdPrice;
        this.preciousUsdPrice = preciousUsdPrice;
        this.date = LocalDate.now();
        historyStock.add(this);
    }

    private StockPrice() {
        this.kr17UsdPrice = 0;
        this.kr57UsdPrice = 0;
        this.kr57bigUsdPrice = 0;
        this.preciousUsdPrice = 0;
    }

    public StockPrice(int usd, int gold) {
        StockPrice lastStock = getLastPrice() != null ? getLastPrice() : new StockPrice();
        this.usd = usd;
        this.gold = gold;
        this.kr17UsdPrice = lastStock.getKr17UsdPrice();
        this.kr57UsdPrice = lastStock.getKr57UsdPrice();
        this.kr57bigUsdPrice = lastStock.getKr57bigUsdPrice();
        this.preciousUsdPrice = lastStock.getPreciousUsdPrice();
        this.date = LocalDate.now();
        historyStock.add(this);
    }
}

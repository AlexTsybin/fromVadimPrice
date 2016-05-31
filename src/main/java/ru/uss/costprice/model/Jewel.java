package ru.uss.costprice.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by vadelic on 30.04.2016
 */
public class Jewel {
    private String serialNumber;
    private String sku;
    private double grossWeight;
    private double netWeight;
    private LocalDate releaseDate;
    private List<Gemstone> stones;

    public Jewel(String serialNumber, String sku, double grossWeight, double netWeight, LocalDate releaseDate, List<Gemstone> stones) {
        this.serialNumber = serialNumber;
        this.sku = sku;
        this.grossWeight = grossWeight;
        this.netWeight = netWeight;
        this.releaseDate = releaseDate;
        this.stones = stones;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public String getSku() {
        return sku;
    }

    public double getGrossWeight() {
        return grossWeight;
    }

    public double getNetWeight() {
        return netWeight;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public List<Gemstone> getStones() {
        if (stones != null)
            return Collections.unmodifiableList(stones);
        else return new ArrayList<>();
    }

    //==================================================
    public int getCountStones() {
        int countStones = 0;
        for (Gemstone stone : stones) {
            countStones += stone.getCount();
        }
        return countStones;
    }

    public double getWeightKr17() {
        double kr17 = 0;
        for (Gemstone stone : stones) {
            if (stone.getCut() == ShapeCut.KR_17) {
                kr17 += stone.getWeightCt();
            }
        }
        return kr17;
    }

    public double getWeightKr57() {
        double kr57 = 0;
        for (Gemstone stone : stones) {
            if (stone.getCut() == ShapeCut.KR_57 && stone.getSize() < 2.5f) {
                kr57 += stone.getWeightCt();
            }
        }
        return kr57;
    }

    public double getWeightPrecious() {
        double precious = 0;
        for (Gemstone stone : stones) {
            if (stone.getType() == TypeStone.SAPPHIRE ||
                    stone.getType() == TypeStone.RUBY ||
                    stone.getType() == TypeStone.EMERALD) {
                precious += stone.getWeightCt();
            }
        }
        return precious;
    }

    public double getWeightKr57big() {
        double kr57b = 0;
        for (Gemstone stone : stones) {
            if (stone.getCut() == ShapeCut.KR_57 && stone.getSize() >= 2.5f) {
                kr57b += stone.getWeightCt();
            }
        }
        return kr57b;
    }

    public double getCost(StockPrice stockPrice) {
        double result = 0;
// TODO: 21.05.2016

        return result;
    }

    @Override
    public String toString() {
        return serialNumber + "\t" +
                sku + "\t" +
                getCountStones() + "\t" +
                getWeightKr17() + "\t" +
                getWeightKr57() + "\t" +
                getWeightPrecious();
    }
}

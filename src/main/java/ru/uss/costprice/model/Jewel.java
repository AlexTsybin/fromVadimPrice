package ru.uss.costprice.model;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

/**
 * Created by vadelic on 30.04.2016.
 */
public class Jewel {
    private String serialNumber;
    private String sku;
    private Double grossWeight;
    private Double netWeight;
    private LocalDate releaseDate;
    private List<Gemstone> stones;

    public List<Gemstone> getStones() {
        return Collections.unmodifiableList(stones);
    }

    public Jewel(String sku, List<Gemstone> stones) {
        this.sku = sku;
        this.serialNumber = "";
        this.netWeight = 0.0;
        this.grossWeight = 0.0;
        this.stones = stones;
    }

    public Jewel(String serialNumber, String sku, Double grossWeight, Double netWeight, LocalDate releaseDate, List<Gemstone> stones) {
        this.serialNumber = serialNumber;
        this.sku = sku;
        this.grossWeight = grossWeight;
        this.netWeight = netWeight;
        this.releaseDate = releaseDate;
        this.stones = stones;
    }

    public String getSku() {
        return sku;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public double getWeghtPrecious() {
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

    public double getWeghtKr17() {
        double kr17 = 0;
        for (Gemstone stone : stones) {
            if (stone.getCut() == ShapeCut.KR_17) {
                kr17 += stone.getWeightCt();
            }
        }
        return kr17;
    }

    public double getWeghtKr57() {
        double kr57 = 0;
        for (Gemstone stone : stones) {
            if (stone.getCut() == ShapeCut.KR_57) {
                kr57 += stone.getWeightCt();
            }
        }
        return kr57;
    }

    public int countStones() {
        int countStones = 0;
        for (Gemstone stone : stones) {
            countStones += stone.getCount();
        }
        return countStones;
    }

    @Override
    public String toString() {
        return serialNumber + "\t" +
                sku + "\t" +
                countStones() + "\t" +
                getWeghtKr17() + "\t" +
                getWeghtKr57() + "\t" +
                getWeghtPrecious();
    }
}

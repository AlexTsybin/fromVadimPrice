package ru.uss.costprice.model;

/**
 * Created by vadelic on 30.04.2016.
 */
public class Gemstone {
    private int count;
    private TypeStone type;
    private ShapeCut cut;
    private String size;
    private String quality;
    private Double weightCt;

    public int getCount() {
        return count;
    }

    public TypeStone getType() {
        return type;
    }

    public ShapeCut getCut() {
        return cut;
    }

    public Double getWeightCt() {
        return weightCt;
    }

    public Gemstone(int count, TypeStone type, ShapeCut cut, String size, String quality, Double weightCt) {
        this.count = count;
        this.type = type;
        this.cut = cut;
        this.size = size;
        this.quality = quality;
        this.weightCt = weightCt;
    }

    @Override
    public String toString() {
        return String.format("%3d | %8s | %5s | %6s | %5.3f",
                count,
                type,
                cut,
                size,
                weightCt);
    }
}

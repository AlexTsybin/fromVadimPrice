package ru.uss.costprice.model;

/**
 * Created by vadelic on 30.04.2016.
 */
public class Gemstone {
    private int count;
    private TypeStone type;
    private ShapeCut cut;
    private double size;
    private String quality;
    private Double weightCt;


    public void setCount(int count) {
        this.count = count;
    }

    public void setType(TypeStone type) {
        this.type = type;
    }

    public void setCut(ShapeCut cut) {
        this.cut = cut;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public void setWeightCt(Double weightCt) {
        this.weightCt = weightCt;
    }

    public int getCount() {
        return count;
    }

    public TypeStone getType() {
        return type;
    }

    public ShapeCut getCut() {
        return cut;
    }

    public double getSize() {
        return size;
    }

    public Double getWeightCt() {
        return weightCt;
    }

    public String getQuality() {
        return quality;
    }

    public Gemstone(int count, TypeStone type, ShapeCut cut, double size, String quality, Double weightCt) {
        this.count = count;
        this.type = type;
        this.cut = cut;
        this.size = size;
        this.quality = quality;
        this.weightCt = weightCt;
    }

    @Override
    public String toString() {
        return String.format("%3d | %8s | %5s | %6.2f | %5.3f",
                count,
                type,
                cut,
                size,
                weightCt);
    }
}

package ru.uss.costprice.model;

/**
 * Created by Komyshenets on 28.05.2016.
 */
public class BasisCalculation {
    private String id;
    private double w_gross;
    private double w_net;
    private int count_stone;
    private double KR_17;
    private double KR_57s;
    private double precious;
    private double KR_57b;

    @Override
    public String toString() {
        return "id=" + id  +
                ", w_gross=" + w_gross +
                ", w_net=" + w_net +
                ", count=" + count_stone +
                ", KR_17=" + KR_17 +
                ", KR_57s=" + KR_57s +
                ", prec=" + precious +
                ", KR_57b=" + KR_57b ;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getW_gross() {
        return w_gross;
    }

    public void setW_gross(double w_gross) {
        this.w_gross = w_gross;
    }

    public double getW_net() {
        return w_net;
    }

    public void setW_net(double w_net) {
        this.w_net = w_net;
    }

    public int getCount_stone() {
        return count_stone;
    }

    public void setCount_stone(int count_stone) {
        this.count_stone = count_stone;
    }

    public double getKR_17() {
        return KR_17;
    }

    public void setKR_17(double KR_17) {
        this.KR_17 = KR_17;
    }

    public double getKR_57s() {
        return KR_57s;
    }

    public void setKR_57s(double KR_57s) {
        this.KR_57s = KR_57s;
    }

    public double getPrecious() {
        return precious;
    }

    public void setPrecious(double precious) {
        this.precious = precious;
    }

    public double getKR_57b() {
        return KR_57b;
    }

    public void setKR_57b(double KR_57b) {
        this.KR_57b = KR_57b;
    }


}

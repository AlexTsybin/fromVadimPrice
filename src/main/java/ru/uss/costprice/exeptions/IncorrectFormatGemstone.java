package ru.uss.costprice.exeptions;

/**
 * Created by ����� on 11.05.2016.
 */
public class IncorrectFormatGemstone extends Throwable {

    public IncorrectFormatGemstone(String line) {
        super(line);
    }
}

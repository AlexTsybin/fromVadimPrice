package ru.uss.costprice.exeptions;

/**
 * Created by Вадим on 11.05.2016.
 */
public class IncorrectFormatGemstone extends Throwable {

    public IncorrectFormatGemstone(String line) {
        super(line);
    }
}

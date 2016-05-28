package ru.uss.costprice.exeptions;

/**
 * Created by vadelic on 11.05.2016
 */
public class IncorrectFormatSku extends Throwable {
    public IncorrectFormatSku(String line) {
        super(line);
    }
}

package ru.uss.costprice.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Вадим on 01.05.2016.
 */
public enum ShapeCut {
    OVAL,
    BAGUETTE,
    TRILLION,
    CIRCLE,
    MARQUIS,
    SQUARE,
    KR_57,
    KR_17,
    PEAR;
    private static Map<String, ShapeCut> variable = new HashMap<>();
    static {
        variable.put("баг",BAGUETTE);
        variable.put("багет",BAGUETTE);
        variable.put("груш",PEAR);
        variable.put("груша",PEAR);
        variable.put("кв",SQUARE);
        variable.put("квадрат",SQUARE);
        variable.put("кр",CIRCLE);
        variable.put("круг",CIRCLE);
        variable.put("кр17",KR_17);
        variable.put("кр57",KR_57);
        variable.put("м-з",MARQUIS);
        variable.put("марк",MARQUIS);
        variable.put("маркиз",MARQUIS);
        variable.put("ов",OVAL);
        variable.put("овал",OVAL);
        variable.put("трилл",TRILLION);
    }

    public static ShapeCut getShapeCut(String name){
        ShapeCut select = variable.get(name);
        //TODO check null
//        if (select!=null)
        return select;
//        else
//            throw new IOException();
    }
}

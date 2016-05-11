package ru.uss.costprice.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ����� on 01.05.2016.
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
        variable.put("���",BAGUETTE);
        variable.put("�����",BAGUETTE);
        variable.put("����",PEAR);
        variable.put("�����",PEAR);
        variable.put("��",SQUARE);
        variable.put("�������",SQUARE);
        variable.put("��",CIRCLE);
        variable.put("����",CIRCLE);
        variable.put("��17",KR_17);
        variable.put("��57",KR_57);
        variable.put("�-�",MARQUIS);
        variable.put("����",MARQUIS);
        variable.put("������",MARQUIS);
        variable.put("��",OVAL);
        variable.put("����",OVAL);
        variable.put("�����",TRILLION);
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

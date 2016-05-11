package ru.uss.costprice.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ����� on 01.05.2016.
 */
public enum TypeStone {
    AMETHYST,
    TURQUOISE,
    DIAMOND,
    GARNET,
    EMERALD,
    RAUCH_TOPAZ,
    RUBY,
    SAPPHIRE,
    TOPAZ,
    TOPAZ_LONDON,
    TOPAZ_SWISS,
    CHRYSOLITE,
    CITRINE;

    private static Map<String, TypeStone> variable = new HashMap<>();
    static {
        variable.put("�������",AMETHYST);
        variable.put("���.",TURQUOISE);
        variable.put("������",TURQUOISE);
        variable.put("��",DIAMOND);
        variable.put("������",GARNET);
        variable.put("��",EMERALD);
        variable.put("����",RAUCH_TOPAZ);
        variable.put("����-�����",RAUCH_TOPAZ);
        variable.put("��",RUBY);
        variable.put("��",SAPPHIRE);
        variable.put("��.",SAPPHIRE);
        variable.put("�����",TOPAZ);
        variable.put("����� lond",TOPAZ_LONDON);
        variable.put("����� london",TOPAZ_LONDON);
        variable.put("����� swis",TOPAZ_SWISS);
        variable.put("����� swiss",TOPAZ_SWISS);
        variable.put("����",CHRYSOLITE);
        variable.put("��������",CHRYSOLITE);
        variable.put("���",CITRINE);
        variable.put("������",CITRINE);
    }

    public static TypeStone getTypeStone(String name){
        TypeStone select = variable.get(name);
        //TODO check null
        return select;
    }
}

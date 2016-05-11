package ru.uss.costprice.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Вадим on 01.05.2016.
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
        variable.put("аметист",AMETHYST);
        variable.put("бир.",TURQUOISE);
        variable.put("бирюза",TURQUOISE);
        variable.put("бр",DIAMOND);
        variable.put("гранат",GARNET);
        variable.put("из",EMERALD);
        variable.put("раух",RAUCH_TOPAZ);
        variable.put("раух-топаз",RAUCH_TOPAZ);
        variable.put("рб",RUBY);
        variable.put("сф",SAPPHIRE);
        variable.put("сф.",SAPPHIRE);
        variable.put("топаз",TOPAZ);
        variable.put("топаз lond",TOPAZ_LONDON);
        variable.put("топаз london",TOPAZ_LONDON);
        variable.put("топаз swis",TOPAZ_SWISS);
        variable.put("топаз swiss",TOPAZ_SWISS);
        variable.put("хриз",CHRYSOLITE);
        variable.put("хризолит",CHRYSOLITE);
        variable.put("цит",CITRINE);
        variable.put("цитрин",CITRINE);
    }

    public static TypeStone getTypeStone(String name){
        TypeStone select = variable.get(name);
        //TODO check null
        return select;
    }
}

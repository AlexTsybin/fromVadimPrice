package ru.uss.costprice.model;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

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
    CITRINE,
    BEARING,
    PEARL;

    private static Map<String, TypeStone> variable = new HashMap<>();

    static {
//        variable.put("аметист",AMETHYST);
//        variable.put("бир.",TURQUOISE);
//        variable.put("бирюза",TURQUOISE);
//        variable.put("бр",DIAMOND);
//        variable.put("гранат",GARNET);
//        variable.put("из",EMERALD);
//        variable.put("раух",RAUCH_TOPAZ);
//        variable.put("раух-топаз",RAUCH_TOPAZ);
//        variable.put("рб",RUBY);
//        variable.put("сф",SAPPHIRE);
//        variable.put("сф.",SAPPHIRE);
//        variable.put("топаз",TOPAZ);
//        variable.put("топаз lond",TOPAZ_LONDON);
//        variable.put("топаз london",TOPAZ_LONDON);
//        variable.put("топаз swis",TOPAZ_SWISS);
//        variable.put("топаз swiss",TOPAZ_SWISS);
//        variable.put("хриз",CHRYSOLITE);
//        variable.put("хризолит",CHRYSOLITE);
//        variable.put("цит",CITRINE);
//        variable.put("цитрин",CITRINE);

        variable = loadWords("src/main/webapp/WEB-INF/ex_typeStone.properties");

    }

     static Map<String, TypeStone> loadWords(String path) {
        Map<String, TypeStone> result = new HashMap<>();
        try {
            Properties properties = new Properties();
            Reader reader = new InputStreamReader(Files.newInputStream(Paths.get(path)), Charset.defaultCharset());
            properties.load(reader);

            for (String name : properties.stringPropertyNames()) {
                TypeStone typeStone = TypeStone.valueOf(properties.getProperty(name));
                result.put(name, typeStone);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static TypeStone getTypeStone(String name) {
        TypeStone select = variable.get(name.replace(" ", ""));
        //TODO check null
        return select;
    }
}

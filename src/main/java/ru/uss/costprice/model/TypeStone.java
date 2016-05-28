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
    CITRINE,
    BEARING,
    PEARL;

    private static Map<String, TypeStone> variable = new HashMap<>();

    static {
//        variable.put("�������",AMETHYST);
//        variable.put("���.",TURQUOISE);
//        variable.put("������",TURQUOISE);
//        variable.put("��",DIAMOND);
//        variable.put("������",GARNET);
//        variable.put("��",EMERALD);
//        variable.put("����",RAUCH_TOPAZ);
//        variable.put("����-�����",RAUCH_TOPAZ);
//        variable.put("��",RUBY);
//        variable.put("��",SAPPHIRE);
//        variable.put("��.",SAPPHIRE);
//        variable.put("�����",TOPAZ);
//        variable.put("����� lond",TOPAZ_LONDON);
//        variable.put("����� london",TOPAZ_LONDON);
//        variable.put("����� swis",TOPAZ_SWISS);
//        variable.put("����� swiss",TOPAZ_SWISS);
//        variable.put("����",CHRYSOLITE);
//        variable.put("��������",CHRYSOLITE);
//        variable.put("���",CITRINE);
//        variable.put("������",CITRINE);

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

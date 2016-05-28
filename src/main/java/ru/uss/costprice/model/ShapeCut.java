package ru.uss.costprice.model;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

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
    PEAR,
    CABOCHON;
    private static Map<String, ShapeCut> variable;

    static {
//        variable = loadWords("src/main/resources/ex_shapeCut.properties");
        variable = loadWords("src/main/webapp/WEB-INF/ex_shapeCut.properties");
    }

    public static Map<String, ShapeCut> loadWords(String path) {
        Map<String, ShapeCut> result = new HashMap<>();
        try {
            Properties properties = new Properties();
            Reader reader = new InputStreamReader(Files.newInputStream(Paths.get(path)), Charset.defaultCharset());
            properties.load(reader);

            for (String name : properties.stringPropertyNames()) {
                ShapeCut shapeCut = ShapeCut.valueOf(properties.getProperty(name));
                result.put(name, shapeCut);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getStr() {
        return String.valueOf(variable.size());
    }

    public static ShapeCut getShapeCut(String name) {
        ShapeCut select = variable.get(name);
        //TODO check null
//        if (select!=null)
        return select;
//        else
//            throw new IOException();
    }
}

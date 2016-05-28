package ru.uss.costprice.model;

import org.junit.Test;

import java.util.Map;

/**
 * Created by Komyshenets on 21.05.2016.
 */
public class ShapeCutTest {
    @Test
    public void loadWords() throws Exception {
        Map<String, ShapeCut> map = ShapeCut.loadWords("src/main/webapp/WEB-INF/ex_shapeCut.properties");
        System.out.println(map.keySet());


    }


}
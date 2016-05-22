package ru.uss.costprice.model;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by Komyshenets on 21.05.2016.
 */
public class TypeStoneTest {
    @Test
    public void loadWords() throws Exception {
        Map<String, TypeStone> map = TypeStone.loadWords("src/main/resources/ex_typeStone.properties");
        System.out.println(map.keySet());
    }

}
package ru.uss.costprice.parsing;

import org.junit.Test;
import ru.uss.costprice.exeptions.IncorrectFormatGemstone;
import ru.uss.costprice.model.Jewel;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by Вадим on 16.05.2016.
 */
public class ParserTest {
    @Test
    public void createStone() throws Exception {
        try {
            System.out.println(Parser.createStone("1 Бр кр17 1.00 2/3А 0.004ct"));

        } catch (IncorrectFormatGemstone incorrectFormatGemstone) {
            incorrectFormatGemstone.printStackTrace();
        }

    }

    private static final String ITEMS = "SP0178280;Подвеска 50-02-1010-10044;1,37;1,211;10.06.2015 0:00:00;1 Бр кр17 1.00 2/3А 0.004ct, 1 Бр кр17 1.20 2/3А 0.007ct, 1 Бр кр17 1.40 2/3А 0.010ct, 1 Жем белый круг диапз 5.00*4.50 0.155";
    private static final String PATH = "src/main/resources/ts.csv";
    @Test
    public void getSkuFromCsv() throws Exception {
        Map<String, List<Jewel>> result = Parser.getSkuFromCsv(new ByteArrayInputStream(ITEMS.getBytes(StandardCharsets.UTF_8)));
        assertEquals(1, result.size());
        result =Parser.getSkuFromCsv(Files.newInputStream(Paths.get(PATH)));
        assertEquals(144, result.size());

    }

}
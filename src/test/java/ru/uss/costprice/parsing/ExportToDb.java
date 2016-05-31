package ru.uss.costprice.parsing;

import org.junit.Test;
import ru.uss.costprice.Parser;
import ru.uss.costprice.model.Gemstone;
import ru.uss.costprice.model.Jewel;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Komyshenets on 23.05.2016.
 */
public class ExportToDb {
    private static final String PATH_MAIN = "src/main/resources/list_series.csv";
    private static List<Jewel> all;

    @Test
    public void getSkuFromCsv() throws Exception {
        List<Jewel> result = Parser.getSkuFromCsv(new FileInputStream(PATH_MAIN));
        all = new ArrayList<>();
        all.addAll(result);

        List<String> stringList = all.stream()
                //            .filter(jewel -> jewel.getSku().charAt(11) == '1')
                .map(ExportToDb::reformatJewelToSql)
                .collect(Collectors.toList());
        System.out.println(stringList.size());
        saveInFile(stringList);
        System.out.println("complete");
    }

    private static String reformatJewelToSql(Jewel jewel) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("(\"%.9s\",", jewel.getSerialNumber()));
        sb.append(String.format("\"%s\",", jewel.getSku()));
        if (jewel.getReleaseDate() != null)
            sb.append(String.format("\"%s\",", jewel.getReleaseDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
        else
            sb.append("null,");
        sb.append(String.format(Locale.ENGLISH, "%.3f,", jewel.getNetWeight()));
        sb.append(String.format(Locale.ENGLISH, "%.3f)", jewel.getGrossWeight()));
        return sb.toString();
    }

    @Test
    public void getDataForStoneSet() throws IOException {
        if (all == null) {
             List<Jewel> result = Parser.getSkuFromCsv(new FileInputStream(PATH_MAIN));
            all = new ArrayList<>();
            all.addAll(result);
        }
        List<String> stringList = new ArrayList<>();

        for (Jewel j : all) {
            stringList.addAll(reformatStonelToSql(j));
        }

        System.out.println(stringList.size());
        saveInFile(stringList);
        System.out.println("complete");

    }

    private static List<String> reformatStonelToSql(Jewel jewel) {

        List<String> gemList = new ArrayList<>();
        for (Gemstone g : jewel.getStones()) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(String.format("(\"%.9s\",", jewel.getSerialNumber()));
                sb.append(String.format("%d,", g.getCount()));
                sb.append(String.format("%d,", g.getType().ordinal() + 1));

                if (g.getCut() != null)
                    sb.append(String.format("%d,", g.getCut().ordinal() + 1));
                else
                    sb.append("null,");
                sb.append(String.format(Locale.ENGLISH, "%.3f,", g.getSize()));
                sb.append(String.format("\"%s\",", g.getQuality()));
                sb.append(String.format(Locale.ENGLISH, "%.3f)", g.getWeightCt()));
                gemList.add(sb.toString());
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        return gemList;
    }

    private static void saveInFile(List<String> saver) {
        try {
            FileWriter writer = new FileWriter(saver.size() + "items.txt");

            for (String s : saver) {
                writer.write(s + ",\r\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

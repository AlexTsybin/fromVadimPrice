package ru.uss.costprice.parsing;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import ru.uss.costprice.exeptions.IncorrectFormatGemstone;
import ru.uss.costprice.exeptions.IncorrectFormatSku;
import ru.uss.costprice.model.Gemstone;
import ru.uss.costprice.model.Jewel;
import ru.uss.costprice.model.ShapeCut;
import ru.uss.costprice.model.TypeStone;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by vadelic on 30.04.2016.
 */
public class Parser {

    private static List<String> kindStone;

    static {
        kindStone = new ArrayList<>();
        kindStone.add("swis");
        kindStone.add("swiss");
        kindStone.add("london");
        kindStone.add("lond");
        kindStone.add("");
    }

    public static Map<String, List<Jewel>> getSkuFromCsv(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        CSVParser csvParser = new CSVParser(reader, CSVFormat
                .newFormat(';')
                .withHeader(new String[]{"﻿Серия номенклатуры",
                        "Номенклатура",
                        "Вес изделия",
                        "Вес металла в изделии",
                        "Дата выпуска",
                        "Описание вставки"})
                .withSkipHeaderRecord(true));

        Map<String, List<Jewel>> jewelMap = new HashMap<>();
        List<CSVRecord> incGemstone = new ArrayList<>();
        List<CSVRecord> incSku = new ArrayList<>();

        for (CSVRecord rec : csvParser.getRecords()) {
            try {
                Jewel jewel = convertToJewel(rec);
                String sku = jewel.getSku();

                if (!jewelMap.containsKey(sku)) {
                    jewelMap.put(sku, new ArrayList<>());
                }
                jewelMap.get(sku).add(jewel);
            } catch (IncorrectFormatGemstone e) {
                incGemstone.add(rec);
                System.out.println(rec.get(5));

            } catch (IncorrectFormatSku e) {
                incSku.add(rec);
            }
        }

        System.out.println("add " + jewelMap.size() + " items");
        return jewelMap;
    }


    public static String getSkuFromString(String line) throws IncorrectFormatSku {
        Pattern pattern = Pattern.compile("\\d{2}-\\d{2}-.{2}\\d{2}-\\d{5}");
        Matcher matcher = pattern.matcher(line);
        if (!matcher.find())
            throw new IncorrectFormatSku();
        return matcher.group();
    }

    private static Jewel convertToJewel(CSVRecord record) throws IncorrectFormatGemstone, IncorrectFormatSku {

        String sn = record.get(0);
        String sku = getSkuFromString(record.get(1));

        double gross = Double.parseDouble(record.get(2).replace(',', '.'));
        double net = Double.parseDouble(record.get(3).replace(',', '.'));
        LocalDate date;
        try {
            date = LocalDate.parse(record.get(4).substring(0, 10), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        } catch (Exception e) {
            date = null;
        }
        String stones = record.get(5);
        return new Jewel(sn, sku, gross, net, date, getListGemstoneInJewel(stones));

    }

    public static List<Gemstone> getListGemstoneInJewel(String line) throws IncorrectFormatGemstone {
        List<Gemstone> result = new ArrayList<>();
        for (String s : line.trim().split(",")) {
            result.add(createStone(s));
        }

        return result;
    }

    public static Gemstone createStone(String line) throws IncorrectFormatGemstone {
        try {

            List<String> listDescription = new ArrayList<>(Arrays.asList(line.trim().split(" ")));
            if (listDescription.size() == 4) return null;


            if (kindStone.contains(listDescription.get(2).toLowerCase())) {
                listDescription.set(1, listDescription.get(1) + " " + listDescription.get(2));
                listDescription.remove(2);
            }

            if (!listDescription.get(4).contains("/")) {
                listDescription.add(4, "");
            }
            if (listDescription.get(5).contains("????.???")) {
                listDescription.remove(5);
            }

            int count = getInt(listDescription.get(0));
            TypeStone type = TypeStone.getTypeStone(listDescription.get(1).toLowerCase().trim());
            ShapeCut cut = ShapeCut.getShapeCut(listDescription.get(2).toLowerCase().trim());
            String size = listDescription.get(3);
            String quality = listDescription.get(4);
            Double weightCt = getDouble(listDescription.get(5));

            return new Gemstone(count, type, cut, size, quality, weightCt);
        } catch (Exception e) {
            throw new IncorrectFormatGemstone();
//            System.out.println("GEM ERROR " + listDescription + line);
//            return null;
        }
    }

    private static int getInt(String s) {
        StringBuilder sb = new StringBuilder();
        for (Character ch : s.toCharArray()) {
            if (Character.isDigit(ch)) sb.append(ch);
        }
        return Integer.parseInt(sb.toString());
    }

    private static double getDouble(String s) {
        try {
            StringBuilder sb = new StringBuilder();
            for (Character ch : s.toCharArray()) {
                if (Character.isDigit(ch) || ch == '.')
                    sb.append(ch);
            }
            return Double.parseDouble(sb.toString());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

}

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

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by vadelic on 30.04.2016
 */
public class Parser {

    private static List<String> kindStone;

    static {
        kindStone = new ArrayList<>();
        kindStone.add("swis");
        kindStone.add("swiss");
        kindStone.add("london");
        kindStone.add("lond");
        kindStone.add("ск.");
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

        for (CSVRecord rec : csvParser.getRecords()) {
            try {
                Jewel jewel = convertToJewel(rec);
                String sku = jewel.getSku();

                if (!jewelMap.containsKey(sku)) {
                    jewelMap.put(sku, new ArrayList<>());
                }
                jewelMap.get(sku).add(jewel);

            } catch (IncorrectFormatGemstone e) {
                System.out.println(e.getMessage());

            } catch (IncorrectFormatSku e) {
                e.printStackTrace();
            }
        }

        System.out.println("add " + jewelMap.size() + " items");
        return jewelMap;
    }


    public static String getSkuFromString(String line) throws IncorrectFormatSku {
        Pattern pattern = Pattern.compile("\\d{2}-\\d{2}-.{2}\\d{2}-\\d{5}");
        Matcher matcher = pattern.matcher(line);
        if (!matcher.find())
            throw new IncorrectFormatSku(line);
        return matcher.group();
    }

    private static Jewel convertToJewel(CSVRecord record) throws IncorrectFormatGemstone, IncorrectFormatSku {

        String serialN = record.get(0);
        String sku = getSkuFromString(record.get(1));

        double grossWeight = Double.parseDouble(record.get(2).replace(',', '.'));
        double netWeight = Double.parseDouble(record.get(3).replace(',', '.'));
        LocalDate dateRealise;
        try {
            dateRealise = LocalDate.parse(record.get(4).substring(0, 10), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        } catch (Exception e) {
            dateRealise = null;
        }
        List<Gemstone> gemstoneList = getListGemstoneInJewel(record.get(5));
        return new Jewel(serialN, sku, grossWeight, netWeight, dateRealise, gemstoneList);

    }

    private static List<Gemstone> getListGemstoneInJewel(String line) throws IncorrectFormatGemstone {
        List<Gemstone> result = new ArrayList<>();
        for (String s : line.trim().split(",")) {
            result.add(createStone(s));
        }

        return result;
    }

    static Gemstone createStone(String line) throws IncorrectFormatGemstone {

        try {

            List<String> listDescription = new ArrayList<>(Arrays.asList(line
                    .replace("розовый ", "")
                    .replace("белый ", "")
                    .replace("черный ", "")
                    .replace("культ ", "")
                    .trim()
                    .split(" ")));
            if (listDescription.size() == 4) return null;


            if (kindStone.contains(listDescription.get(2).toLowerCase())) {
                listDescription.set(1, listDescription.get(1) + " " + listDescription.get(2));
                listDescription.remove(2);
            }
            if (listDescription.get(1).equals("Подш ск.")) {
                listDescription.add(2, "");
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
            double size = getSize(listDescription.get(3));
            String quality = listDescription.get(4);
            Double weightCt = getDouble(listDescription.get(5));
            if (type == null)
                throw new IncorrectFormatGemstone(line);
            return new Gemstone(count, type, cut, size, quality, weightCt);
        } catch (Exception e) {
            throw new IncorrectFormatGemstone(line);
        }
    }

    private static double getSize(String s) {
        double size = 0.0;
        for (String st : s.split("\\*")) {
            if (size < getDouble(st)) {
                size = getDouble(st);
            }
        }
        return size;
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

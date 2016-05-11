package ru.uss.costprice.parsing;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by ????? on 30.04.2016.
 */
public class Parser {

    private List<String> kindStone;

    public Parser() {
        this.kindStone = new ArrayList<>();
        this.kindStone.add("swis");
        this.kindStone.add("swiss");
        this.kindStone.add("london");
        this.kindStone.add("lond");
        this.kindStone.add("");
    }

    public List<Jewel> getSkuFromCsv(InputStream is) throws IOException {
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

        return csvParser.getRecords()
                .stream()
                .map(record -> convertToJewel(record))
                .collect(Collectors.toList());
    }


    private Jewel convertToJewel(CSVRecord record) {

        String sn = record.get(0);
        Pattern pattern = Pattern.compile("\\d{2}-\\d{2}-.{2}\\d{2}-\\d{5}");
        Matcher matcher = pattern.matcher(record.get(1));
        String sku;
        if (matcher.find()) sku = matcher.group();
        else {
//            sku = record.get(1);
//            System.out.println("no validate " + record.get(1));
            return null;
        }

        double gross = Double.parseDouble(record.get(2).replace(',', '.'));
        double net = Double.parseDouble(record.get(3).replace(',', '.'));
        LocalDate date;
        try {
            date = LocalDate.parse(record.get(4).substring(0, 10), DateTimeFormatter.ofPattern("dd.MM.yyyy  0:00:00"));
        } catch (Exception e) {
//            System.out.println("ERROR " + record.get(4) + " (" + record.get(0) + ")");
            date = null;
        }
        String stones = record.get(5);
        return new Jewel(sn, sku, gross, net, date, getListGemsoneInJewel(stones));

    }


    public List<Gemstone> getListGemsoneInJewel(String line) {
        return Arrays.stream(line.trim().split(","))
                .map(s -> createStone(s))
                .collect(Collectors.toList());
    }

    private Gemstone createStone(String line) {
        List<String> listDesription = new ArrayList<>(Arrays.asList(line.trim().split(" ")));
       if (listDesription.size()==4) return null;
        try {

            if (kindStone.contains(listDesription.get(2).toLowerCase())) {
                listDesription.set(1, listDesription.get(1) + " " + listDesription.get(2));
                listDesription.remove(2);
            }

            if (!listDesription.get(4).contains("/")) {
                listDesription.add(4, "");
            }
            if (listDesription.get(5).contains("????.???")) {
                listDesription.remove(5);
            }

            int count = getInt(listDesription.get(0));
            TypeStone type = TypeStone.getTypeStone(listDesription.get(1).toLowerCase().trim());
            ShapeCut cut = ShapeCut.getShapeCut(listDesription.get(2).toLowerCase().trim());
            String size = listDesription.get(3);
            String quality = listDesription.get(4);
            Double weightCt = getDouble(listDesription.get(5));
            return new Gemstone(count, type, cut, size, quality, weightCt);
        } catch (Exception e) {
            System.out.println("GEM ERROR " + listDesription +line);
          //  e.printStackTrace();
            return null;
        }
    }

    private int getInt(String s) {
        StringBuilder sb = new StringBuilder();
        for (Character ch : s.toCharArray()) {
            if (Character.isDigit(ch)) sb.append(ch);
        }
        return Integer.parseInt(sb.toString());
    }

    private double getDouble(String s) {
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

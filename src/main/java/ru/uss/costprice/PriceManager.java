package ru.uss.costprice;

import ru.uss.costprice.model.Jewel;
import ru.uss.costprice.parsing.Parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Created by vadelic on 16.05.2016.
 */
public class PriceManager {
    private static final String PATH = "src/main/resources/list_series.csv";
    // private static final String PATH = "src/main/resources/ts.csv";

    private static PriceManager ourInstance = null;
    private Map<String, List<Jewel>> dBase = new HashMap<>();

    static {
        loadDb();
    }

    private static void loadDb() {
        try {
            System.out.println("loading BD...");
            PriceManager.addData(Parser.getSkuFromCsv(Files.newInputStream(Paths.get(PATH).toAbsolutePath())));
            System.out.println("load completed");
        } catch (IOException e) {
            System.out.println("Error loading BD");
        }
    }

    public static PriceManager getInstance() {
        if (ourInstance == null) {
            ourInstance = new PriceManager();
        }
        return ourInstance;
    }

    private static void addData(Map<String, List<Jewel>> dBase) {
        if (ourInstance == null) {
            ourInstance = new PriceManager();
        }
        ourInstance.dBase.putAll(dBase);
    }

    private PriceManager() {
    }

    public void printSerials(String sku) {
        List<Jewel> jewelList = dBase.get(sku);
        Collections.sort(jewelList, backComparatorReleaseDate());

        System.out.format("%10s", "Date");
        System.out.format("%7s", "Gross");
        System.out.format("%7s", "Net ");
        System.out.format("%3s", "Cnt");
        System.out.format("%8s", "Kr17");
        System.out.format("%8s", "Kr57");
        System.out.format("%8s", "Precious");
        System.out.println();

        for (Jewel jewel : jewelList) {
            System.out.format("%10s", jewel.getReleaseDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            System.out.format("%6.2fg", jewel.getGrossWeight());
            System.out.format("%6.2fg", jewel.getNetWeight());
            System.out.format("%3d", jewel.getCountStones());

            System.out.format("%6.3fct", jewel.getWeightKr17());
            System.out.format("%6.3fct", jewel.getWeightKr57());
            System.out.format("%6.3fct", jewel.getWeightPrecious());
            System.out.println();
        }

    }

    private Jewel getLastRealiseItems(List<Jewel> eachSku) throws NullPointerException {
        return eachSku.stream().min(backComparatorReleaseDate()).get();
    }

    public void printListSku(Set<String> sku) {


        List<Jewel> jewelList = new ArrayList<>();

        if (!sku.isEmpty()) {
            for (String sOfSet : sku) {
                try {
                    jewelList.add(getLastRealiseItems(dBase.get(sOfSet)));
                } catch (NullPointerException e) {
                    System.out.println(sku + " Item is not found");
                }
            }
        } else {
            for (List<Jewel> eachSku : dBase.values()) {
                jewelList.add(getLastRealiseItems(eachSku));
            }
        }
        if (jewelList.isEmpty()) {
            System.out.println("No data to view");
            return;
        }
        System.out.format("%17s", "SKU");
        System.out.format("%7s", "Gross");
        System.out.format("%7s", "Net ");
        System.out.format("%3s", "Cnt");
        System.out.format("%8s", "Kr17");
        System.out.format("%8s", "Kr57");
        System.out.format("%8s", "Precious");
        System.out.println();

        for (Jewel jewel : jewelList) {
            if (jewel == null) continue;
            System.out.format("%17s", jewel.getSku());
            System.out.format("%6.2fg", jewel.getGrossWeight());
            System.out.format("%6.2fg", jewel.getNetWeight());
            System.out.format("%3d", jewel.getCountStones());

            System.out.format("%6.3fct", jewel.getWeightKr17());
            System.out.format("%6.3fct", jewel.getWeightKr57());
            System.out.format("%6.3fct", jewel.getWeightPrecious());
            System.out.println();
        }

    }

    private Comparator<Jewel> backComparatorReleaseDate() {
        return new Comparator<Jewel>() {
            @Override
            public int compare(Jewel o1, Jewel o2) {
                return o2.getReleaseDate().compareTo(o1.getReleaseDate());
            }
        };
    }
}

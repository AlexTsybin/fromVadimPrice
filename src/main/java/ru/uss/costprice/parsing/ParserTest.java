package ru.uss.costprice.parsing;

import org.junit.Test;
import ru.uss.costprice.model.Jewel;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by vadelic on 01.05.2016.
 */
public class ParserTest {
    @Test
    public void getSkuFromCsv() throws Exception {
        Parser parser = new Parser();
        Path path = Paths.get("list_series.csv");
        List<Jewel> jewelList = parser.getSkuFromCsv(Files.newInputStream(path));
        System.out.println(jewelList.size());
       // jewelList.forEach(s -> System.out.println(s));
    }

    @org.junit.Test
    public void createStone() throws Exception {
        Parser parser = new Parser();
        Path path = Paths.get("res", "list_series.csv");
      //  BufferedReader r = new BufferedReader(new InputStreamReader(Files.newInputStream(path), "UTF-8"));
     //   Set<TypeStone> typeSet = new HashSet<>();
     //   Set<ShapeCut> cutSet = new HashSet<>();


        //  typeSet.add(stone.getType());
        //  cutSet.add(stone.getCut());


//        System.out.println(typeSet + "\n");
//        System.out.println(cutSet + "\n");

    }

}
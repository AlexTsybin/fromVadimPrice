package ru.uss.costprice;

import com.sun.xml.internal.bind.v2.TODO;
import ru.uss.costprice.model.Jewel;
import ru.uss.costprice.parsing.Parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vadelic on 11.05.2016.
 */
public class StartMenu {

//    10-02-1200-10333
//    TODO http://stackoverflow.com/questions/5915425/programmatically-reading-static-resources-from-my-java-webapp
    public static void main(String[] args) {
        CmdHelper.Operation operation;
        do {
            operation = CmdHelper.getOperation();
            CmdHelper.exec(operation);

        } while (CmdHelper.Operation.EXIT != operation);
    }
}

import com.google.common.collect.Maps;
import pl.kalishak.trainclassif.*;
import pl.kalishak.trainclassif.serialization.TomlSerializer;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static final Map<CarrierIdentifier, String> ID_TO_RESOURCE = Maps.newHashMap();

    public static final String RESOURCES = "./src/main/resources/";
    public static final String TRAINS = RESOURCES + "trains/";

    public static void main(String[] args) throws NullPointerException, IOException {
        for (CarrierIdentifier t : Identifiers.AVAILABLE) {
            String symbol = t.symbol();
            ID_TO_RESOURCE.put(t, TRAINS + symbol.toLowerCase(Locale.ROOT));
            print(ID_TO_RESOURCE.get(t));
        }

        final Scanner sc = new Scanner(System.in);
        print("Enter line's id name=");
        final String s = sc.nextLine();
        TrainInfo ti = TomlSerializer.serialize(new File(getTrainResource(Identifiers.KM, s)));
        print(ti);
        print("With route:");
        printAll(ti.getRoute().getStationRawList());
    }

    public static <T> void print(T cos) {
        System.out.println(cos);
    }

    public static <T> void printAll(T... values) {
        for (T t : values) {
            System.out.print(t + ", ");
        }

        System.out.println();
    }

    public static String getTrainResource(CarrierIdentifier id, String line ) {
        return ID_TO_RESOURCE.get(id) + "/" + line + ".toml";
    }
}

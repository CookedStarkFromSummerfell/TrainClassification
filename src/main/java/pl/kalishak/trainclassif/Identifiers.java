package pl.kalishak.trainclassif;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Identifiers {
    public static final List<CarrierIdentifier> AVAILABLE = new ArrayList<>();
    private static int id = 0;

    public static final CarrierIdentifier KM = setAvailable("Koleje Mazowieckie", Color.GREEN);
    public static final CarrierIdentifier IC = setAvailable("Inter City", Color.BLUE);
    public static final CarrierIdentifier PR = setAvailable("Pol Regio", Color.RED);
    public static final CarrierIdentifier SKMW = setAvailable("Szybka Kolej Miejska", Color.RED);
    public static final CarrierIdentifier SKMS = setAvailable("Szybka Kolej Miejska", Color.RED);
    public static final CarrierIdentifier WKD = setAvailable("Warszawka Kolej Dojazdowa", Color.BLUE);

    private static CarrierIdentifier setAvailable(String name, Color color) {
        CarrierIdentifier identifier = new CarrierIdentifier(name, id++, color);
        AVAILABLE.add(identifier);
        return identifier;
    }

    private static void setAvailable(CarrierIdentifier carrierIdentifier) {
        boolean flag = false;

        for (CarrierIdentifier cid : AVAILABLE) {
            if (carrierIdentifier.localizedName().equalsIgnoreCase(cid.localizedName())) {
                flag = !flag;
                break;
            }
        }
    }
}

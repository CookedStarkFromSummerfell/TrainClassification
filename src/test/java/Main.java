import pl.kalishak.trainclassif.Identifiers;
import pl.kalishak.trainclassif.CarrierIdentifier;

public class Main {

    public static void main(String[] args) {
        for (CarrierIdentifier t : Identifiers.AVAILABLE) {
            print(t.symbol());
        }
    }

    public static <T> void print(T cos) {
        System.out.println(cos);
    }

    public static <T> void printAll(T... values) {
        for (T t : values) {
            System.out.print(t);
        }

        System.out.println();
    }
}

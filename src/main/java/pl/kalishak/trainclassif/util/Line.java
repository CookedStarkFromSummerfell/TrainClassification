package pl.kalishak.trainclassif.util;

public record Line(char id, short number) {
    @Override
    public String toString() {
        return String.valueOf(id) + number;
    }
}

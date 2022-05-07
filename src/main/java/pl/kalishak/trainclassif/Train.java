package pl.kalishak.trainclassif;

import java.util.Optional;

public class Train {

    /**
     * Train's unique name
     */
    protected final Optional<String> name;
    /**
     * Train's identification number
     */
    protected final long number;
    /**
     * Train characteristics
     */
    protected final TrainTrait[] traits;

    public Train(Optional<String> name, long number, TrainTrait[] traits) {
        this.name = name;
        this.number = number;
        this.traits = traits;
    }

    public String getName() {
        return name.orElse("");
    }

    public long getNumber() {
        return this.number;
    }
}

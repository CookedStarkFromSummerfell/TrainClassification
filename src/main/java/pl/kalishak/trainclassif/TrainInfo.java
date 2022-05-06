package pl.kalishak.trainclassif;

import pl.kalishak.trainclassif.station.Route;
import pl.kalishak.trainclassif.util.Line;

import java.util.NoSuchElementException;
import java.util.Optional;

public class TrainInfo {

    /**
     * Carrier's name
     */
    private final CarrierIdentifier carrierIdentifier;
    /**
     * Carrier's short name
     */
    protected final String symbol;
    /**
     * Line Identifier
     */
    protected final Optional<Line> line;
    /**
     * Train's unique name
     */
    protected final Optional<String> name;
    /**
     * Train's identification number
     */
    protected final long number;
    /**
     * Station list, where the train stops
     */
    protected Route route;

    /**
     * Train characteristics
     */
    protected final TrainTrait[] traits;

    private TrainInfo(CarrierIdentifier carrierIdentifier, Optional<Line> line, Optional<String> name, long number, Route route, TrainTrait[] traits) {
        this.carrierIdentifier = carrierIdentifier;
        this.symbol = carrierIdentifier.symbol();
        this.line = line;
        this.name = name;
        this.number = number;
        this.route = route;
        this.traits = traits;
    }

    protected final CarrierIdentifier getCarrierIdentifier() {
        return carrierIdentifier;
    }

    public String getSimpleCarrierName() {
        return getCarrierIdentifier().localizedName();
    }

    public Line getLine() throws NoSuchElementException {
        return line.orElseThrow();
    }

    public String getName() {
        return name.orElse("");
    }

    public long getNumber() {
        return this.number;
    }

    public static class Builder {
        private final CarrierIdentifier identifier;
        private final long number;
        private Optional<Line> line = Optional.empty();
        private Optional<String> name = Optional.empty();
        private Route route = Route.EMPTY;
        private TrainTrait[] traits = { CasingTraits.CLASSIC };

        public Builder(CarrierIdentifier identifier, long number) {
            this.identifier = identifier;
            this.number = number;
        }

        public Builder line(Line line) {
            assert line != null;
            this.line = Optional.of(line);
            return this;
        }

        public Builder name(String name) {
            assert !name.isEmpty();
            this.name = Optional.of(name);
            return this;
        }

        public Builder stations(Route route) {
            this.route = route;
            return this;
        }

        public Builder traits(TrainTrait... traits) {
            this.traits = traits;
            return this;
        }

        public TrainInfo build() {
            return new TrainInfo(identifier, line, name, number, route, traits);
        }
    }
}

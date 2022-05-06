package pl.kalishak.trainclassif;

import pl.kalishak.trainclassif.util.Line;

import java.util.ArrayList;
import java.util.List;
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
    //TODO Special class to inform about different line or line changes
    /**
     * Station list, where the train stops
     */
    protected List<String> stations;

    /**
     * Train characteristics
     */
    protected final TrainModelType[] traits;

    private TrainInfo(CarrierIdentifier carrierIdentifier, Optional<Line> line, Optional<String> name, long number, List<String> stations, TrainModelType[] traits) {
        this.carrierIdentifier = carrierIdentifier;
        this.symbol = carrierIdentifier.symbol();
        this.line = line;
        this.name = name;
        this.number = number;
        this.stations = stations;
        this.traits = traits;
    }

    public void setStationList(List<String> stations) {
        this.stations = stations;
    }

    public final String getEndStation(boolean reversible) {

        return reversible ? getStartStation(!reversible) : this.stations.get(stations.size() -1);
    }

    public final String getStartStation(boolean reversible) {
        return reversible ? getEndStation(!reversible) : this.stations.get(0);
    }

    public static class Builder {
        private final CarrierIdentifier identifier;
        private final long number;
        private Optional<Line> line = Optional.empty();
        private Optional<String> name = Optional.empty();
        private List<String> stations = new ArrayList<>();
        private TrainModelType[] traits = { CasingTraits.CLASSIC };

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

        public Builder stations(List<String> stations) {
            this.stations = stations;
            return this;
        }

        public Builder traits(TrainModelType... traits) {
            this.traits = traits;
            return this;
        }

        public TrainInfo build() {
            return new TrainInfo(identifier, line, name, number, stations, traits);
        }
    }
}

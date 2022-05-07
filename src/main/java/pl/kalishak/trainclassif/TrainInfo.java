package pl.kalishak.trainclassif;

import pl.kalishak.trainclassif.station.Route;

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
    protected final String line;

    /**
     * Station list, where the train stops
     */
    protected Route route;

    private TrainInfo(CarrierIdentifier carrierIdentifier, String line, Route route) {
        this.carrierIdentifier = carrierIdentifier;
        this.symbol = carrierIdentifier.symbol();
        this.line = line;
        this.route = route;
    }

    protected final CarrierIdentifier getCarrierIdentifier() {
        return carrierIdentifier;
    }

    public String getSimpleCarrierName() {
        return getCarrierIdentifier().localizedName();
    }

    public String getLine() {
        return line;
    }

    public Route getRoute() {
        return route;
    }

    @Override
    public String toString() {
        return "TrainInfo{" +
                "carrierIdentifier=" + carrierIdentifier +
                ", symbol='" + symbol + '\'' +
                ", line='" + line + '\'' +
                '}';
    }

    public static class Builder {
        private final CarrierIdentifier identifier;
        private String line = "";
        private Route route = Route.EMPTY;

        public Builder(CarrierIdentifier identifier) {
            this.identifier = identifier;
        }

        public Builder line(String line) {
            assert line != null;
            this.line = line;
            return this;
        }

        public Builder stations(Route route) {
            this.route = route;
            return this;
        }


        public TrainInfo build() {
            return new TrainInfo(identifier, line, route);
        }
    }
}

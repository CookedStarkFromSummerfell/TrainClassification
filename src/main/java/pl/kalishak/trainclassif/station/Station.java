package pl.kalishak.trainclassif.station;

public record Station(String name, Route route) implements RouteElement {

    @Override
    @SuppressWarnings("unchecked")
    public int getPosition() {
        return route.stations.indexOf(this);
    }

    @Override
    public void setPosition(int position) {
        route.stations.add(position, this);
    }

    @Override
    public boolean optional() {
        return route instanceof Route.SubRoute;
    }
}

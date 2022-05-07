package pl.kalishak.trainclassif.station;

public interface RouteElement {

    int getPosition();
    void setPosition(int position);

    boolean optional();

    default void reverse(Route route) {
        final int rIds = route.stations.size();
        //TODO change id
    }

    String toString();
}

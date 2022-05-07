package pl.kalishak.trainclassif.station;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Route {

    public static final Route EMPTY = new Route();

    protected final List<Station> stations = new ArrayList<>();
    protected final List<RouteElement> routes;

    private final boolean mainRoute;
    private final boolean changeable;

    protected int lastUsedId;

    Route(boolean mainRoute, boolean changeable) {
        this.mainRoute = mainRoute;
        this.changeable = changeable;
        this.lastUsedId = 0;
        this.routes = new ArrayList<>();
    }

    public Route() {
        this(true, true);
    }

    public void add(RouteElement element) {
        try {
            add(lastUsedId, element);
            ++lastUsedId;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings({ "unused", "deprecated" })
    public void add(Consumer<RouteElement> element) {
        try {
            add(element.andThen(e -> {
                int pos = e.getPosition();

            }));
        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    private void add(int id, RouteElement element) throws Exception {
        if(stations.contains(element)) {
            throw new Exception("Cannot add the same object!" + element.toString());
        } else {
            this.routes.add(id, element);
        }
    }

    public static Route createFromLists(SubRoute subRoute, List<String> mainRoute) {
        final Route route = new Route();

        mainRoute.forEach(station -> route.add(new Station(station, route)));
        route.add(subRoute);

        return route;
    }

    @SuppressWarnings("unchecked")
    public final Station getEndStation(boolean reversible, boolean useMainRoute) {
        RouteElement element = this.routes.get(routes.size() -1);

        if (reversible) {
            return getStartStation(false, useMainRoute);
        }

        if (useMainRoute) {
            return element instanceof Station ? (Station) element : this.findNearestStation(element.getPosition());
        }

        if (element instanceof SubRoute) {
            SubRoute subRoute = (SubRoute) element;
            return (Station)subRoute.routes.get(subRoute.routes.size() -1);
        }

        return null;
    }

    public final Station getStartStation(boolean reversible, boolean useMainRoute) {
        RouteElement element = this.routes.get(0);

        if (reversible) {
            return getEndStation(false, useMainRoute);
        }

        if (useMainRoute) {
            return element instanceof Station ? (Station) element : this.findFurtherstStation(element.getPosition());
        }

        if (element instanceof SubRoute) {
            SubRoute subRoute = (SubRoute) element;
            return (Station)subRoute.routes.get(0);
        }

        return null;
    }

    public final boolean isMainRoute() {
        return mainRoute;
    }

    public final boolean isChangeable() {
        return changeable;
    }

    @SuppressWarnings("unchecked")
    private Station findNearestStation(int initPos) {
        Station station = null;

        for (int i = initPos; i < 0; --i) {
            if (routes.get(i) instanceof Station) {
                station = (Station) routes.get(i);
                break;
            }
        }

        return station;
    }

    @SuppressWarnings("unchecked")
    private Station findFurtherstStation(int initPos) {
        Station station = null;

        for (int i = initPos; i < routes.size() -1; ++i) {
            if (routes.get(i) instanceof Station) {
                station = (Station) routes.get(i);
                break;
            }
        }

        return station;
    }

    @SuppressWarnings("unchecked")
    public void reverse() {
        for (RouteElement element : routes) {
            if (element instanceof SubRoute) {
                ((SubRoute)element).routes.forEach(station -> station.reverse(this));
            } else if (element instanceof Station) {
                ((Station)element).reverse(this);
            }
        }
    }

    public List<String> getStationRawList() {
        final List<String> result = new ArrayList<>();
        final String prefix = isMainRoute() ? "-" : "*";
        this.routes.forEach(element -> {

            if(element instanceof SubRoute) {
                result.addAll(((SubRoute)element).getStationRawList());
            } else {
                result.add(prefix + element.toString());
            }
        });
        return result;
    }

    public static class SubRoute extends Route implements RouteElement {

        public SubRoute() {
            super(false, false);
        }

        public static SubRoute createFromList(List<String> stations) {
            SubRoute subRoute = new SubRoute();
            stations.forEach(station -> subRoute.add(new Station(station, subRoute)));
            return subRoute;
        }

        @Override
        public void add(RouteElement element) {
            if (element instanceof SubRoute) {
                throw new IllegalArgumentException("Cannot set another SubRoute in another SubRoute!");
            }

            super.add(element);
        }

        @Override
        public int getPosition() {
            return this.lastUsedId;
        }

        @Override
        public void setPosition(int position) {
            routes.add(position, this);
        }

        @Override
        public boolean optional() {
            return !isMainRoute();
        }

        @Override
        public String toString() {
            return "SubRoute[%s]".formatted(this.routes.size());
        }
    }
}

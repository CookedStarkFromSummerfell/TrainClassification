package pl.kalishak.trainclassif.station;

import java.util.ArrayList;
import java.util.List;

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
        if (!stations.contains(element)) {
            routes.add(lastUsedId, element);
            ++lastUsedId;
        }
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

    public static class SubRoute extends Route implements RouteElement {

        public SubRoute() {
            super(false, false);
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
    }
}

package pl.kalishak.trainclassif.serialization;

import com.moandjiezana.toml.Toml;
import pl.kalishak.trainclassif.Identifiers;
import pl.kalishak.trainclassif.TrainInfo;
import pl.kalishak.trainclassif.station.Route;

import java.io.File;

public class TomlSerializer {

    public static TrainInfo serialize(File file) {
        Toml toml = new Toml().read(file);
        var routes = toml.getTable("stations");
        var subrs = routes.containsTable("subroutes") ? routes.getTable("subroutes") : null;

        return new TrainInfo
                .Builder(Identifiers.fromSymbol(toml.getString("carrier")))
                .line(file.getName().substring(0, file.getName().lastIndexOf('.')))
                .stations(Route.createFromLists(Route.SubRoute.createFromList(subrs.getList("first")), routes.getList("mainroute")))
                .build();
    }
}

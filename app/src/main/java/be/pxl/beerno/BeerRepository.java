package be.pxl.beerno;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class BeerRepository {

    public static List<Beer> getAllBeers() {
        List<Beer> beers = new ArrayList<>();


        Beer stella = new Beer("Stella Artois", Paths.get("Beerno\\app\\src\\main\\res\\drawable-xxxhdpi\\stella.png"));
        Beer jupiler = new Beer("Jupiler", Paths.get("Beerno\\app\\src\\main\\res\\drawable-xxxhdpi\\jupiler.png"));
        Beer guiness = new Beer("Guiness", Paths.get("Beerno\\app\\src\\main\\res\\drawable-xxxhdpi\\guiness.jpg"));
        Beer krusuvice = new Beer("Krušovice", Paths.get("Beerno\\app\\src\\main\\res\\drawable-xxxhdpi\\krusovice.png"));
        Beer heineken = new Beer("Heineken", Paths.get("Beerno\\app\\src\\main\\res\\drawable-xxxhdpi\\heineken.png"));

        beers.add(jupiler);
        beers.add(stella);
        beers.add(guiness);
        beers.add(krusuvice);
        beers.add(heineken);

        return beers;
    }
}

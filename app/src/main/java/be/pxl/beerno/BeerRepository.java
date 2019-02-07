package be.pxl.beerno;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class BeerRepository {

    public static List<Beer> getAllBeers() {
        List<Beer> beers = new ArrayList<>();


        Beer stella = new Beer("Stella Artois", Paths.get("Beerno\\app\\src\\main\\res\\drawable-xxxhdpi\\stella.png");
        Beer jupiler = new Beer("Jupiler", Paths.get("Beerno\\app\\src\\main\\res\\drawable-xxxhdpi\\jupiler.png");
        Beer guiness = new Beer("Guiness", Paths.get("Beerno\\app\\src\\main\\res\\drawable-xxxhdpi\\guiness.jpg"));

        beers.add(jupiler);
        beers.add(stella);

        return beers;
    }
}

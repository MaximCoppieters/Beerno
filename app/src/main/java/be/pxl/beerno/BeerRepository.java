package be.pxl.beerno;

import android.net.Uri;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeerRepository {

    public static List<Beer> getAllBeers() {
        List<Beer> beers = new ArrayList<>();

        Beer stella = new Beer("Stella Artois", Uri.parse("Beerno\\app\\src\\main\\res\\drawable-xxxhdpi\\stella.png"));
        Beer jupiler = new Beer("Jupiler", Uri.parse("Beerno\\app\\src\\main\\res\\drawable-xxxhdpi\\jupiler.png"));
        Beer guiness = new Beer("Guiness", Uri.parse("Beerno\\app\\src\\main\\res\\drawable-xxxhdpi\\guiness.jpg"));
        Beer krusuvice = new Beer("Krušovice", Uri.parse("Beerno\\app\\src\\main\\res\\drawable-xxxhdpi\\krusovice.png"));
        Beer heineken = new Beer("Heineken", Uri.parse("Beerno\\app\\src\\main\\res\\drawable-xxxhdpi\\heineken.png"));

        beers.add(jupiler);
        beers.add(stella);
        beers.add(guiness);
        beers.add(krusuvice);
        beers.add(heineken);

        return beers;
    }
}

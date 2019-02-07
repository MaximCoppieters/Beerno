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

        Beer stella = new Beer("Stella Artois", R.drawable.stella);
        Beer jupiler = new Beer("Jupiler", R.drawable.jupiler);
        Beer guiness = new Beer("Guiness", R.drawable.guiness);
        Beer krusuvice = new Beer("Krušovice", R.drawable.krusovice);
        Beer heineken = new Beer("Heineken", R.drawable.heineken);

        beers.add(jupiler);
        beers.add(stella);
        beers.add(guiness);
        beers.add(krusuvice);
        beers.add(heineken);

        return beers;
    }
}

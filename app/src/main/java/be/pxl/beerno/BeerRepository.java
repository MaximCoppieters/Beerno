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

        Beer stella = new Beer("Stella Artois", getUriFromImageName("stella.png"));
        Beer jupiler = new Beer("Jupiler", getUriFromImageName("jupuler.png"));
        Beer guiness = new Beer("Guiness", getUriFromImageName("guiness.jpg"));
        Beer krusuvice = new Beer("Krušovice", getUriFromImageName("krusovice.png"));
        Beer heineken = new Beer("Heineken", getUriFromImageName("heineken.png"));

        beers.add(jupiler);
        beers.add(stella);
        beers.add(guiness);
        beers.add(krusuvice);
        beers.add(heineken);

        return beers;
    }

    private static Uri getUriFromImageName(String imageName) {
        return Uri.parse("android.resource://be.pxl/" + imageName);
    }
}

package be.pxl.beerno;

import java.util.ArrayList;
import java.util.List;

public class BeerRepository {

    public static List<Beer> getAllBeers() {
        List<Beer> beers = new ArrayList<>();


        Beer stella = new Beer();
        Beer jupiler = new Beer();

        beers.add(jupiler);
        beers.add(stella);

        return beers;
    }
}

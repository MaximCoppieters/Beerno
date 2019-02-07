package be.pxl.beerno;

import java.util.ArrayList;

public class BeerRepository {

    public static List<Beer> getAllBeers() {
        List<Beer> beers = new ArrayList<>();

        beers.add(new Beer());
        beers.add(new Beer());

        return beers;
    }
}

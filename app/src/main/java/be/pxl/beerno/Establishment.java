package be.pxl.beerno;

import com.mapbox.mapboxsdk.geometry.LatLng;

import java.util.List;

public class Establishment {

    private String name;
    private LatLng location;
    private List<Beer> beerList;

    //todo implement beer class and delete this inner class
    private class Beer{}

    public Establishment() { }

    public Establishment(String name, LatLng location, List<Beer> beerList) {
        this.name = name;
        this.location = location;
        this.beerList = beerList;
    }

    public String getName() {
        return name;
    }

    public LatLng getLocation() {
        return location;
    }

    public List<Beer> getBeers() {
        return beerList;
    }

    @Override
    public String toString() {
        return "Establishment{" +
                "name='" + name + '\'' +
                ", location=" + location +
                ", Beers=" + beerList +
                '}';
    }
}

package be.pxl.beerno;

import com.mapbox.mapboxsdk.geometry.LatLng;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Establishment implements Serializable {

    private String name;
    private LatLng location;
    private List<Beer> beerMenu;
    private int id;
    public Establishment(String name, LatLng location) {
        this(name, location, new ArrayList<Beer>());
    }

    public Establishment(String name, LatLng location, List<Beer> beerList) {
        this.name = name;
        this.location = location;
        this.beerMenu = beerList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public LatLng getLocation() {
        return location;
    }

    public List<Beer> getBeers() {
        return beerMenu;
    }

    public void addBeerToMenu(Beer beer) {
        beerMenu.add(beer);
    }

    @Override
    public String toString() {
        return "Establishment{" +
                "name='" + name + '\'' +
                ", location=" + location +
                ", Beers=" + beerMenu +
                '}';
    }
}

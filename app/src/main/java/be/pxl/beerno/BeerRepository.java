package be.pxl.beerno;

import android.content.res.Resources;

import com.mapbox.mapboxsdk.geometry.LatLng;

import java.util.ArrayList;
import java.util.List;

public class BeerRepository {
    private List<Beer> beers;
    private List<Establishment> establishments;

    public BeerRepository() {
        initializeRepository();
    }

    private void initializeRepository() {
        this.beers = new ArrayList<>();
        this.establishments = new ArrayList<>();

        findNearbyEstablishments();
        findAllBeers();
    }

    public List<Establishment> getNearbyEstablishments() {
        return establishments;
    }

    // irish pub coords 49.196244, 16.608121
    //little big bar coords 49.196346, 16.608459
    //SHOT bar coords 49.196450, 16.609556
    //Aloha coords 49.196368, 16.609605
    //bar,ktery neexistuje coords 49.195918, 16.609724
    // Cubana coords 49.197033, 16.609721

    private void findNearbyEstablishments() {
        Establishment shotBar = new Establishment("Irish Pub", new LatLng(49.196244, 16.608121));
        Establishment aloha = new Establishment("Aloha", new LatLng(49.196346,16.608459));
        Establishment barktery = new Establishment("Barktery", new LatLng(49.196450,16.609556));
        Establishment cubana = new Establishment("Cubana", new LatLng(49.197033, 16.609721));

        establishments.add(shotBar);
        establishments.add(aloha);
        establishments.add(barktery);
        establishments.add(cubana);

        Beer stella = new Beer("Stella Artois", R.drawable.stella);
        Beer jupiler = new Beer("Jupiler", R.drawable.jupiler);
        Beer guiness = new Beer("Guiness", R.drawable.guiness);
        Beer krusuvice = new Beer("Krušovice", R.drawable.krusovice);
        Beer heineken = new Beer("Heineken", R.drawable.heineken);

        stella.associateBeerWithEstablishment(shotBar);
        stella.associateBeerWithEstablishment(aloha);
        jupiler.associateBeerWithEstablishment(barktery);
        jupiler.associateBeerWithEstablishment(cubana);
        guiness.associateBeerWithEstablishment(barktery);
        guiness.associateBeerWithEstablishment(cubana);
        krusuvice.associateBeerWithEstablishment(shotBar);
        krusuvice.associateBeerWithEstablishment(aloha);
        heineken.associateBeerWithEstablishment(aloha);
        heineken.associateBeerWithEstablishment(shotBar);

        beers.add(stella);
        beers.add(guiness);
        beers.add(krusuvice);
        beers.add(heineken);
        beers.add(jupiler);
    }

    public Establishment getEstablishmentAtLatLng(final LatLng establishmentLatLng) {
        // TODO Have to get;
        return establishments.get(0);

/*        for (Establishment establishment : establishments) {
            if (establishment.getLocation().equals(establishmentLatLng)) {
                return establishment;
            }
        }
        throw new Resources.NotFoundException("Establishment at location was not found");*/
    }

    private void findAllBeers() {
    }

    public List<Beer> getAllBeers() {
        return beers;
    }
}

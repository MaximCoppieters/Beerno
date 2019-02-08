package be.pxl.beerno;


import android.content.res.Resources;

import com.mapbox.mapboxsdk.geometry.LatLng;

import java.util.ArrayList;
import java.util.List;

public class BeerRepository {
    private List<Beer> beers;
    private List<Establishment> establishments;
    private Establishment establishmentVisiting;

    private static final BeerRepository instance = new BeerRepository();

    //private constructor to avoid client applications to use constructor
    private BeerRepository() {
        initializeRepository();
    }

    public static BeerRepository getInstance() {
        return instance;
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
        Establishment shotBar = new Establishment("Shot Bar", new LatLng(49.209752, 16.614986));
        Establishment aloha = new Establishment("Aloha", new LatLng(49.210006,16.614782));
        Establishment barktery = new Establishment("Barktery", new LatLng(49.209790,16.614993));
        Establishment irishPub = new Establishment("Irish Pub", new LatLng(49.209700,16.614903));

        establishments.add(shotBar);
        establishments.add(aloha);
        establishments.add(barktery);
        establishments.add(irishPub);

        Beer stella = new Beer("Stella Artois", R.drawable.stella);
        Beer jupiler = new Beer("Jupiler", R.drawable.jupiler);
        Beer guinness = new Beer("Guinness", R.drawable.guinness);
        Beer krusuvice = new Beer("Krušovice", R.drawable.krusovice);
        Beer heineken = new Beer("Heineken", R.drawable.heineken);

        stella.setDescription("a Belgian pilsner of between 4.8 and 5.2% ABV");
        jupiler.setDescription("a Belgian beer introduced in 1966, biggest-selling beer in Belgium");
        guinness.setDescription("an Irish dry stout that originated in the brewery of Arthur Guinness");
        krusuvice.setDescription("established in 1581 by Jiří Birka in the village of Krušovice");
        heineken.setDescription("Piswater");

        stella.associateBeerWithEstablishment(shotBar);
        stella.associateBeerWithEstablishment(aloha);
        jupiler.associateBeerWithEstablishment(barktery);
        jupiler.associateBeerWithEstablishment(irishPub);
        guinness.associateBeerWithEstablishment(barktery);
        guinness.associateBeerWithEstablishment(irishPub);
        krusuvice.associateBeerWithEstablishment(shotBar);
        krusuvice.associateBeerWithEstablishment(aloha);
        heineken.associateBeerWithEstablishment(aloha);
        heineken.associateBeerWithEstablishment(shotBar);

        beers.add(stella);
        beers.add(guinness);
        beers.add(krusuvice);
        beers.add(heineken);
        beers.add(jupiler);
    }

    public Establishment getEstablishmentAtLatLng(LatLng establishmentLatLng) {
        for (Establishment establishment : establishments) {
            if (establishment.getLocation().distanceTo(establishmentLatLng) < 50.0) {
                return establishment;
            }
        }
        throw new Resources.NotFoundException(String.format("Location at long: %.6f, lat: %.6f not found"
                ,establishmentLatLng.getLatitude(), establishmentLatLng.getLongitude()));
    }

    private void findAllBeers() {
    }

    public void setEstablishmentVisitingAtLocation(LatLng location) {
        establishmentVisiting = getEstablishmentAtLatLng(location);
    }

    public Establishment getEstablishmentVisiting() {
        return establishmentVisiting;
    }

    public List<Beer> getAllBeers() {
        return beers;
    }
}

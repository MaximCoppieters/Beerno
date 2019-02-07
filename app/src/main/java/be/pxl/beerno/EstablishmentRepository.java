package be.pxl.beerno;

import android.location.Location;

import com.mapbox.android.core.location.LocationEnginePriority;
import com.mapbox.mapboxsdk.geometry.LatLng;

import java.util.ArrayList;
import java.util.List;

public class EstablishmentRepository extends Establishment {


    // TODO: integrate google places
    public static List<Establishment> getNearbyEstablishments() {
        List<Establishment> nearbyEstablishments = new ArrayList<>();

        // irish pub coords 49.196244, 16.608121
        //little big bar coords 49.196346, 16.608459
        //SHOT bar coords 49.196450, 16.609556
        //Aloha coords 49.196368, 16.609605
        //bar,ktery neexistuje coords 49.195918, 16.609724
        // Cubana coords 49.197033, 16.609721

        Establishment irishPub = new Establishment();
        Establishment littleBigBar=new Establishment();
        Establishment shotBar=new Establishment();
        Establishment aloha=new Establishment();
        Establishment barKtery=new Establishment();
        Establishment cubana=new Establishment();


        nearbyEstablishments.add(irishPub);
        nearbyEstablishments.add(littleBigBar);
        nearbyEstablishments.add(shotBar);
        nearbyEstablishments.add(aloha);
        nearbyEstablishments.add(barKtery);
        nearbyEstablishments.add(cubana);




        return nearbyEstablishments;
    }
}
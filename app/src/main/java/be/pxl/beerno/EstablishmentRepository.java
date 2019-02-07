package be.pxl.beerno;

import android.location.Location;

import com.mapbox.android.core.location.LocationEnginePriority;
import com.mapbox.mapboxsdk.geometry.LatLng;

import java.util.ArrayList;
import java.util.List;

public class EstablishmentRepository {


    // TODO: integrate google places
    public static List<Establishment> getNearbyEstablishments() {
        List<Establishment> nearbyEstablishments = new ArrayList<>();

        // irish pub coords 49.196244, 16.608121
        //little big bar coords 49.196346, 16.608459
        //SHOT bar coords 49.196450, 16.609556
        //Aloha coords 49.196368, 16.609605
        //bar,ktery neexistuje coords 49.195918, 16.609724
        // Cubana coords 49.197033, 16.609721


        //sky ice bar coords 49.195329, 16.609960
        // charlie's hat coords 49.195186, 16.610868
        //metro music bar coords 49.194776, 16.610096
        //soundcafé and bar  vyhlidka coords 49.194299, 16.608261
        // bar 1920 london coords 49.193415, 16.607056
        //mayday cocktail bar coords 49.193410, 16.606681
        //air cafe coords 49.192083, 16.608455
        //spirit bar coords 49.192520, 16.611206
        //charlie's square coords 49.192749, 16.611321
        //highfive,it's your bar coords 49.190914, 16.610183
        //mamut pub coords 49.190601, 16.608991
        //super panda circus coords 49.192660, 16.605158
        //runaway bar coords 49.197565, 16.613420
        //kentaur bar and barber shop coords 49.197696, 16.601874


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
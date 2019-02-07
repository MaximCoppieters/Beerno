package be.pxl.beerno;

import java.util.ArrayList;

public class EstablishmentRepository {


    // TODO: integrate google places
    public static void getNearbyEstablishments() {
        List<Establishment> nearbyEstablishments = new ArrayList<>();

        Establishment irishPub = new Establishment();
        Establishment czechPub = new Establishment();

        nearbyEstablishments.add(irishPub);
        nearbyEstablishments.add(czechPub);

        return nearbyEstablishments;
    }
}
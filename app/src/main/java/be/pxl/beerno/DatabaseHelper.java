package be.pxl.beerno;

import com.mapbox.mapboxsdk.geometry.LatLng;

import java.util.List;

import cz.mendelu.busItWeek.library.StoryLineDatabaseHelper;
import cz.mendelu.busItWeek.library.builder.StoryLineBuilder;

public class DatabaseHelper extends StoryLineDatabaseHelper {
    public DatabaseHelper() {
        super(32);
    }

    @Override
    protected void onCreate(StoryLineBuilder builder) {
        BeerRepository beerRepository = BeerRepository.getInstance();

        List<Establishment> nearbyEstablishments = beerRepository.getNearbyEstablishments();



//todo terug uncommenten
//        for (Establishment nearbyEstablishment : nearbyEstablishments) {
//            LatLng establishmentLocation = nearbyEstablishment.getLocation();
//
//            builder.addGPSTask(nearbyEstablishment.getName())
//                    .location(establishmentLocation.getLatitude(), establishmentLocation.getLongitude())
//                    .radius(100)
//                    .victoryPoints(10)
//                    .taskDone();
//        }

        builder.addGPSTask("1")
                .location(49.215322, 16.630016)
                .radius(100)
                .victoryPoints(10)
                .taskDone();

//        builder.addGPSTask("1")
//                .location(49.209742, 16.614986)
//                .radius(100)
//                .victoryPoints(10)
//                .taskDone();
//
//        builder.addGPSTask("2")
//                .location(49.210006, 16.614782)
//                .radius(100)
//                .victoryPoints(10)
//                .taskDone();
//
//        builder.addGPSTask("3")
//                .location(49.209782, 16.614963)
//                .radius(100)
//                .victoryPoints(10)
//                .taskDone();

    }
}

package be.pxl.beerno;

import com.mapbox.mapboxsdk.geometry.LatLng;

import java.util.List;

import cz.mendelu.busItWeek.library.StoryLineDatabaseHelper;
import cz.mendelu.busItWeek.library.builder.StoryLineBuilder;

public class DatabaseHelper extends StoryLineDatabaseHelper {
    public DatabaseHelper() {
        super(33);
    }

    @Override
    protected void onCreate(StoryLineBuilder builder) {
        BeerRepository beerRepository = BeerRepository.getInstance();

        List<Establishment> nearbyEstablishments = beerRepository.getNearbyEstablishments();

        for (Establishment nearbyEstablishment : nearbyEstablishments) {
            LatLng establishmentLocation = nearbyEstablishment.getLocation();

            builder.addGPSTask(nearbyEstablishment.getName())
                    .location(establishmentLocation.getLatitude(), establishmentLocation.getLongitude())
                    .radius(100)
                    .victoryPoints(10)
                    .taskDone();
        }
    }
}

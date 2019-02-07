package be.pxl.beerno;

import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;

import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.location.LocationEngineListener;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

import java.util.List;

import cz.mendelu.busItWeek.library.StoryLine;
import cz.mendelu.busItWeek.library.Task;

public class BeerStopActivity extends AppCompatActivity implements OnMapReadyCallback, PermissionsListener, LocationEngineListener {

    private MapView mapView;
    private MapboxMap mapBoxMap;
    private PermissionsManager permissionsManager;
    private LocationEngine locationEngine;
    private LocationComponent locationComponent;

    private StoryLine storyLine;
    private Task currentTask;

    private Marker currentMarker;

    private CardView beaconScanningCard;

    @Override
    public void onConnected() {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {

    }

    @Override
    public void onPermissionResult(boolean granted) {

    }

    @Override
    public void onMapReady(MapboxMap mapboxMap) {

    }
}

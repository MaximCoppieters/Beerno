package be.pxl.beerno;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.location.LocationEngineListener;
import com.mapbox.android.core.location.LocationEnginePriority;
import com.mapbox.android.core.location.LocationEngineProvider;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

import java.util.List;

import cz.mendelu.busItWeek.library.BeaconTask;
import cz.mendelu.busItWeek.library.CodeTask;
import cz.mendelu.busItWeek.library.GPSTask;
import cz.mendelu.busItWeek.library.StoryLine;
import cz.mendelu.busItWeek.library.Task;
import cz.mendelu.busItWeek.library.beacons.BeaconDefinition;
import cz.mendelu.busItWeek.library.beacons.BeaconUtil;
import cz.mendelu.busItWeek.library.map.MapUtil;
import cz.mendelu.busItWeek.library.qrcode.QRCodeUtil;

public class BeerRouteActivity extends AppCompatActivity implements PermissionsListener, LocationEngineListener, OnMapReadyCallback {

    private MapView mapView;
    private MapboxMap mapboxMap;
    private PermissionsManager permissionsManager;
    private LocationEngine locationEngine;
    private LocationComponent locationComponent;

    private StoryLine storyLine;
    private Task currentTask;

    private Marker currentMarker;

    private TextToSpeech textToSpeech;
    private Button mapButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, "pk.eyJ1IjoibG9uZWx5Z3JlZW4iLCJhIjoiY2pyc3k3ZGU5MGZ3cDQzbnBrMTZqeXR2dCJ9.MpJAEgZhKGbII_z8baStag");
        setContentView(R.layout.activity_beer_route);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mapView = findViewById(R.id.map_view);

        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        storyLine = StoryLine.open(this, DatabaseHelper.class);
        currentTask = storyLine.currentTask();


//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

        public void onMapReady(MapboxMap mapboxMap) {
            this.mapboxMap = mapboxMap;

            mapboxMap.getUiSettings().setAllGesturesEnabled(true);
            initializeListeners();
            updateMarkers();

        }

    private void initializeListeners() {
        if (currentTask != null) {
            if (currentTask instanceof GPSTask) {
                initializeLocationComponent();
                initializeLocationEngine();
            }
        }
    }

    private void removeListeners() {
        if (mapboxMap != null && locationEngine != null) {
            locationEngine.removeLocationUpdates();
        }
    }

    private void updateMarkers() {
        if (currentTask != null && mapboxMap != null) {
            if (currentMarker != null) {
                mapboxMap.removeMarker(currentMarker);
            }
            for (Task task: storyLine.taskList()){
                mapboxMap.addMarker(createTaskMarker(this, task));
            }
        }
    }

    private MarkerOptions createTaskMarker(Context context, Task task) {
        int color = R.color.colorPrimary;

        return new MarkerOptions()
                .position(new LatLng(task.getLatitude(), task.getLongitude()))
                .icon(MapUtil.createColoredCircleMarker(context, task.getName(), color));
    }

    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    protected void onResume() {
        super.onResume();
        mapView.onResume();

        currentTask = storyLine.currentTask();
        if (currentTask == null) {
            // no more tasks
            startActivity(new Intent(this, SummaryActivity.class));
            finish();
        } else {
            initializeListeners();
            updateMarkers();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
        removeListeners();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initializeLocationComponent() {
        if (PermissionsManager.areLocationPermissionsGranted(this)) {
            if (mapboxMap != null) {
                locationComponent = mapboxMap.getLocationComponent();
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                locationComponent.activateLocationComponent(this);
                locationComponent.setLocationComponentEnabled(true);
                LatLng currentPositionLatLng = new LatLng();
                currentPositionLatLng.setLongitude(currentTask.getLongitude());
                currentPositionLatLng.setLongitude(currentTask.getLatitude());
                mapboxMap.setCameraPosition(
                        new CameraPosition.Builder()
                        .target(currentPositionLatLng)
                                .build()
                );

                double latitudeCurrentTask = currentTask.getLatitude();
                double longtitudeCurrentTask = currentTask.getLongitude();
                LatLng currentTaskLatlng = new LatLng();
                currentTaskLatlng.setLatitude(latitudeCurrentTask);
                currentTaskLatlng.setLongitude(longtitudeCurrentTask);

                mapboxMap.setCameraPosition(new CameraPosition
                        .Builder()
                        .target(currentTaskLatlng)
                        .build()
                );
            }
        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);
        }
    }

    @SuppressLint("MissingPermission")
    private void initializeLocationEngine() {
        if (mapboxMap != null && PermissionsManager.areLocationPermissionsGranted(this)) {
            locationEngine = new LocationEngineProvider(this).obtainBestLocationEngineAvailable();
            locationEngine.setPriority(LocationEnginePriority.HIGH_ACCURACY);
            locationEngine.setInterval(1000);
            locationEngine.requestLocationUpdates();
            locationEngine.addLocationEngineListener(this);
            locationEngine.activate();
        }
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {

    }

    @Override
    public void onPermissionResult(boolean granted) {

    }

    @Override
    public void onConnected() {

    }

    @Override
    public void onLocationChanged(Location location) {
        if (currentTask != null && currentTask instanceof GPSTask) {
            double radius = ((GPSTask) currentTask).getRadius();

            LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
            LatLng taskLocation = new LatLng(currentTask.getLatitude(), currentTask.getLongitude());

            if (userLocation.distanceTo(taskLocation) < radius) {
                // todo nakijken of dit BeerRouteActivity.this moet zijn of gewoon this
                startActivity(new Intent(BeerRouteActivity.this, EstablishmentActivity.class));
                LatLng establishmentLatLng = new LatLng(location.getLatitude(), location.getLatitude());
                BeerRepository beerRepository = new BeerRepository();

                Establishment nearbyEstablishment = beerRepository.getEstablishmentAtLatLng(establishmentLatLng);

                getIntent().putExtra("establishment", nearbyEstablishment);
                startActivity(new Intent(this, EstablishmentActivity.class));

            }
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}

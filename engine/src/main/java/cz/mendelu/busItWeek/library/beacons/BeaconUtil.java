package cz.mendelu.busItWeek.library.beacons;

import android.app.Activity;
import android.util.Log;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.estimote.sdk.SystemRequirementsChecker;

import java.util.ArrayList;
import java.util.List;

import cz.mendelu.busItWeek.library.qrcode.QRCodeUtil;

/**
 * The utility for managing the beacons.
 */
public class BeaconUtil {

    // the main beacon manager from estimotes
    private BeaconManager beaconManager;
    // the region to be ranged
    private Region region;

    // the context of the activity
    private Activity activity;

    // the list of beacons
    private List<BeaconDefinition> listOfBeacons = new ArrayList<>();

    private static final String TAG = "BeaconUtil";

    private boolean ranging = false;

    /**
     * Initialization of the beacon utility
     * @param activity the context of the calling activity
     */
    public BeaconUtil(Activity activity) {

        this.activity = activity;
        beaconManager = new BeaconManager(activity);
        region = new Region("Ranged Region", null, null, null);
    }


    /**
     * Starts the ranging for the beacons.
     */
    public void startRanging(){

        // checks default permissions
        SystemRequirementsChecker.checkWithDefaultDialogs(activity);
        // connects to the service
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                Log.i(TAG, "Beacon service connected and ready" );
                beaconManager.startRanging(region);
            }
        });

        // starts ranging for the beacons
        beaconManager.setRangingListener(new BeaconManager.RangingListener() {
            @Override
            public void onBeaconsDiscovered(Region region, List<Beacon> list) {
                // beacon discovered

                if (!list.isEmpty()) {
                    for (Beacon beacon: list) {
                        for (BeaconDefinition beaconDef: listOfBeacons) {
                            if (beacon.getMajor() == beaconDef.getMajorNumber() && beacon.getMinor()
                                    == beaconDef.getMinorNumber() && !beaconDef.codeExecuted){
                                beaconDef.execute();
                                beaconDef.codeExecuted = true;
                            }
                        }
                    }
                }
            }
        });
        ranging = true;
    }

    public void clearBeacons(){
        listOfBeacons.clear();
    }

    /**
     * Stops the ranging for the beacons.
     */
    public void stopRanging(){
        if (beaconManager != null) {
            beaconManager.stopRanging(region);
        }
        ranging = false;
    }

    /**
     * Adds new beacon to the list of beacons.
     * @param beacon the beacon to be ranged
     * @throws IllegalArgumentException throws if beacon is null
     */
    public void addBeacon(BeaconDefinition beacon) throws IllegalArgumentException{
        if (beacon == null){
            throw new IllegalArgumentException("New beacon cannot be null.");
        }

        listOfBeacons.add(beacon);
    }

    public boolean isRanging() {
        return ranging;
    }


}
package cz.mendelu.busItWeek.library;

import android.content.ContentValues;
import android.util.Log;

import static java.lang.String.format;

/**
 * Created by Honza on 07.03.2017.
 */

public class AssignmentPuzzle extends Puzzle {

    private static final String TAG = AssignmentPuzzle.class.getSimpleName();

    static final int TYPE_VALUE = 1;

    final static String ASSIGNMENT = "assignment"; 

    final static String QR_CODE = "qrCode";

    final static String EAN = "ean"; 

    final static String BEACON_MAJOR_CODE = "beaconMajorCode";

    final static String BEACON_MINOR_CODE = "beaconMinorCode";

    public AssignmentPuzzle(ContentValues contentValues) {
        super(contentValues);
        Log.d(TAG, format("Create: %s", contentValues));
    }

    public String getAssignment() {
        return contentValues.getAsString(ASSIGNMENT);
    }

    public String getQrCode() {
        return contentValues.getAsString(QR_CODE);
    }

    public Integer getEan() {
        return contentValues.getAsInteger(EAN);
    }

    public Integer getBeaconMajorCode() {
        return contentValues.getAsInteger(BEACON_MAJOR_CODE);
    }

    public Integer getBeaconMinorCode() {
        return contentValues.getAsInteger(BEACON_MINOR_CODE);
    }
}

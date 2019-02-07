package cz.mendelu.busItWeek.library;

import android.content.ContentValues;
import android.util.Log;

import static java.lang.String.format;

/**
 * Created by Honza on 01.03.2017.
 */

public class BeaconTask extends Task {

    private static final String TAG = BeaconTask.class.getSimpleName();

    static final int TYPE_VALUE = 1;
    
    static final String MAJOR = "major";
    static final String MINOR = "minor";

    public BeaconTask(TaskDatabase taskDatabase, ContentValues contentValues) {
        super(taskDatabase, contentValues);
        Log.d(TAG, format("Create: %s", contentValues));
    }

    public Integer getMajor() {
        return contentValues.getAsInteger(MAJOR);
    }

    public int getMinor() {
        return contentValues.getAsInteger(MINOR);
    }
}

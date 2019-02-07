package cz.mendelu.busItWeek.library;

import android.content.ContentValues;
import android.util.Log;

import static java.lang.String.format;

/**
 * Created by Honza on 01.03.2017.
 */

public class GPSTask extends Task {

    private static final String TAG = GPSTask.class.getSimpleName();

    static final int TYPE_VALUE = 3;

    static final String RADIUS = "radius";

    public GPSTask(TaskDatabase taskDatabase, ContentValues contentValues) {
        super(taskDatabase, contentValues);
        Log.d(TAG, format("Create: %s", contentValues));
    }

    public Double getRadius() {
        return contentValues.getAsDouble(RADIUS);
    }
}

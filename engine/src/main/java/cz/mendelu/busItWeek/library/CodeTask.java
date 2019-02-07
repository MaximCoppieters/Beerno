package cz.mendelu.busItWeek.library;

import android.content.ContentValues;
import android.util.Log;

import static java.lang.String.format;

/**
 * Created by Honza on 01.03.2017.
 */

public class CodeTask extends Task {

    private static final String TAG = CodeTask.class.getSimpleName();

    static final int TYPE_VALUE = 2;

    static final String EAN = "ean";
    static final String QR = "qr";

    public CodeTask(TaskDatabase taskDatabase, ContentValues contentValues) {
        super(taskDatabase, contentValues);
        Log.d(TAG, format("Create: %s", contentValues));
    }


    public String getQR() {
        return contentValues.getAsString(QR);
    }

    public Long getEAN() {
        return contentValues.getAsLong(EAN);
    }
}

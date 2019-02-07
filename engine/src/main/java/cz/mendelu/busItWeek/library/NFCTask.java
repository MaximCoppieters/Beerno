package cz.mendelu.busItWeek.library;

import android.content.ContentValues;
import android.util.Log;

import static java.lang.String.format;

/**
 * Created by Honza on 01.03.2017.
 */

public class NFCTask extends Task {

    private static final String TAG = NFCTask.class.getSimpleName();

    static final int TYPE_VALUE = 4;

    static final String NFC = "nfc";
    static final String ALTERNATIVE_TASK_ID = "alternative_task_id";

    public NFCTask(TaskDatabase taskDatabase, ContentValues contentValues) {
        super(taskDatabase, contentValues);
        Log.d(TAG, format("Create: %s", contentValues));
    }

    public byte[] getNFC() {
        return contentValues.getAsByteArray(NFC);
    }

    private Task alternativeTask;

    public Task getAlternativeTask() {
        if (contentValues.get(ALTERNATIVE_TASK_ID) == null) {
            return null;
        }

        if (alternativeTask == null) {
            long id = contentValues.getAsLong(ALTERNATIVE_TASK_ID);
            Log.i(TAG, format("Load alternative task with id %d for task (id: %d).", id, getId()));
            ReadableTaskDatabase rdb = taskDatabase.openReadableTaskDatabase();
            try {
                alternativeTask = rdb.findTaskById(id);
            } catch (Exception e) {
                throw new StoryLineRuntimeException(format("Load alternative task with id %d for task (id: %d) failed", id, getId()), e);
            } finally {
                rdb.close();
            }
        }

        return alternativeTask;
    }
}

package cz.mendelu.busItWeek.library;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import static java.lang.String.format;

/**
 * Created by Honza on 15.03.2017.
 */

class WritableTaskDatabase extends ReadableTaskDatabase {

    private static final String TAG = WritableTaskDatabase.class.getSimpleName();

    public WritableTaskDatabase(TaskDatabase taskDatabase, SQLiteDatabase sqLiteDatabase) {
        super(taskDatabase, sqLiteDatabase);
        db.beginTransaction();
    }

    public void saveTask(long id, ContentValues values) {
        Log.i(TAG, format("Save task id: %d, values: %s", id, values));
        try {
            db.update(Task.TABLE,
                    values,
                    Task.ID + " = " + id,
                    null);
        } catch (Exception e) {
            throw new StoryLineRuntimeException(format("Update task failed, id: %d, values: %s", id, values), e);
        }
    }

    public void commit() {
        Log.i(TAG, "Commit transaction");
        try {
            db.setTransactionSuccessful();
            db.endTransaction();
        } catch (Exception e) {
            throw new StoryLineRuntimeException("Commit transaction failed.", e);
        }
    }

    public void rollBack() {
        Log.i(TAG, "Rollback transaction");
        try {
            db.endTransaction();
        } catch (Exception e) {
            throw new StoryLineRuntimeException("Rollback transaction failed.", e);
        }
    }
}

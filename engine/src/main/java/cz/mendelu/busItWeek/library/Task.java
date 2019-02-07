package cz.mendelu.busItWeek.library;

import android.content.ContentValues;
import android.provider.BaseColumns;
import android.util.Log;

import static java.lang.String.format;

/**
 * Created by Honza on 01.03.2017.
 */
public abstract class Task {

    private static final String TAG = Task.class.getSimpleName();

    static final String TABLE = "Task";

    static final String ID = BaseColumns._ID;
    static final String TYPE = "type";
    static final String NAME = "name";
    static final String VICTORY_POINTS = "victoryPoints";
    static final String STATUS_ENUM = "taskStatus";
    static final String PUZZLE_ID = "puzzle";
    static final String HINT = "hint";
    static final String LATITUDE = "latitude";
    static final String LONGITUDE = "longitude";

    static Task load(TaskDatabase taskDatabase, ContentValues values) {
        Integer taskType = values.getAsInteger(TYPE);
        Log.d(TAG, format("Load Task type: %s", taskType));
        switch (taskType) {
            case BeaconTask.TYPE_VALUE:
                return new BeaconTask(taskDatabase, values);
            case CodeTask.TYPE_VALUE:
                return new CodeTask(taskDatabase, values);
            case GPSTask.TYPE_VALUE:
                return new GPSTask(taskDatabase, values);
            case NFCTask.TYPE_VALUE:
                return new NFCTask(taskDatabase, values);
        }
        throw new StoryLineRuntimeException("Unknown task type " + taskType);
    }

    protected final ContentValues contentValues;
    protected final TaskDatabase taskDatabase;

    protected Task(TaskDatabase taskDatabase, ContentValues contentValues) {
        this.taskDatabase = taskDatabase;
        this.contentValues = contentValues;
    }

    public void finish(boolean success) {
        Log.d(TAG, format("Task id %d: finish(%s)", getId(), Boolean.toString(success)));
        String status = (success ? TaskStatus.SUCCESS : TaskStatus.FAILURE).name();
        moveToNext(status);
    }

    public void skip() {
        Log.d(TAG, format("Task id %d: skip", getId()));
        moveToNext(TaskStatus.SKIPPED.name());
    }

    private void moveToNext(String status) {
        Log.i(TAG, format("Task id %d change status to %s", getId(), status));
        WritableTaskDatabase wdb = taskDatabase.beginTaskDatabaseTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(Task.STATUS_ENUM, status);

            wdb.saveTask(getId(), values);

            contentValues.putAll(values);

            Task next = wdb.findNextTask(getId());
            if (next != null) {
                Log.i(TAG, format("Next task id id %d", next.getId()));
                values.put(Task.STATUS_ENUM, TaskStatus.CURRENT.name());
                wdb.saveTask(next.getId(), values);
            } else {
                Log.i(TAG, "StoryLine finish, next task don't exists.");
            }

            wdb.commit();
        } catch (Exception e) {
            wdb.rollBack();
            throw new StoryLineRuntimeException(format("Move to next task failed, current task id: %d.", getId()), e);
        } finally {
            wdb.close();
        }
    }

    public long getId() {
        return contentValues.getAsLong(Task.ID);
    }

    public String getName() {
        return contentValues.getAsString(NAME);
    }

    public Integer getVictoryPoints() {
        return contentValues.getAsInteger(VICTORY_POINTS);
    }

    public TaskStatus getTaskStatus() {
        return (contentValues.get(STATUS_ENUM) == null)
                ? null
                : TaskStatus.valueOf(contentValues.getAsString(STATUS_ENUM));
    }

    private Puzzle puzzle;

    public Puzzle getPuzzle() {
        if (contentValues.get(PUZZLE_ID) == null) {
            return null;
        }

        if (puzzle == null) {
            Log.i(TAG, format("Load puzzle for task (id: %d).", getId()));
            long id = contentValues.getAsLong(PUZZLE_ID);
            ReadableTaskDatabase rdt = taskDatabase.openReadableTaskDatabase();
            try {
                puzzle = rdt.findPuzzleById(id);
            } catch (Exception e) {
                throw new StoryLineRuntimeException(format("Load puzzle for task (id: %d) failed", getId()), e);
            } finally {
                rdt.close();
            }
        }

        return puzzle;
    }

    public String getHint() {
        return contentValues.getAsString(HINT);
    }

    public Double getLatitude() {
        return contentValues.getAsDouble(LATITUDE);
    }

    public Double getLongitude() {
        return contentValues.getAsDouble(LONGITUDE);
    }

    @Override
    public String toString() {
        return format("Task id: %d, values: %s", getId(), contentValues);
    }
}

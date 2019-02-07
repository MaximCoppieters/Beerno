package cz.mendelu.busItWeek.library;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;

/**
 * Created by Honza on 15.03.2017.
 */

class ReadableTaskDatabase {

    private static final String TAG = ReadableTaskDatabase.class.getSimpleName();

    private static final String ASC = " ASC";

    protected final SQLiteDatabase db;
    protected final TaskDatabase taskDatabase;

    public ReadableTaskDatabase(TaskDatabase taskDatabase, SQLiteDatabase sqLiteDatabase) {
        this.taskDatabase = taskDatabase;
        this.db = sqLiteDatabase;
    }

    public Collection<Task> findAllNonAlternativeTasks() {
        Log.v(TAG, "Find all nonAlternative tasks.");
        List<Task> result = new LinkedList<>();
        Cursor cursor = null;
        try {
            cursor = db.query(Task.TABLE, // table
                    null, // columns
                    Task.STATUS_ENUM + " not null", // selection
                    null, // selectionArgs
                    null, // groupBy
                    null, // having
                    null, // orderBy
                    null);// limit
            while (cursor.moveToNext()) {
                result.add(Task.load(taskDatabase, readValues(cursor)));
            }
            Log.d(TAG, String.format("Find all nonAlternative tasks: s", result));
            return result;
        } catch (Exception e) {
            throw new StoryLineRuntimeException("Find all nonAlternative tasks failed.", e);
        } finally {
            saveClose(cursor);
        }
    }

    public Collection<Task> findTaskByStatus(TaskStatus status) {
        Log.v(TAG, format("Find task by status %s.", status));
        List<Task> result = new LinkedList<>();
        Cursor cursor = null;
        try {
            cursor = db.query(Task.TABLE, // table
                    null, // columns
                    Task.STATUS_ENUM + " = ?", // selection
                    new String[] {status.name()}, // selectionArgs
                    null, // groupBy
                    null, // having
                    null, // orderBy
                    null);// limit
            while (cursor.moveToNext()) {
                result.add(Task.load(taskDatabase, readValues(cursor)));
            }
            Log.d(TAG, format("Find task by status %s: %s", status, result));
            return result;
        } catch (Exception e) {
            throw new StoryLineRuntimeException(format("Find task by status %s failed.", status), e);
        } finally {
            saveClose(cursor);
        }
    }

    public Task findNextTask(long currentTaskId) {
        Log.v(TAG, format("Find next task, current task id %d.", currentTaskId));
        Cursor cursor = null;
        try {
            cursor = db.query(Task.TABLE, // table
                    null, // columns
                    Task.ID + " > " + currentTaskId + " AND " + Task.STATUS_ENUM + " = ?", // selection
                    new String[] {TaskStatus.WAITING.name()}, // selectionArgs
                    null, // groupBy
                    null, // having
                    Task.ID + ASC, // orderBy
                    null);// limit
            if (!cursor.moveToFirst()) {
                return null;
            }

            Task result = Task.load(taskDatabase, readValues(cursor));
            Log.d(TAG, format("Find next task, current task id %d: %s", currentTaskId, result));
            return result;
        } catch (Exception e) {
            throw new StoryLineRuntimeException(format("Find next task, current task id %d failed.", currentTaskId), e);
        } finally {
            saveClose(cursor);
        }
    }

    public Task findTaskById(long id) {
        Log.v(TAG, format("Find task by id %d.", id));
        Cursor cursor = null;
        try {
            cursor = db.query(Task.TABLE, // table
                    null, // columns
                    Task.ID + " = " + id, // selection
                    null, // selectionArgs
                    null, // groupBy
                    null, // having
                    null, // orderBy
                    null);// limit
            if (!cursor.moveToFirst()) {
                return null;
            }

            Task result = Task.load(taskDatabase, readValues(cursor));
            Log.d(TAG, format("Find task by id %d: %s", id, result));
            return result;
        } catch (Exception e) {
            throw new StoryLineRuntimeException(format("Find task by id %d failed.", id), e);
        } finally {
            saveClose(cursor);
        }
    }

    public Puzzle findPuzzleById(long id) {
        Log.v(TAG, format("Find puzzle by id %d.", id));
        Cursor cursor = null;
        try {
            cursor = db.query(Puzzle.TABLE, // table
                    null, // columns
                    Puzzle.ID + " = " + id, // selection
                    null, // selectionArgs
                    null, // groupBy
                    null, // having
                    null, // orderBy
                    null);// limit
            if (!cursor.moveToFirst()) {
                return null;
            }
            Puzzle result = Puzzle.load(this, readValues(cursor));
            Log.d(TAG, format("Find puzzle by id %d: %s", id, result));
            return result;
        } catch (Exception e) {
            throw new StoryLineRuntimeException(format("Find puzzle by id %d failed.", id), e);
        } finally {
            saveClose(cursor);
        }
    }

    public Map<String, Boolean> findChoicesForPuzzle(long id) {
        Log.v(TAG, format("Find choices for Puzzle with id %d.", id));
        Map<String, Boolean> result = new LinkedHashMap<>();
        Cursor cursor = null;
        String answer;
        Boolean correct;
        try {
            cursor = db.query(ChoicePuzzle.CHOICES_TABLE, // table
                    null, // columns
                    ChoicePuzzle.CHOICES_PUZZLE_ID + " = " + id, // selection
                    null, // selectionArgs
                    null, // groupBy
                    null, // having
                    ChoicePuzzle.ID + ASC, // orderBy
                    null);// limit

            int answerIndex = cursor.getColumnIndex(ChoicePuzzle.CHOICES_ANSWER);
            int correctIndex = cursor.getColumnIndex(ChoicePuzzle.CHOICES_CORRECT);

            while (cursor.moveToNext()) {
                answer = cursor.getString(answerIndex);
                correct = (cursor.isNull(correctIndex))
                        ? null
                        : cursor.getInt(correctIndex) == 1;
                result.put(answer, correct);
            }

            Log.d(TAG, format("Find choices for Puzzle with id %d: %s", id, result));
            return result;
        } catch (Exception e) {
            throw new StoryLineRuntimeException(format("Find choices for Puzzle with id %d failed", id), e);
        } finally {
            saveClose(cursor);
        }
    }

    public Map<Integer, Boolean> findImagesForPuzzle(long id) {
        Log.v(TAG, format("Find images for Puzzle with id %d.", id));
        Map<Integer, Boolean> result = new LinkedHashMap<>();
        Cursor cursor = null;
        Integer resource;
        Boolean correct;
        try {
            cursor = db.query(ImageSelectPuzzle.IMAGES_TABLE, // table
                    null, // columns
                    ImageSelectPuzzle.IMAGES_PUZZLE_ID + " = " + id, // selection
                    null, // selectionArgs
                    null, // groupBy
                    null, // having
                    ImageSelectPuzzle.ID + ASC, // orderBy
                    null);// limit

            int resourceIndex = cursor.getColumnIndex(ImageSelectPuzzle.IMAGES_RESOURCE);
            int correctIndex = cursor.getColumnIndex(ImageSelectPuzzle.IMAGES_CORRECT);

            while (cursor.moveToNext()) {
                resource = cursor.getInt(resourceIndex);
                correct = (cursor.isNull(correctIndex))
                        ? null
                        : cursor.getInt(correctIndex) == 1;
                result.put(resource, correct);
            }

            Log.d(TAG, format("Find images for Puzzle with id %d: %s", id, result));
            return result;
        } catch (Exception e) {
            throw new StoryLineRuntimeException(format("Find images for Puzzle with id %d failed.", id), e);
        } finally {
            saveClose(cursor);
        }
    }

    public void close() {
        Log.i(TAG, "Close database connection.");
        if (db != null) {
            try {
                db.close();
            } catch (Exception e) {
                Log.e(TAG, "Close database failed.", e);
            }
        }
    }

    protected static void saveClose(Cursor cursor) {
        if (cursor != null) {
            try {
                cursor.close();
            } catch (Exception e) {
                Log.e(TAG, "Close cursor failed.", e);
            }
        }
    }

    protected static ContentValues readValues(Cursor cursor) {
        ContentValues contentValues = new ContentValues();
        android.database.DatabaseUtils.cursorRowToContentValues(cursor, contentValues);
        return contentValues;
    }

}

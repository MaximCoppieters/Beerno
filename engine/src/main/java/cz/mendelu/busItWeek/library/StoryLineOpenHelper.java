package cz.mendelu.busItWeek.library;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static java.lang.String.format;

/**
 * Created by Honza on 02.03.2017.
 */

class StoryLineOpenHelper extends SQLiteOpenHelper implements TaskDatabase {

    private static final String TAG = StoryLineOpenHelper.class.getSimpleName();

    private final StoryLineDatabaseHelper databaseHelper;

    public StoryLineOpenHelper(Context context, String dbName, StoryLineDatabaseHelper databaseHelper) {
        super(context, dbName, null, databaseHelper.getVersion());
        this.databaseHelper = databaseHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d(TAG, "Execute onCreate() method.");
        this.createTables(sqLiteDatabase);
        SQLiteStoryLineBuilder builder = new SQLiteStoryLineBuilder(sqLiteDatabase);
        databaseHelper.onCreate(builder);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int fromVersion, int toVersion) {
        Log.d(TAG, format("Execute onUpgrade(fromVersion: %d, toVersion: %d) method.", fromVersion, toVersion));
        dropAllTables(sqLiteDatabase);
        onCreate(sqLiteDatabase);
    }

    private void dropAllTables(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + Task.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + Puzzle.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + ChoicePuzzle.CHOICES_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + ImageSelectPuzzle.IMAGES_TABLE);
    }

    @Override
    public ReadableTaskDatabase openReadableTaskDatabase() {
        Log.d(TAG, "Open readable TaskDatabase");
        return new ReadableTaskDatabase(this, getReadableDatabase());
    }

    @Override
    public WritableTaskDatabase beginTaskDatabaseTransaction() {
        Log.i(TAG, "Begin TaskDatabase transaction");
        return new WritableTaskDatabase(this, getWritableDatabase());
    }

    private void createTables(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Task.TABLE + " (" +
                Task.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Task.TYPE + " INTEGER NOT NULL, " +
                Task.NAME + " TEXT UNIQUE, " +
                Task.STATUS_ENUM + " TEXT, " +
                Task.VICTORY_POINTS + " INTEGER, " +
                Task.HINT + " TEXT, " +
                Task.LATITUDE + " REAL, " +
                Task.LONGITUDE + " REAL, " +
                Task.PUZZLE_ID + " INTEGER, " +
                CodeTask.EAN + " INTEGER, " +
                CodeTask.QR + " TEXT, " +
                BeaconTask.MAJOR + " INTEGER, " +
                BeaconTask.MINOR + " INTEGER, " +
                GPSTask.RADIUS + " REAL, " +
                NFCTask.NFC + " BLOB, " +
                NFCTask.ALTERNATIVE_TASK_ID + " INTEGER" +
                ")");
        db.execSQL("CREATE TABLE " + Puzzle.TABLE + " (" +
                Puzzle.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Puzzle.TYPE + " INTEGER NOT NULL, " +
                Puzzle.HINT + " TEXT, " +
                Puzzle.PUZZLE_TIME + " INTEGER, " +
                AssignmentPuzzle.ASSIGNMENT + " TEXT, " +
                AssignmentPuzzle.BEACON_MAJOR_CODE + " INTEGER, " +
                AssignmentPuzzle.BEACON_MINOR_CODE + " INTEGER, " +
                AssignmentPuzzle.EAN + " INTEGER, " +
                AssignmentPuzzle.QR_CODE + " TEXT, " +
                ChoicePuzzle.QUESTION + " TEXT, " +
                //ImageSelectPuzzle.QUESTION + " TEXT, " + // Duplication definitio
                //SimplePuzzle.QUESTION + " TEXT, " + // Duplication definition
                SimplePuzzle.ANSWER + " TEXT" +
                ")");
        db.execSQL("CREATE TABLE " + ChoicePuzzle.CHOICES_TABLE + " (" +
                ChoicePuzzle.CHOICES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ChoicePuzzle.CHOICES_PUZZLE_ID + " INTEGER, " +
                ChoicePuzzle.CHOICES_ANSWER + " TEXT, " +
                ChoicePuzzle.CHOICES_CORRECT + " BOOLEAN" +
                ")");
        db.execSQL("CREATE TABLE " + ImageSelectPuzzle.IMAGES_TABLE + " (" +
                ImageSelectPuzzle.IMAGES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ImageSelectPuzzle.IMAGES_PUZZLE_ID + " INTEGER, " +
                ImageSelectPuzzle.IMAGES_RESOURCE + " INTEGER, " +
                ImageSelectPuzzle.IMAGES_CORRECT + " BOOLEAN" +
                ")");
    }
}

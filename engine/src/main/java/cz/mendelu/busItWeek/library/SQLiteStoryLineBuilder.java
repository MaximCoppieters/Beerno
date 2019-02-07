package cz.mendelu.busItWeek.library;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

import cz.mendelu.busItWeek.library.builder.AlternativeTaskBuilder;
import cz.mendelu.busItWeek.library.builder.AssignmentPuzzleBuilder;
import cz.mendelu.busItWeek.library.builder.BeaconTaskBuilder;
import cz.mendelu.busItWeek.library.builder.ChoicePuzzleBuilder;
import cz.mendelu.busItWeek.library.builder.CodeTaskBuilder;
import cz.mendelu.busItWeek.library.builder.GPSTaskBuilder;
import cz.mendelu.busItWeek.library.builder.ImageSelectPuzzleBuilder;
import cz.mendelu.busItWeek.library.builder.NFCTaskBuilder;
import cz.mendelu.busItWeek.library.builder.PuzzleBuilder;
import cz.mendelu.busItWeek.library.builder.SimplePuzzleBuilder;
import cz.mendelu.busItWeek.library.builder.StoryLineBuilder;
import cz.mendelu.busItWeek.library.builder.TaskBuilder;

import static java.lang.String.format;

/**
 * Created by Honza on 03.03.2017.
 */

class SQLiteStoryLineBuilder implements StoryLineBuilder {

    private static final String TAG = SQLiteStoryLineBuilder.class.getSimpleName();

    private final SQLiteDatabase sqLiteDatabase;

    private boolean isFirstTask = true;

    public SQLiteStoryLineBuilder(SQLiteDatabase sqLiteDatabase) {
        this.sqLiteDatabase = sqLiteDatabase;
    }

    @Override
    public BeaconTaskBuilder addBeaconTask(String name) {
        return new SQLiteBeaconTaskBuilder(new ContentValues(), name);
    }

    @Override
    public CodeTaskBuilder addCodeTask(String name) {
        return new SQLiteCodeTaskBuilder(new ContentValues(), name);
    }

    @Override
    public GPSTaskBuilder addGPSTask(String name) {
        return new SQLiteGPSTaskBuilder(new ContentValues(), name);
    }

    @Override
    public NFCTaskBuilder addNFCTask(String name) {
        return new SQLiteNFCTaskBuilder(new ContentValues(), name);
    }

    abstract class SQLiteTaskBuilder<T> implements TaskBuilder<T> {

        protected final ContentValues contentValues;
        protected PuzzleBuilder puzzleBuilder;

        SQLiteTaskBuilder(ContentValues contentValues, String name) {
            this.contentValues = contentValues;
            this.contentValues.put(Task.NAME, name);
            this.contentValues.put(Task.VICTORY_POINTS, 0);
            if (isFirstTask) {
                isFirstTask = false;
                this.contentValues.put(Task.STATUS_ENUM, TaskStatus.CURRENT.name());
            } else {
                this.contentValues.put(Task.STATUS_ENUM, TaskStatus.WAITING.name());
            }
        }

        @Override
        public StoryLineBuilder taskDone() {
            long id = sqLiteDatabase.insertOrThrow(Task.TABLE, null, contentValues);
            Log.i(TAG, format("Insert task: id: %d, data %s", id, contentValues));
            return SQLiteStoryLineBuilder.this;
        }

        @Override
        public T victoryPoints(int victoryPoints) {
            this.contentValues.put(Task.VICTORY_POINTS, victoryPoints);
            return (T) this;
        }

        @Override
        public T hint(String hint) {
            this.contentValues.put(Task.HINT, hint);
            return (T) this;
        }

        @Override
        public T location(double latitude, double longitude) {
            this.contentValues.put(GPSTask.LATITUDE, latitude);
            this.contentValues.put(GPSTask.LONGITUDE, longitude);
            return (T)this;
        }

        @Override
        public AssignmentPuzzleBuilder<T> assignmentPuzzle() {
            puzzleBuilder = new SQLiteAssignmentPuzzleBuilder(this, new ContentValues());
            return (AssignmentPuzzleBuilder)puzzleBuilder;
        }

        @Override
        public ChoicePuzzleBuilder<T> choicePuzzle() {
            puzzleBuilder = new SQLiteChoicePuzzleBuilder((T)this, new ContentValues());
            return (ChoicePuzzleBuilder)puzzleBuilder;
        }

        @Override
        public ImageSelectPuzzleBuilder<T> imageSelectPuzzle() {
            puzzleBuilder = new SQLiteImageSelectPuzzleBuilder((T)this, new ContentValues());
            return (ImageSelectPuzzleBuilder)puzzleBuilder;
        }

        @Override
        public SimplePuzzleBuilder<T> simplePuzzle() {
            puzzleBuilder = new SQLiteSimplePuzzleBuilder((T)this, new ContentValues());
            return (SimplePuzzleBuilder)puzzleBuilder;
        }


    }

    abstract class SQLitePuzzleBuilder<P, T> implements PuzzleBuilder<P, T> {

        protected final ContentValues contentValues;
        protected final SQLiteTaskBuilder<T> parentTaskBuilder;
        protected long puzzleId;

        SQLitePuzzleBuilder(T parentTaskBuilder, ContentValues contentValues) {
            this.parentTaskBuilder = (SQLiteTaskBuilder<T>)parentTaskBuilder;
            this.contentValues = contentValues;
        }


        @Override
        public P puzzleTime(long time) {
            contentValues.put(Puzzle.PUZZLE_TIME, time);
            return (P) this;
        }

        @Override
        public P hint(String hint) {
            contentValues.put(Puzzle.HINT, hint);
            return (P) this;
        }

        @Override
        public T puzzleDone() {
            puzzleId = sqLiteDatabase.insertOrThrow(Puzzle.TABLE, null, contentValues);
            Log.i(TAG, format("Insert puzzle: id: %d, data %s", puzzleId, contentValues));
            parentTaskBuilder.contentValues.put(Task.PUZZLE_ID, puzzleId);
            return (T)parentTaskBuilder;
        }

    }

    class SQLiteAssignmentPuzzleBuilder<T> extends SQLitePuzzleBuilder<AssignmentPuzzleBuilder, T> implements AssignmentPuzzleBuilder<T> {

        SQLiteAssignmentPuzzleBuilder(T parentTaskBuilder, ContentValues contentValues) {
            super(parentTaskBuilder, contentValues);
            this.contentValues.put(Task.TYPE, AssignmentPuzzle.TYPE_VALUE);
        }

        @Override
        public AssignmentPuzzleBuilder assignment(String assignment) {
            contentValues.put(AssignmentPuzzle.ASSIGNMENT, assignment);
            return this;
        }

        @Override
        public AssignmentPuzzleBuilder qrCode(String qrCode) {
            contentValues.put(AssignmentPuzzle.QR_CODE, qrCode);
            return this;
        }

        @Override
        public AssignmentPuzzleBuilder ean(int ean) {
            contentValues.put(AssignmentPuzzle.EAN, ean);
            return this;
        }

        @Override
        public AssignmentPuzzleBuilder beacon(int major, int minor) {
            contentValues.put(AssignmentPuzzle.BEACON_MAJOR_CODE, major);
            contentValues.put(AssignmentPuzzle.BEACON_MINOR_CODE, minor);
            return this;
        }
    }

    class SQLiteChoicePuzzleBuilder<T> extends SQLitePuzzleBuilder<ChoicePuzzleBuilder, T> implements ChoicePuzzleBuilder<T> {

        private List<ContentValues> choicesContentValues = new LinkedList<>();

        SQLiteChoicePuzzleBuilder(T parentTaskBuilder, ContentValues contentValues) {
            super(parentTaskBuilder, contentValues);
            this.contentValues.put(Task.TYPE, ChoicePuzzle.TYPE_VALUE);
        }

        @Override
        public ChoicePuzzleBuilder question(String question) {
            contentValues.put(ChoicePuzzle.QUESTION, question);
            return this;
        }

        @Override
        public ChoicePuzzleBuilder addChoice(String answer, boolean correct) {
            ContentValues values = new ContentValues();
            values.put(ChoicePuzzle.CHOICES_ANSWER, answer);
            values.put(ChoicePuzzle.CHOICES_CORRECT, correct);
            choicesContentValues.add(values);
            return this;
        }

        @Override
        public T puzzleDone() {
            T taskBuilder = super.puzzleDone();
            for (ContentValues values: choicesContentValues) {
                values.put(ChoicePuzzle.CHOICES_PUZZLE_ID, puzzleId);
                long id = sqLiteDatabase.insertOrThrow(ChoicePuzzle.CHOICES_TABLE, null, values);
                Log.i(TAG, format("Insert puzzle choice: id: %d, data %s", id, contentValues));
            }
            return taskBuilder;
        }
    }

    class SQLiteImageSelectPuzzleBuilder<T> extends SQLitePuzzleBuilder<ImageSelectPuzzleBuilder, T> implements ImageSelectPuzzleBuilder<T> {

        private List<ContentValues> imagesContentValues = new LinkedList<>();

        SQLiteImageSelectPuzzleBuilder(T parentTaskBuilder, ContentValues contentValues) {
            super(parentTaskBuilder, contentValues);
            this.contentValues.put(Task.TYPE, ImageSelectPuzzle.TYPE_VALUE);
        }

        @Override
        public ImageSelectPuzzleBuilder question(String question) {
            contentValues.put(ImageSelectPuzzle.QUESTION, question);
            return this;
        }

        @Override
        public ImageSelectPuzzleBuilder addImage(int resourceId, boolean correct) {
            ContentValues values = new ContentValues();
            values.put(ImageSelectPuzzle.IMAGES_RESOURCE, resourceId);
            values.put(ImageSelectPuzzle.IMAGES_CORRECT, correct);
            imagesContentValues.add(values);
            return this;
        }

        @Override
        public T puzzleDone() {
            T taskBuilder = super.puzzleDone();
            for (ContentValues values: imagesContentValues) {
                values.put(ImageSelectPuzzle.IMAGES_PUZZLE_ID, puzzleId);
                long id = sqLiteDatabase.insertOrThrow(ImageSelectPuzzle.IMAGES_TABLE, null, values);
                Log.i(TAG, format("Insert puzzle image: id: %d, data %s", id, contentValues));
            }
            return taskBuilder;
        }
    }

    class SQLiteSimplePuzzleBuilder<T> extends SQLitePuzzleBuilder<SimplePuzzleBuilder, T> implements SimplePuzzleBuilder<T> {

        SQLiteSimplePuzzleBuilder(T parentTaskBuilder, ContentValues contentValues) {
            super(parentTaskBuilder, contentValues);
            this.contentValues.put(Task.TYPE, SimplePuzzle.TYPE_VALUE);
        }

        @Override
        public SimplePuzzleBuilder question(String question) {
            contentValues.put(SimplePuzzle.QUESTION, question);
            return this;
        }

        @Override
        public SimplePuzzleBuilder answer(String answer) {
            contentValues.put(SimplePuzzle.ANSWER, answer);
            return this;
        }
    }


    class SQLiteBeaconTaskBuilder extends SQLiteTaskBuilder<BeaconTaskBuilder> implements BeaconTaskBuilder {

        SQLiteBeaconTaskBuilder(ContentValues contentValues, String name) {
            super(contentValues, name);
            this.contentValues.put(Task.TYPE, BeaconTask.TYPE_VALUE);
        }

        @Override
        public BeaconTaskBuilder beacon(int major, int minor) {
            this.contentValues.put(BeaconTask.MAJOR, major);
            this.contentValues.put(BeaconTask.MINOR, minor);
            return this;
        }
    }

    class SQLiteAlternativeBeaconTaskBuilder extends SQLiteBeaconTaskBuilder implements AlternativeTaskBuilder.IsForBeaconTask {

        private final SQLiteNFCTaskBuilder parentTaskBuilder;

        SQLiteAlternativeBeaconTaskBuilder(SQLiteNFCTaskBuilder parentTaskBuilder, ContentValues contentValues) {
            super(contentValues, null);
            this.parentTaskBuilder = parentTaskBuilder;
            this.contentValues.putNull(Task.STATUS_ENUM);
        }

        @Override
        public NFCTaskBuilder alternativeTaskDone() {
            contentValues.put(Task.PUZZLE_ID, parentTaskBuilder.contentValues.getAsLong(Task.PUZZLE_ID));
            long id = sqLiteDatabase.insertOrThrow(Task.TABLE, null, contentValues);
            Log.i(TAG, format("Insert alternative task: id: %d, data %s", id, contentValues));
            parentTaskBuilder.contentValues.put(NFCTask.ALTERNATIVE_TASK_ID, id);
            return parentTaskBuilder;
        }
    }

    class SQLiteCodeTaskBuilder extends SQLiteTaskBuilder<CodeTaskBuilder> implements CodeTaskBuilder {

        SQLiteCodeTaskBuilder(ContentValues contentValues, String name) {
            super(contentValues, name);
            this.contentValues.put(Task.TYPE, CodeTask.TYPE_VALUE);
        }

        @Override
        public CodeTaskBuilder ean(int ean) {
            this.contentValues.put(CodeTask.EAN, ean);
            return this;
        }

        @Override
        public CodeTaskBuilder qr(String qr) {
            this.contentValues.put(CodeTask.QR, qr);
            return this;
        }

    }

    class SQLiteAlternativeCodeTaskBuilder extends SQLiteCodeTaskBuilder implements AlternativeTaskBuilder.IsForCodeTask {

        private final SQLiteNFCTaskBuilder parentTaskBuilder;

        SQLiteAlternativeCodeTaskBuilder(SQLiteNFCTaskBuilder parentTaskBuilder, ContentValues contentValues) {
            super(contentValues, null);
            this.parentTaskBuilder = parentTaskBuilder;
            this.contentValues.putNull(Task.STATUS_ENUM);
        }

        @Override
        public NFCTaskBuilder alternativeTaskDone() {
            contentValues.put(Task.PUZZLE_ID, parentTaskBuilder.contentValues.getAsLong(Task.PUZZLE_ID));
            long id = sqLiteDatabase.insertOrThrow(Task.TABLE, null, contentValues);
            Log.i(TAG, format("Insert alternative task: id: %d, data %s", id, contentValues));
            parentTaskBuilder.contentValues.put(NFCTask.ALTERNATIVE_TASK_ID, id);
            return parentTaskBuilder;
        }
    }

    class SQLiteGPSTaskBuilder extends SQLiteTaskBuilder<GPSTaskBuilder> implements GPSTaskBuilder {

        SQLiteGPSTaskBuilder(ContentValues contentValues, String name) {
            super(contentValues, name);
            this.contentValues.put(Task.TYPE, GPSTask.TYPE_VALUE);
        }

        @Override
        public GPSTaskBuilder radius(double radius) {
            this.contentValues.put(GPSTask.RADIUS, radius);
            return this;
        }
    }

    class SQLiteAlternativeGPSTaskBuilder extends SQLiteGPSTaskBuilder implements AlternativeTaskBuilder.IsForGPSTask {

        private final SQLiteNFCTaskBuilder parentTaskBuilder;

        SQLiteAlternativeGPSTaskBuilder(SQLiteNFCTaskBuilder parentTaskBuilder, ContentValues contentValues) {
            super(contentValues, null);
            this.parentTaskBuilder = parentTaskBuilder;
            this.contentValues.putNull(Task.STATUS_ENUM);
        }

        @Override
        public NFCTaskBuilder alternativeTaskDone() {
            contentValues.put(Task.PUZZLE_ID, parentTaskBuilder.contentValues.getAsLong(Task.PUZZLE_ID));
            long id = sqLiteDatabase.insertOrThrow(Task.TABLE, null, contentValues);
            Log.i(TAG, format("Insert alternative task: id: %d, data %s", id, contentValues));
            parentTaskBuilder.contentValues.put(NFCTask.ALTERNATIVE_TASK_ID, id);
            return parentTaskBuilder;
        }
    }

    class SQLiteNFCTaskBuilder extends SQLiteTaskBuilder<NFCTaskBuilder> implements NFCTaskBuilder {

        private ContentValues alternativeTaskContentValues;

        SQLiteNFCTaskBuilder(ContentValues contentValues, String name) {
            super(contentValues, name);
            this.alternativeTaskContentValues = new ContentValues();
            this.contentValues.put(Task.TYPE, NFCTask.TYPE_VALUE);
        }

        @Override
        public NFCTaskBuilder nfc(byte[] nfc) {
            this.contentValues.put(NFCTask.NFC, nfc);
            return this;
        }

        @Override
        public AlternativeTaskBuilder.IsForBeaconTask alternativeBeaconTask() {
            return new SQLiteAlternativeBeaconTaskBuilder(this, alternativeTaskContentValues);
        }

        @Override
        public AlternativeTaskBuilder.IsForCodeTask alternativeCodeTask() {
            return new SQLiteAlternativeCodeTaskBuilder(this, alternativeTaskContentValues);
        }

        @Override
        public AlternativeTaskBuilder.IsForGPSTask alternativeGPSTask() {
            return new SQLiteAlternativeGPSTaskBuilder(this, alternativeTaskContentValues);
        }
    }
}

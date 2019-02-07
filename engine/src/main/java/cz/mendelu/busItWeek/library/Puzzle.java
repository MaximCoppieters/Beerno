package cz.mendelu.busItWeek.library;

import android.content.ContentValues;
import android.provider.BaseColumns;
import android.util.Log;

import java.util.Map;

import static java.lang.String.copyValueOf;
import static java.lang.String.format;

/**
 * Created by Honza on 07.03.2017.
 */

public abstract class Puzzle {

    private static final String TAG = Puzzle.class.getSimpleName();

    static final String TABLE = "Puzzle";

    static final String ID = BaseColumns._ID;
    static final String TYPE = "type";
    static final String HINT = "hint";
    static final String PUZZLE_TIME = "time";

    static Puzzle load(ReadableTaskDatabase rdb, ContentValues values) {
        Integer puzzleType = values.getAsInteger(TYPE);
        Log.d(TAG, format("Load puzzle type: %s", puzzleType));
        switch (puzzleType) {
            case AssignmentPuzzle.TYPE_VALUE: {
                return new AssignmentPuzzle(values);
            }
            case ChoicePuzzle.TYPE_VALUE: {
                long id = values.getAsLong(Puzzle.ID);
                Map<String, Boolean> choices = rdb.findChoicesForPuzzle(id);
                return new ChoicePuzzle(values, choices);
            }
            case ImageSelectPuzzle.TYPE_VALUE: {
                long id = values.getAsLong(Puzzle.ID);
                Map<Integer, Boolean> images = rdb.findImagesForPuzzle(id);
                return new ImageSelectPuzzle(values, images);
            }
            case SimplePuzzle.TYPE_VALUE: {
                return new SimplePuzzle(values);
            }
        }
        throw new StoryLineRuntimeException("Unknown puzzle type " + puzzleType);
    }

    protected final ContentValues contentValues;

    public Puzzle(ContentValues contentValues) {
        this.contentValues = contentValues;
    }

    public Long getId() {
        return contentValues.getAsLong(ID);
    }

    public Long getPuzzleTime() {
        return contentValues.getAsLong(PUZZLE_TIME);
    }

    public String getHint() {
        return contentValues.getAsString(HINT);
    }

    @Override
    public String toString() {
        return format("Puzzle id: %d, values: %s", getId(), contentValues);
    }
}

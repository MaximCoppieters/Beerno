package cz.mendelu.busItWeek.library;

import android.content.ContentValues;
import android.util.Log;

import static java.lang.String.format;

/**
 * Created by Honza on 07.03.2017.
 */

public class SimplePuzzle extends Puzzle {

    private static final String TAG = SimplePuzzle.class.getSimpleName();

    static final int TYPE_VALUE = 4;

    static final String QUESTION = "question";

    static final String ANSWER = "answer";

    public SimplePuzzle(ContentValues contentValues) {
        super(contentValues);
        Log.d(TAG, format("Create: %s", contentValues));
    }

    public String getQuestion() {
        return contentValues.getAsString(QUESTION);
    }

    public String getAnswer() {
        return contentValues.getAsString(ANSWER);
    }
}

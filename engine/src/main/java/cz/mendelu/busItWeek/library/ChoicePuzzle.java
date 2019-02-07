package cz.mendelu.busItWeek.library;

import android.content.ContentValues;
import android.provider.BaseColumns;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;

/**
 * Created by Honza on 07.03.2017.
 */

public class ChoicePuzzle extends Puzzle {

    private static final String TAG = ChoicePuzzle.class.getSimpleName();

    static final int TYPE_VALUE = 2;

    static final String CHOICES_TABLE = "choice";

    static final String CHOICES_ID = BaseColumns._ID;

    static final String CHOICES_PUZZLE_ID = "puzzle_id";

    static final String CHOICES_ANSWER = "answer";

    static final String CHOICES_CORRECT = "correct";

    static final  String QUESTION = "question";

    private final Map<String, Boolean> choices;
    private Map<Integer, Boolean> answerByOrder;

    public ChoicePuzzle(ContentValues contentValues, Map<String, Boolean> choices) {
        super(contentValues);
        this.choices = choices;
        this.fillAnswerByOrder(choices);
        Log.d(TAG, format("Create: %s, choices: %s", contentValues, choices));
    }

    private void fillAnswerByOrder(Map<String, Boolean> choices) {
        answerByOrder = new HashMap<>();
        int index = 0;
        for(Map.Entry<String, Boolean> entry : choices.entrySet()) {
            answerByOrder.put(index++, entry.getValue());
        }
    }

    public String getQuestion() {
        return contentValues.getAsString(QUESTION);
    }

    public Map<String, Boolean> getChoices() {
        return choices;
    }

    /**
     * Return answer for image by order in images Map.
     * @param index
     * @return
     */
    public boolean getAnswerForChoice(int index) {
        return answerByOrder.get(index);
    }
}

package cz.mendelu.busItWeek.library;

import android.content.ContentValues;
import android.provider.BaseColumns;
import android.util.Log;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.lang.String.format;

/**
 * Created by Honza on 07.03.2017.
 */

public class ImageSelectPuzzle extends Puzzle {

    private static final String TAG = ImageSelectPuzzle.class.getSimpleName();

    static final int TYPE_VALUE = 3;

    static final String IMAGES_TABLE = "image";

    static final String IMAGES_ID = BaseColumns._ID;

    static final String IMAGES_PUZZLE_ID = "puzzle_id";

    static final String IMAGES_RESOURCE = "resource_id";

    static final String IMAGES_CORRECT = "correct";

    static final  String QUESTION = "question";

    private Map<Integer, Boolean> images;
    private Map<Integer, Boolean> answerByOrder;

    public ImageSelectPuzzle(ContentValues contentValues, Map<Integer, Boolean> images) {
        super(contentValues);
        this.images = images;
        this.fillAnswerByOrder(images);
        Log.d(TAG, format("Create: %s, images: %s", contentValues, images));
    }

    private void fillAnswerByOrder(Map<Integer, Boolean> images) {
        answerByOrder = new HashMap<>();
        int index = 0;
        for(Map.Entry<Integer, Boolean> entry : images.entrySet()) {
            answerByOrder.put(index++, entry.getValue());
        }
    }

    public String getQuestion() {
        return contentValues.getAsString(QUESTION);
    }

    /**
     * Map of images. Map is orderd as choices was created.
     * This map has fix order of items.
     * @return Map contains <code>key</code> as image resource as and <code>value</code> is answer.
     */
    public Map<Integer, Boolean> getImages() {
        return images;
    }

    /**
     * Return answer for image by order in images Map.
     * @param index
     * @return
     */
    public boolean getAnswerForImage(int index) {
        return answerByOrder.get(index);
    }
}

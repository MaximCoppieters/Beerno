package cz.mendelu.busItWeek.library

import android.content.ContentValues
import android.content.Context
import cz.mendelu.busItWeek.library.utils.StubStoryLineDatabaseHelper

/**
 * Created by Honza on 01.03.2017.
 */
class DummyStoryLineOpenHelper extends StoryLineOpenHelper {

    static String NAME = "DummyStoryLineOpenHelper.db"
    static StoryLineDatabaseHelper databaseHelper = new StubStoryLineDatabaseHelper()

    public DummyStoryLineOpenHelper(Context context) {
        super(context, NAME, databaseHelper)
    }

    public void clearDatabase() {
        onUpgrade(getWritableDatabase(), 1, 1);
    }

    public long task(Map<String, Objects> task) {
        def db = getWritableDatabase();
        return db.insertOrThrow(Task.TABLE, null, contentValues(task))
    }

    public long puzzle(Map<String, Objects> puzzle) {
        def db = getWritableDatabase();
        return db.insertOrThrow(Puzzle.TABLE, null, contentValues(puzzle))
    }

    public long choice(Map<String, Objects> choice) {
        def db = getWritableDatabase();
        return db.insertOrThrow(ChoicePuzzle.CHOICES_TABLE, null, contentValues(choice))
    }

    public long image(Map<String, Objects> image) {
        def db = getWritableDatabase();
        return db.insertOrThrow(ImageSelectPuzzle.IMAGES_TABLE, null, contentValues(image))
    }

    private ContentValues contentValues(Map<String, Objects> map) {
        ContentValues values = new ContentValues()
        map.each{ k, v ->
            if (v instanceof TaskStatus) {
                values.put(k, ((TaskStatus)v).name())
            } else {
                values.put(k, v)
            }
        }
        return values;
    }
}

package cz.mendelu.busItWeek.library;

import android.content.Context;
import android.util.Log;

import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static java.lang.String.format;

/**
 * Created by Honza on 01.03.2017.
 */

public class StoryLine {

    private static final String TAG = StoryLine.class.getSimpleName();

    private static final String DEFAULT_DB_NAME = "storyLine";
    private static final String FORMAT_DB_NAME = "%s.db";

    public static StoryLine open(Context context, Class<? extends StoryLineDatabaseHelper> helperClass) {
        return open(context, DEFAULT_DB_NAME, helperClass);
    }

    public static StoryLine open(Context context, String name, Class<? extends StoryLineDatabaseHelper> helperClass) {
        if (context == null)
            throw new StoryLineRuntimeException("Parameter context can't be null");

        Log.i(TAG, format("open StoryLine, name: %s, helperClass: %s", name, helperClass.getSimpleName()));
        StoryLineDatabaseHelper databaseHelper = createBuilder(helperClass);
        String dbName = format(FORMAT_DB_NAME, name);
        StoryLineOpenHelper storyLineOpenHelper = new StoryLineOpenHelper(context, dbName, databaseHelper);
        return new StoryLine(storyLineOpenHelper);
    }

    private static StoryLineDatabaseHelper createBuilder(Class<? extends StoryLineDatabaseHelper> helperClass) {
        try {
            Constructor<?> constructor = helperClass.getConstructor();
            return (StoryLineDatabaseHelper) constructor.newInstance();
        } catch (NoSuchMethodException e) {
            throw new StoryLineRuntimeException(format("Class %s must have constructor without parameters.", helperClass.getName()), e);
        } catch (Exception e) {
            throw new StoryLineRuntimeException(format("Create new instance of class %s failed.", helperClass.getName()), e);
        }
    }

    private final TaskDatabase taskDatabase;

    private StoryLine(TaskDatabase taskDatabase) {
        this.taskDatabase = taskDatabase;
    }

    public Task currentTask() {
        Log.d(TAG, "Get current Task.");
        ReadableTaskDatabase rdb = taskDatabase.openReadableTaskDatabase();
        try {
            Collection<Task> tasks = rdb.findTaskByStatus(TaskStatus.CURRENT);
            Task currentTask = (tasks.isEmpty()) ? null : tasks.iterator().next();
            Log.i(TAG, format("Get current task: %s.", currentTask));
            return currentTask;
        } catch (Exception e) {
            throw new StoryLineRuntimeException("Load current task failed.", e);
        } finally {
            rdb.close();
        }
    }

    public List<Task> taskList() {
        Log.d(TAG, "Get task list.");
        ReadableTaskDatabase rdb = taskDatabase.openReadableTaskDatabase();
        try {
            Collection<Task> tasks = rdb.findAllNonAlternativeTasks();
            Log.i(TAG, format("Get task list: %s.", tasks));
            return new LinkedList<>(tasks);
        } catch (Exception e) {
            throw new StoryLineRuntimeException("Load task list failed.", e);
        } finally {
            rdb.close();
        }
    }

    public void reset() {
        throw new UnsupportedOperationException("Not implemented yet!");
    }
}

package cz.mendelu.busItWeek.library

import android.database.sqlite.SQLiteDatabase
import cz.mendelu.busItWeek.library.builder.StoryLineBuilder
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import org.robospock.RoboSpecification

import java.lang.reflect.Field
import java.lang.reflect.Modifier

/**
 * Created by Honza on 02.03.2017.
 */
@Config(manifest=Config.NONE)
class StoryLineOpenHelperTest extends RoboSpecification {

    def context
    def mockStoryLineDatabaseHelper

    def setup() {
        context = RuntimeEnvironment.application
        mockStoryLineDatabaseHelper = Mock(StoryLineDatabaseHelper)
        mockStoryLineDatabaseHelper.getVersion() >> 1
    }

    def "OnCreate"() {
        when:
        def storyLineOpenHelper = new StoryLineOpenHelper(context, "StoryLineOpenHelperTest.db", mockStoryLineDatabaseHelper)

        and:
        storyLineOpenHelper.getWritableDatabase()

        then:
        1 * mockStoryLineDatabaseHelper.onCreate(!null)
    }

    def "Create table"() {
        setup:
        def storyLineOpenHelper = new StoryLineOpenHelper(context, "StoryLineOpenHelperTest", mockStoryLineDatabaseHelper)
        def sqLiteDatabase = Mock(SQLiteDatabase)
        def sqlValidation = [:]
            sqlValidation[Task.TABLE] = { sql -> containsColumns(sql, Task.class, CodeTask.class, BeaconTask.class, GPSTask.class, NFCTask.class)}
            sqlValidation[Puzzle.TABLE] = { sql -> containsColumns(sql, Puzzle.class, AssignmentPuzzle.class, ChoicePuzzle.class, ImageSelectPuzzle.class, SimplePuzzle.class)}
            sqlValidation[ChoicePuzzle.CHOICES_TABLE] = {sql -> containsColumns(sql, ChoicePuzzle.CHOICES_ID, ChoicePuzzle.CHOICES_ID, ChoicePuzzle.CHOICES_ANSWER, ChoicePuzzle.CHOICES_CORRECT)}
            sqlValidation[ImageSelectPuzzle.IMAGES_TABLE] = {sql -> containsColumns(sql, ImageSelectPuzzle.IMAGES_ID, ImageSelectPuzzle.IMAGES_PUZZLE_ID, ImageSelectPuzzle.IMAGES_RESOURCE, ImageSelectPuzzle.IMAGES_CORRECT)}

        when:
        storyLineOpenHelper.createTables(sqLiteDatabase)

        then:
        4 * sqLiteDatabase.execSQL(_) >> { sql ->
            sql.each {
                assert sqlValidation[tableName(it)](it) == true
            }
        }
    }

    def "Test private method dropAllTables"() {
        setup:
        def storyLineOpenHelper = new StoryLineOpenHelper(context, "StoryLineOpenHelperTest", mockStoryLineDatabaseHelper)
        def sqLiteDatabase = Mock(SQLiteDatabase)

        when:
        storyLineOpenHelper.dropAllTables(sqLiteDatabase)

        then:
        4 * sqLiteDatabase.execSQL(_)
    }

    def "Test onUpgrade"() {
        setup:
        def storyLineOpenHelper = new StoryLineOpenHelper(context, "StoryLineOpenHelperTest", mockStoryLineDatabaseHelper)
        def sqLiteDatabase = Mock(SQLiteDatabase)

        when:
        storyLineOpenHelper.onUpgrade(sqLiteDatabase, 1, 2)

        then:
        8 * sqLiteDatabase.execSQL(_)
    }

    def "First created task is current"() {
        setup:
        def storyLineOpenHelper = new StoryLineOpenHelper(
                context, "CurrentTaskDbTest.db",
                new TestCurrentDBHelper(1))

        when:
        def tasks = storyLineOpenHelper.openReadableTaskDatabase().findAllNonAlternativeTasks();

        then:
        tasks.size() == 3
        tasks[0].name == "Task 1"
        tasks[0].taskStatus == TaskStatus.CURRENT
        tasks[1].name == "Task 2"
        tasks[1].taskStatus == TaskStatus.WAITING
        tasks[2].name == "Task 3"
        tasks[2].taskStatus == TaskStatus.WAITING
    }

    private String tableName(String sql) {
        return sql.substring("CREATE TABLE".length(), sql.indexOf("(")).trim();
    }

    private boolean containsColumns(String sql, Class ... classes) {
        for (Class cls : classes) {
            def columns = extractColumns(cls)
            if (!containsColumns(sql, columns as String[])) {
                return false
            }
        }
        return true
    }

    private boolean containsColumns(String sql, String ... columns) {
        for (String col : columns) {
            if (!sql.contains(col)) {
                print "\nMissing column name '$col' definition for table ${tableName(sql)}\n"
                return false;
            }
        }
        return true
    }

    private List<String> extractColumns(Class type) {
        type.getDeclaredFields()
                .findAll {field -> isColumnField(field)}
                .each    {field -> field.setAccessible(true)}
                .collect {field -> (String)field.get(null)}
    }
    private boolean isColumnField(Field field) {
        if (!(field.getModifiers() & Modifier.FINAL)) return false
        if (field.getType() != String.class) return false
        if (field.name.contains("TABLE")) return false
        if (field.name.startsWith("CHOICES_")) return false
        if (field.name.startsWith("IMAGES_")) return false
        if (field.name == "TAG") return false
        return true
    }

    private static class TestCurrentDBHelper extends StoryLineDatabaseHelper {

        TestCurrentDBHelper(int version) {
            super(version)
        }

        @Override
        protected void onCreate(StoryLineBuilder builder) {
            builder.addBeaconTask("Task 1").beacon(1,2).taskDone()
            builder.addCodeTask("Task 2").ean(1).taskDone()
            builder.addGPSTask("Task 3").location(1,2).taskDone()
        }
    }
}

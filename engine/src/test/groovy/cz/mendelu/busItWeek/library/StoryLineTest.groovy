package cz.mendelu.busItWeek.library

import cz.mendelu.busItWeek.library.builder.StoryLineBuilder
import cz.mendelu.busItWeek.library.utils.StubStoryLineDatabaseHelper
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import org.robospock.RoboSpecification

/**
 * Created by Honza on 01.03.2017.
 */
@Config(manifest=Config.NONE)
class StoryLineTest extends RoboSpecification {

    def context = RuntimeEnvironment.application
    def storyLineOpenHelper = new DummyStoryLineOpenHelper(context)

    def setup() {
        storyLineOpenHelper.clearDatabase();
    }

    def "Open with default database"() {
        when: "When First time call method Open"
        def storyLine = StoryLine.open(context, StubStoryLineDatabaseHelper.class)

        then: "return not null."
        storyLine != null
    }

    def "First Open"() {
        when: "When First time call method Open"
        def storyLine = StoryLine.open(context, "testStoryLine", StubStoryLineDatabaseHelper.class)

        then: "return not null."
        storyLine != null
    }

    def "Open with null context"() {
        when: "Context set to null"
        StoryLine.open(null, "testStoryLine", StubStoryLineDatabaseHelper.class)

        then: "method throw StoryLineRuntimeException."
        thrown StoryLineRuntimeException
    }

    def "Open with BadStoryLineDatabaseHelper, constructor with arguments"() {
        when: "When used StoryLineDatabaseHelper without proper constructor"
        StoryLine.open(context, "testStoryLine", BadStoryLineDatabaseHelper.class)

        then: "method throw StoryLineRuntimeException."
        thrown StoryLineRuntimeException
    }

    def "CurrentTask: no current task"() {
        setup:
        def storyLine = new StoryLine(storyLineOpenHelper)

        when:
        def task = storyLine.currentTask()

        then:
        task == null
    }

    def "CurrentTask: get current task"() {
        setup:
        storyLineOpenHelper.task([
                "$Task.TYPE"       : BeaconTask.TYPE_VALUE,
                "$Task.STATUS_ENUM": TaskStatus.SUCCESS
        ])
        storyLineOpenHelper.task([
                "$Task.TYPE"        : GPSTask.TYPE_VALUE,
                "$Task.STATUS_ENUM" : TaskStatus.CURRENT
        ])
        storyLineOpenHelper.task([
                "$Task.TYPE"        : CodeTask.TYPE_VALUE,
                "$Task.STATUS_ENUM" : TaskStatus.WAITING
        ])
        def storyLine = new StoryLine(storyLineOpenHelper)

        when:
        def task = storyLine.currentTask()

        then:
        task != null
        task instanceof GPSTask
        task.taskStatus == TaskStatus.CURRENT
    }

    def "Get List od all tasks"() {
        setup:
        storyLineOpenHelper.task([
                "$Task.TYPE"        : BeaconTask.TYPE_VALUE,
                "$Task.STATUS_ENUM" : TaskStatus.SUCCESS
        ])
        storyLineOpenHelper.task([
                "$Task.TYPE"        : GPSTask.TYPE_VALUE,
                "$Task.STATUS_ENUM" : TaskStatus.CURRENT
        ])
        storyLineOpenHelper.task([
                "$Task.TYPE"        : CodeTask.TYPE_VALUE,
                "$Task.STATUS_ENUM" : TaskStatus.WAITING
        ])
        def storyLine = new StoryLine(storyLineOpenHelper)

        when:
        def list = storyLine.taskList()

        then:
        list.size == 3
    }

    def "Test if alternative task is not in List"() {
        setup:
        def atId = storyLineOpenHelper.task([
                "$Task.TYPE"        : BeaconTask.TYPE_VALUE
        ])
        storyLineOpenHelper.task([
                "$Task.TYPE"        : NFCTask.TYPE_VALUE,
                "$Task.STATUS_ENUM" : TaskStatus.CURRENT.name(),
                "$Task.NAME"        : "Test if alternative task is not in List",
                "$NFCTask.ALTERNATIVE_TASK_ID" : atId
        ])
        def storyLine = new StoryLine(storyLineOpenHelper)

        when:
        def list = storyLine.taskList()

        then:
        list.size == 1
        list[0].name == "Test if alternative task is not in List"
    }

    def "Reset"() {

    }

    static class BadStoryLineDatabaseHelper extends StoryLineDatabaseHelper {


        BadStoryLineDatabaseHelper(int version) {
            super(version)
        }

        @Override
        protected void onCreate(StoryLineBuilder builder) {

        }
    }

}

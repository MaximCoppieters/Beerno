package cz.mendelu.busItWeek.library

import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import org.robospock.RoboSpecification

/**
 * Created by Honza on 10.03.2017.
 */
@Config(manifest=Config.NONE)
class TaskTest extends RoboSpecification {

    def context = RuntimeEnvironment.application
    def storyLineOpenHelper = new DummyStoryLineOpenHelper(context)

    def "Load BeaconTask"() {
        setup:
        def tId = storyLineOpenHelper.task([
                "$Task.TYPE"       : BeaconTask.TYPE_VALUE,
                "$Task.STATUS_ENUM": TaskStatus.CURRENT.name()])

        when:
        def task = storyLineOpenHelper.openReadableTaskDatabase().findTaskById(tId);

        then:
        task instanceof BeaconTask
    }

    def "Load CodeTask"() {
        setup:
        def tId = storyLineOpenHelper.task([
                "$Task.TYPE": CodeTask.TYPE_VALUE,
                "$Task.STATUS_ENUM": TaskStatus.CURRENT.name()])

        when:
        def task = storyLineOpenHelper.openReadableTaskDatabase().findTaskById(tId);

        then:
        task instanceof CodeTask
    }

    def "Load GPSTask"() {
        setup:
        def tId = storyLineOpenHelper.task([
                "$Task.TYPE": GPSTask.TYPE_VALUE,
                "$Task.STATUS_ENUM": TaskStatus.CURRENT.name()])

        when:
        def task = storyLineOpenHelper.openReadableTaskDatabase().findTaskById(tId);

        then:
        task instanceof GPSTask
    }

    def "Load NFCTask"() {
        setup:
        def tId = storyLineOpenHelper.task([
                "$Task.TYPE": NFCTask.TYPE_VALUE,
                "$Task.STATUS_ENUM": TaskStatus.CURRENT.name()])

        when:
        def task = storyLineOpenHelper.openReadableTaskDatabase().findTaskById(tId);

        then:
        task instanceof NFCTask
    }


    def "Finish: success"() {
        setup:
        def id = storyLineOpenHelper.task([
                "$Task.TYPE"        : BeaconTask.TYPE_VALUE,
                "$Task.STATUS_ENUM" : TaskStatus.CURRENT
        ])
        def task = storyLineOpenHelper.openReadableTaskDatabase().findTaskById(id)

        when:
        task.finish(true)

        then:
        storyLineOpenHelper.openReadableTaskDatabase().findTaskById(id).taskStatus == TaskStatus.SUCCESS

        and:
        task.taskStatus == TaskStatus.SUCCESS
    }

    def "Finish: failure"() {
        setup:
        def id = storyLineOpenHelper.task([
                "$Task.TYPE"        : BeaconTask.TYPE_VALUE,
                "$Task.STATUS_ENUM" : TaskStatus.CURRENT
        ])
        def task = storyLineOpenHelper.openReadableTaskDatabase().findTaskById(id)

        when:
        task.finish(false)

        then:
        storyLineOpenHelper.openReadableTaskDatabase().findTaskById(id).taskStatus == TaskStatus.FAILURE

        and:
        task.taskStatus == TaskStatus.FAILURE
    }

    def "Skip"() {
        setup:
        def id = storyLineOpenHelper.task([
                "$Task.TYPE"        : BeaconTask.TYPE_VALUE,
                "$Task.STATUS_ENUM" : TaskStatus.CURRENT
        ])
        def task = storyLineOpenHelper.openReadableTaskDatabase().findTaskById(id)

        when:
        task.skip()

        then:
        storyLineOpenHelper.openReadableTaskDatabase().findTaskById(id).taskStatus == TaskStatus.SKIPPED

        and:
        task.taskStatus == TaskStatus.SKIPPED
    }

    def "Finish/Skip: move next"() {
        setup:
        def id = [:]
        id[1] = storyLineOpenHelper.task([
                "$Task.TYPE"        : BeaconTask.TYPE_VALUE,
                "$Task.STATUS_ENUM" : TaskStatus.CURRENT
        ])
        id[2] = storyLineOpenHelper.task([
                "$Task.TYPE"        : CodeTask.TYPE_VALUE,
                "$Task.STATUS_ENUM" : TaskStatus.WAITING
        ])
        def task = storyLineOpenHelper.openReadableTaskDatabase().findTaskById(id[1])

        when:
        task.skip()

        then:
        storyLineOpenHelper.openReadableTaskDatabase().findTaskById(id[2]).taskStatus == TaskStatus.CURRENT
    }

    def "Finish/Skip: move next over alternative task"() {
        setup:
        def id = [:]
        id[1] = storyLineOpenHelper.task([
                "$Task.TYPE"        : BeaconTask.TYPE_VALUE,
                "$Task.STATUS_ENUM" : TaskStatus.CURRENT
        ])
        id[2] = storyLineOpenHelper.task([
                "$Task.TYPE"        : BeaconTask.TYPE_VALUE
        ])
        id[3] = storyLineOpenHelper.task([
                "$Task.TYPE"        : CodeTask.TYPE_VALUE,
                "$Task.STATUS_ENUM" : TaskStatus.WAITING
        ])
        def task = storyLineOpenHelper.openReadableTaskDatabase().findTaskById(id[1])

        when:
        task.skip()

        then:
        storyLineOpenHelper.openReadableTaskDatabase().findTaskById(id[2]).taskStatus == null
        storyLineOpenHelper.openReadableTaskDatabase().findTaskById(id[3]).taskStatus == TaskStatus.CURRENT
    }

    def "GetName"() {
        setup:
        def tId = storyLineOpenHelper.task([
                "$Task.TYPE" : NFCTask.TYPE_VALUE,
                "$Task.STATUS_ENUM" : TaskStatus.CURRENT.name(),
                "$Task.NAME" : "name"
        ])

        when:
        def task = storyLineOpenHelper.openReadableTaskDatabase().findTaskById(tId);

        then:
        task.getName() == "name"
    }

    def "GetVictoryPoints"() {
        setup:
        def tId = storyLineOpenHelper.task([
                "$Task.TYPE" : NFCTask.TYPE_VALUE,
                "$Task.STATUS_ENUM" : TaskStatus.CURRENT.name(),
                "$Task.VICTORY_POINTS" : 11
        ])

        when:
        def task = storyLineOpenHelper.openReadableTaskDatabase().findTaskById(tId);

        then:
        task.getVictoryPoints() == 11
    }

    def "GetPuzzle"() {
        setup:
        def pId = storyLineOpenHelper.puzzle([
                "$Puzzle.TYPE": SimplePuzzle.TYPE_VALUE,
        ])
        def tId = storyLineOpenHelper.task([
                "$Task.TYPE" : NFCTask.TYPE_VALUE,
                "$Task.STATUS_ENUM" : TaskStatus.CURRENT.name(),
                "$Task.PUZZLE_ID" : pId
        ])

        when:
        def task = storyLineOpenHelper.openReadableTaskDatabase().findTaskById(tId);

        then:
        task.getPuzzle() != null
    }

    def "GetPuzzle when is not defined"() {
        setup:
        def tId = storyLineOpenHelper.task([
                "$Task.TYPE" : NFCTask.TYPE_VALUE,
                "$Task.STATUS_ENUM" : TaskStatus.CURRENT.name()
        ])

        when:
        def task = storyLineOpenHelper.openReadableTaskDatabase().findTaskById(tId);

        then:
        task.getPuzzle() == null
    }

    def "GetHint"() {
        setup:
        def tId = storyLineOpenHelper.task([
                "$Task.TYPE" : NFCTask.TYPE_VALUE,
                "$Task.STATUS_ENUM" : TaskStatus.CURRENT.name(),
                "$Task.HINT" : "Hint"
        ])

        when:
        def task = storyLineOpenHelper.openReadableTaskDatabase().findTaskById(tId);

        then:
        task.getHint() == "Hint"
    }

    def "GetLatitude"() {
        setup:
        def tId = storyLineOpenHelper.task([
                "$Task.TYPE"        : GPSTask.TYPE_VALUE,
                "$Task.STATUS_ENUM" : TaskStatus.CURRENT.name(),
                "$Task.LATITUDE" : 1.11])

        when:
        def task = storyLineOpenHelper.openReadableTaskDatabase().findTaskById(tId);

        then:
        task.getLatitude() == 1.11
    }

    def "GetLongitude"() {
        setup:
        def tId = storyLineOpenHelper.task([
                "$Task.TYPE"        : GPSTask.TYPE_VALUE,
                "$Task.STATUS_ENUM" : TaskStatus.CURRENT.name(),
                "$Task.LONGITUDE" : 2.22])

        when:
        def task = storyLineOpenHelper.openReadableTaskDatabase().findTaskById(tId);

        then:
        task.getLongitude() == 2.22
    }
}

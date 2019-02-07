package cz.mendelu.busItWeek.library

import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import org.robospock.RoboSpecification

/**
 * Created by Honza on 10.03.2017.
 */
@Config(manifest=Config.NONE)
class NFCTaskTest extends RoboSpecification {

    def context = RuntimeEnvironment.application
    def storyLineOpenHelper = new DummyStoryLineOpenHelper(context)

    def "GetNFC"() {
        setup:
        def tId = storyLineOpenHelper.task([
                "$Task.TYPE"       : NFCTask.TYPE_VALUE,
                "$Task.STATUS_ENUM": TaskStatus.CURRENT.name(),
                "$NFCTask.NFC"     : [1,2,3,4,5,5,6,7,8,9] as byte[]])

        when:
        def task = storyLineOpenHelper.openReadableTaskDatabase().findTaskById(tId);

        then:
        task.getNFC() == [1,2,3,4,5,5,6,7,8,9]
    }

    def "GetAlternativeTask"() {
        setup:
        def atId = storyLineOpenHelper.task([
                "$Task.TYPE"        : BeaconTask.TYPE_VALUE])

        def tId = storyLineOpenHelper.task([
                "$Task.TYPE"        : NFCTask.TYPE_VALUE,
                "$Task.STATUS_ENUM" : TaskStatus.CURRENT.name(),
                "$NFCTask.ALTERNATIVE_TASK_ID" : atId])

        when:
        def task = storyLineOpenHelper.openReadableTaskDatabase().findTaskById(tId);

        then:
        task.getAlternativeTask() != null
        task.getAlternativeTask() instanceof BeaconTask
    }

    def "GetAlternativeTask when is not define"() {
        setup:
        def tId = storyLineOpenHelper.task([
                "$Task.TYPE"        : NFCTask.TYPE_VALUE,
                "$Task.STATUS_ENUM" : TaskStatus.CURRENT.name()
        ])

        when:
        def task = storyLineOpenHelper.openReadableTaskDatabase().findTaskById(tId);

        then:
        task.getAlternativeTask() == null
    }
}

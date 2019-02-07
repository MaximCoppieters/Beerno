package cz.mendelu.busItWeek.library

import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import org.robospock.RoboSpecification

/**
 * Created by Honza on 10.03.2017.
 */
@Config(manifest=Config.NONE)
class BeaconTaskTest extends RoboSpecification {

    def context = RuntimeEnvironment.application
    def storyLineOpenHelper = new DummyStoryLineOpenHelper(context)

    def "GetMajor"() {
        setup:
        def tId = storyLineOpenHelper.task([
                "$Task.TYPE"       : BeaconTask.TYPE_VALUE,
                "$Task.STATUS_ENUM": TaskStatus.CURRENT.name(),
                "$BeaconTask.MAJOR": 123])

        when:
        def task = storyLineOpenHelper.openReadableTaskDatabase().findTaskById(tId);

        then:
        task.getMajor() == 123
    }

    def "GetMinor"() {
        setup:
        def tId = storyLineOpenHelper.task([
                "$Task.TYPE"        : BeaconTask.TYPE_VALUE,
                "$Task.STATUS_ENUM" : TaskStatus.CURRENT.name(),
                "$BeaconTask.MINOR" : 456])

        when:
        def task = storyLineOpenHelper.openReadableTaskDatabase().findTaskById(tId);

        then:
        task.getMinor() == 456
    }
}

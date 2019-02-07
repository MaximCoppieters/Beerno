package cz.mendelu.busItWeek.library

import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import org.robospock.RoboSpecification

/**
 * Created by Honza on 10.03.2017.
 */
@Config(manifest=Config.NONE)
class GPSTaskTest extends RoboSpecification {

    def context = RuntimeEnvironment.application
    def storyLineOpenHelper = new DummyStoryLineOpenHelper(context)

    def "GetRadius"() {
        setup:
        def tId = storyLineOpenHelper.task([
                "$Task.TYPE"       : GPSTask.TYPE_VALUE,
                "$Task.STATUS_ENUM": TaskStatus.CURRENT.name(),
                "$GPSTask.RADIUS"  : 3.33])

        when:
        def task = storyLineOpenHelper.openReadableTaskDatabase().findTaskById(tId);

        then:
        task.getRadius() == 3.33
    }
}

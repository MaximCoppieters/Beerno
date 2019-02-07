package cz.mendelu.busItWeek.library

import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import org.robospock.RoboSpecification

/**
 * Created by Honza on 10.03.2017.
 */
@Config(manifest=Config.NONE)
class CodeTaskTest extends RoboSpecification {

    def context = RuntimeEnvironment.application
    def storyLineOpenHelper = new DummyStoryLineOpenHelper(context)

    def "GetQR"() {
        setup:
        def tId = storyLineOpenHelper.task([
                "$Task.TYPE"       : CodeTask.TYPE_VALUE,
                "$Task.STATUS_ENUM": TaskStatus.CURRENT.name(),
                "$CodeTask.QR"     : "qr-code"])

        when:
        def task = storyLineOpenHelper.openReadableTaskDatabase().findTaskById(tId);

        then:
        task.getQR() == "qr-code"
    }

    def "GetEAN"() {
        setup:
        def tId = storyLineOpenHelper.task([
                "$Task.TYPE"        : CodeTask.TYPE_VALUE,
                "$Task.STATUS_ENUM" : TaskStatus.CURRENT.name(),
                "$CodeTask.EAN" : 123456])

        when:
        def task = storyLineOpenHelper.openReadableTaskDatabase().findTaskById(tId);

        then:
        task.getEAN() == 123456
    }
}

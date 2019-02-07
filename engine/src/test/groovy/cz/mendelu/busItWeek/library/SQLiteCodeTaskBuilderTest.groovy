package cz.mendelu.busItWeek.library

import cz.mendelu.busItWeek.library.utils.SQLiteTaskSpecification
import org.robolectric.annotation.Config

/**
 * Created by Honza on 03.03.2017.
 */
@Config(manifest=Config.NONE)
class SQLiteCodeTaskBuilderTest extends SQLiteTaskSpecification {

    def taskBuilder
    def taskContentValues

    def setup() {
        taskBuilder = storyLineBuilder.addCodeTask("CodeTask")
        taskContentValues = taskBuilder.contentValues
    }

    def "CodeTask builder init"() {
        when: doNothing()

        then:
        taskContentValues.get(CodeTask.EAN) == null
        taskContentValues.get(CodeTask.QR) == null
    }

    def "Task builder: set ean"() {
        when:
        taskBuilder.ean(123465)

        then:
        taskContentValues.get(CodeTask.EAN)  == 123465
    }

    def "Task builder: set qr"() {
        when:
        taskBuilder.qr("GQ_CODE")

        then:
        taskContentValues.get(CodeTask.QR)  == "GQ_CODE"
    }
}

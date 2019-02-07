package cz.mendelu.busItWeek.library

import cz.mendelu.busItWeek.library.utils.SQLiteTaskSpecification
import org.robolectric.annotation.Config

/**
 * Created by Honza on 03.03.2017.
 */
@Config(manifest=Config.NONE)
class SQLiteGPSTaskBuilderTest extends SQLiteTaskSpecification {

    def taskBuilder
    def taskContentValues

    def setup() {
        taskBuilder = storyLineBuilder.addGPSTask("GPSTask")
        taskContentValues = taskBuilder.contentValues
    }

    def "GPSTask builder init"() {
        when: doNothing()

        then:
        taskContentValues.get(GPSTask.RADIUS) == null
    }

    def "Task builder: set radius"() {
        when:
        taskBuilder.radius(1.3)

        then:
        taskContentValues.get(GPSTask.RADIUS) == 1.3
    }
}

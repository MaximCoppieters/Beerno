package cz.mendelu.busItWeek.library

import cz.mendelu.busItWeek.library.utils.SQLiteTaskSpecification
import org.robolectric.annotation.Config

/**
 * Created by Honza on 03.03.2017.
 */
@Config(manifest=Config.NONE)
class SQLiteBeaconTaskBuilderTest extends SQLiteTaskSpecification {

    def taskBuilder
    def taskContentValues

    def setup() {
        taskBuilder = storyLineBuilder.addBeaconTask("BeaconTask")
        taskContentValues = taskBuilder.contentValues
    }

    def "BeaconTask builder init"() {
        when: doNothing()

        then:
        taskContentValues.get(BeaconTask.MAJOR) == null
        taskContentValues.get(BeaconTask.MINOR) == null
    }

    def "Task builder: set beacon"() {
        when:
        taskBuilder.beacon(123, 456)

        then:
        taskContentValues.get(BeaconTask.MAJOR) == 123
        taskContentValues.get(BeaconTask.MINOR) == 456
    }
}

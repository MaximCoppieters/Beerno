package cz.mendelu.busItWeek.library

import android.database.sqlite.SQLiteDatabase
import cz.mendelu.busItWeek.library.builder.BeaconTaskBuilder
import cz.mendelu.busItWeek.library.builder.CodeTaskBuilder
import cz.mendelu.busItWeek.library.builder.GPSTaskBuilder
import cz.mendelu.busItWeek.library.builder.NFCTaskBuilder
import org.robolectric.annotation.Config
import org.robospock.RoboSpecification

/**
 * Created by Honza on 03.03.2017.
 */
@Config(manifest=Config.NONE)
class SQLiteStoryLineBuilderTest extends RoboSpecification {

    def database
    def storyLineBuilder

    def setup() {
        database = Mock(SQLiteDatabase)
        storyLineBuilder = new SQLiteStoryLineBuilder(database)
    }

    def "AddGPSTask init"() {
        when:
        def taskBuilder = storyLineBuilder.addGPSTask("GPSTask")

        then:
        taskBuilder instanceof GPSTaskBuilder
        taskBuilder.contentValues.get(Task.TYPE) == GPSTask.TYPE_VALUE
        taskBuilder.contentValues.get(Task.NAME) == "GPSTask"
    }


    def "AddBeaconTask init"() {
        when:
        def taskBuilder = storyLineBuilder.addBeaconTask("BeaconTask")

        then:
        taskBuilder instanceof BeaconTaskBuilder
        taskBuilder.contentValues.get(Task.TYPE) == BeaconTask.TYPE_VALUE
        taskBuilder.contentValues.get(Task.NAME) == "BeaconTask"
    }

    def "AddNFCTask init"() {
        when:
        def taskBuilder = storyLineBuilder.addNFCTask("NFCTask")

        then:
        taskBuilder instanceof NFCTaskBuilder
        taskBuilder.contentValues.get(Task.TYPE) == NFCTask.TYPE_VALUE
        taskBuilder.contentValues.get(Task.NAME) == "NFCTask"
    }

    def "AddCodeTask init"() {
        when:
        def taskBuilder = storyLineBuilder.addCodeTask("CodeTask")
        
        then:
        taskBuilder instanceof CodeTaskBuilder
        taskBuilder.contentValues.get(Task.TYPE) == CodeTask.TYPE_VALUE
        taskBuilder.contentValues.get(Task.NAME) == "CodeTask"
    }
}

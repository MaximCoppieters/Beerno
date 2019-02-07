package cz.mendelu.busItWeek.library

import cz.mendelu.busItWeek.library.utils.SQLiteTaskSpecification
import org.robolectric.annotation.Config

/**
 * Created by Honza on 03.03.2017.
 */
@Config(manifest=Config.NONE)
class SQLiteNFCTaskBuilderTest extends SQLiteTaskSpecification {

    def taskBuilder
    def taskContentValues
    def alternativeBuilder
    def alternativeTaskContentValues;

    def setup() {
        taskBuilder = storyLineBuilder.addNFCTask("NFCTask")
        taskContentValues = taskBuilder.contentValues
        alternativeTaskContentValues = taskBuilder.alternativeTaskContentValues;
        alternativeBuilder = null
    }

    def "NFCTask builder init"() {
        when: doNothing()

        then:
        taskContentValues.get(NFCTask.NFC) == null
        taskContentValues.get(NFCTask.ALTERNATIVE_TASK_ID) == null
    }

    def "Task builder: set nfc code"() {
        setup:
        byte[] data = [1,2,3]

        when:
        taskBuilder.nfc(data)

        then:
        taskContentValues.get(NFCTask.NFC) == data
    }

    def "Task builder: do alternative BeaconTask"() {
        when:
        alternativeBuilder = taskBuilder.alternativeBeaconTask()

        then:
        alternativeBuilder != null
        alternativeTaskContentValues.get(Task.TYPE) == BeaconTask.TYPE_VALUE
        alternativeTaskContentValues.get(Task.STATUS_ENUM) == null
        alternativeTaskContentValues.get(Task.NAME) == null
    }

    def "Task builder: do alternative CodeTask"() {
        when:
        alternativeBuilder = taskBuilder.alternativeCodeTask()

        then:
        alternativeBuilder != null
        alternativeTaskContentValues.get(Task.TYPE) == CodeTask.TYPE_VALUE
        alternativeTaskContentValues.get(Task.STATUS_ENUM) == null
        alternativeTaskContentValues.get(Task.NAME) == null
    }

    def "Task builder: do alternative GPSTask"() {
        when:
        alternativeBuilder = taskBuilder.alternativeGPSTask()

        then:
        alternativeBuilder != null
        alternativeTaskContentValues.get(Task.TYPE) == GPSTask.TYPE_VALUE
        alternativeTaskContentValues.get(Task.STATUS_ENUM) == null
        alternativeTaskContentValues.get(Task.NAME) == null
    }

    def "Task builder: alternative GPSTask done"() {
        setup:
        alternativeBuilder = taskBuilder.alternativeGPSTask()

        when:
        alternativeBuilder.alternativeTaskDone()

        then:
        1 * database.insertOrThrow(Task.TABLE, null, _)
        taskContentValues.get(NFCTask.ALTERNATIVE_TASK_ID) != null
    }

    def "Task builder: alternative CodeTask done"() {
        setup:
        alternativeBuilder = taskBuilder.alternativeCodeTask()

        when:
        alternativeBuilder.alternativeTaskDone()

        then:
        1 * database.insertOrThrow(Task.TABLE, null, _)
        taskContentValues.get(NFCTask.ALTERNATIVE_TASK_ID) != null
    }

    def "Task builder: alternative BeaconTask done"() {
        setup:
        alternativeBuilder = taskBuilder.alternativeBeaconTask()

        when:
        alternativeBuilder.alternativeTaskDone()

        then:
        1 * database.insertOrThrow(Task.TABLE, null, _)
        taskContentValues.get(NFCTask.ALTERNATIVE_TASK_ID) != null
    }

    def "Task builder: alternative share puzzle id"() {
        setup:
        taskBuilder.simplePuzzle().puzzleDone()
        alternativeBuilder = taskBuilder.alternativeBeaconTask()

        when:
        alternativeBuilder.alternativeTaskDone()

        then:
        taskContentValues.get(Task.PUZZLE_ID) == alternativeTaskContentValues.get(Task.PUZZLE_ID)
    }
}

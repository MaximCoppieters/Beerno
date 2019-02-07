package cz.mendelu.busItWeek.library

import cz.mendelu.busItWeek.library.builder.AssignmentPuzzleBuilder
import cz.mendelu.busItWeek.library.builder.TaskBuilder
import cz.mendelu.busItWeek.library.utils.SQLiteTaskSpecification
import org.robolectric.annotation.Config

/**
 * Created by Honza on 03.03.2017.
 */
@Config(manifest=Config.NONE)
class SQLiteAssignmentPuzzleBuilderTest extends SQLiteTaskSpecification {

    TaskBuilder taskBuilder
    AssignmentPuzzleBuilder puzzleBuilder
    def taskContentValues
    def puzzleContentValues

    def setup() {
        taskBuilder = storyLineBuilder.addBeaconTask("BeaconTask")
        taskContentValues = taskBuilder.contentValues
        puzzleBuilder = taskBuilder.assignmentPuzzle()
        puzzleContentValues = puzzleBuilder.contentValues
    }

    def "AssignmentPuzzle builder init"() {
        when: doNothing()

        then:
        puzzleContentValues.get(Puzzle.TYPE) == AssignmentPuzzle.TYPE_VALUE
        puzzleContentValues.get(AssignmentPuzzle.ASSIGNMENT) == null
        puzzleContentValues.get(AssignmentPuzzle.EAN) == null
        puzzleContentValues.get(AssignmentPuzzle.QR_CODE) == null
        puzzleContentValues.get(AssignmentPuzzle.BEACON_MAJOR_CODE) == null
        puzzleContentValues.get(AssignmentPuzzle.BEACON_MINOR_CODE) == null
    }

    def "AssignmentPuzzle: set assignment"() {
        when:
        puzzleBuilder.assignment("assignment")

        then:
        puzzleContentValues.get(AssignmentPuzzle.ASSIGNMENT) == "assignment"
    }

    def "AssignmentPuzzle: set ean"() {
        when:
        puzzleBuilder.ean(123)

        then:
        puzzleContentValues.get(AssignmentPuzzle.EAN) == 123
    }

    def "AssignmentPuzzle: set qrCode"() {
        when:
        puzzleBuilder.qrCode("qrCode")

        then:
        puzzleContentValues.get(AssignmentPuzzle.QR_CODE) == "qrCode"
    }

    def "AssignmentPuzzle: set beacon"() {
        when:
        puzzleBuilder.beacon(456, 798)

        then:
        puzzleContentValues.get(AssignmentPuzzle.BEACON_MAJOR_CODE) == 456
        puzzleContentValues.get(AssignmentPuzzle.BEACON_MINOR_CODE) == 798
    }
}

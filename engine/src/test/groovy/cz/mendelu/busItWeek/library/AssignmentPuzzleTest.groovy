package cz.mendelu.busItWeek.library

import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import org.robospock.RoboSpecification

/**
 * Created by Honza on 10.03.2017.
 */
@Config(manifest=Config.NONE)
class AssignmentPuzzleTest extends RoboSpecification {

    def context = RuntimeEnvironment.application
    def storyLineOpenHelper = new DummyStoryLineOpenHelper(context)


    def "GetAssignment"() {
        setup:
        def pId = storyLineOpenHelper.puzzle([
                "$Puzzle.TYPE"                : AssignmentPuzzle.TYPE_VALUE,
                "$AssignmentPuzzle.ASSIGNMENT": "Puzzle assignment"
        ])

        when:
        AssignmentPuzzle puzzle = storyLineOpenHelper.openReadableTaskDatabase().findPuzzleById(pId);

        then:
        puzzle.getAssignment() == "Puzzle assignment"
    }

    def "GetQrCode"() {
        setup:
        def pId = storyLineOpenHelper.puzzle([
                "$Puzzle.TYPE" : AssignmentPuzzle.TYPE_VALUE,
                "$AssignmentPuzzle.QR_CODE" : "QR code"
        ])

        when:
        AssignmentPuzzle puzzle = storyLineOpenHelper.openReadableTaskDatabase().findPuzzleById(pId);

        then:
        puzzle.getQrCode() == "QR code"
    }

    def "GetEan"() {
        setup:
        def pId = storyLineOpenHelper.puzzle([
                "$Puzzle.TYPE" : AssignmentPuzzle.TYPE_VALUE,
                "$AssignmentPuzzle.EAN" : 123456789
        ])

        when:
        AssignmentPuzzle puzzle = storyLineOpenHelper.openReadableTaskDatabase().findPuzzleById(pId);

        then:
        puzzle.getEan() == 123456789
    }

    def "GetBeaconMajorCode"() {
        setup:
        def pId = storyLineOpenHelper.puzzle([
                "$Puzzle.TYPE" : AssignmentPuzzle.TYPE_VALUE,
                "$AssignmentPuzzle.BEACON_MAJOR_CODE" : 1234
        ])

        when:
        AssignmentPuzzle puzzle = storyLineOpenHelper.openReadableTaskDatabase().findPuzzleById(pId);

        then:
        puzzle.getBeaconMajorCode() == 1234
    }

    def "GetBeaconMinorCode"() {
        setup:
        def pId = storyLineOpenHelper.puzzle([
                "$Puzzle.TYPE" : AssignmentPuzzle.TYPE_VALUE,
                "$AssignmentPuzzle.BEACON_MINOR_CODE" : 5678
        ])

        when:
        AssignmentPuzzle puzzle = storyLineOpenHelper.openReadableTaskDatabase().findPuzzleById(pId);

        then:
        puzzle.getBeaconMinorCode() == 5678
    }
}

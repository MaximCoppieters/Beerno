package cz.mendelu.busItWeek.library

import cz.mendelu.busItWeek.library.builder.SimplePuzzleBuilder
import cz.mendelu.busItWeek.library.builder.TaskBuilder
import cz.mendelu.busItWeek.library.utils.SQLiteTaskSpecification
import org.robolectric.annotation.Config

/**
 * Created by Honza on 03.03.2017.
 */
@Config(manifest=Config.NONE)
class SQLitePuzzleBuilderTest extends SQLiteTaskSpecification {

    TaskBuilder taskBuilder
    SimplePuzzleBuilder puzzleBuilder
    def taskContentValues
    def puzzleContentValues

    def setup() {
        taskBuilder = storyLineBuilder.addBeaconTask("BeaconTask")
        taskContentValues = taskBuilder.contentValues
        puzzleBuilder = taskBuilder.simplePuzzle()
        puzzleContentValues = puzzleBuilder.contentValues
    }

    def "Puzzle builder init"() {
        when: doNothing()

        then:
        puzzleContentValues.get(Puzzle.HINT) == null
        puzzleContentValues.get(Puzzle.PUZZLE_TIME) == null
    }

    def "AssignmentPuzzle: set hint"() {
        when:
        puzzleBuilder.hint("hint")

        then:
        puzzleContentValues.get(Puzzle.HINT) == "hint"
    }

    def "AssignmentPuzzle: set puzzleTime"() {
        when:
        puzzleBuilder.puzzleTime(123)

        then:
        puzzleContentValues.get(Puzzle.PUZZLE_TIME) == 123
    }
}

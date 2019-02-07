package cz.mendelu.busItWeek.library

import cz.mendelu.busItWeek.library.builder.SimplePuzzleBuilder
import cz.mendelu.busItWeek.library.builder.TaskBuilder
import cz.mendelu.busItWeek.library.utils.SQLiteTaskSpecification
import org.robolectric.annotation.Config

/**
 * Created by Honza on 03.03.2017.
 */
@Config(manifest=Config.NONE)
class SQLiteSimplePuzzleBuilderTest extends SQLiteTaskSpecification {

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

    def "SimplePuzzle builder init"() {
        when: doNothing()

        then:
        puzzleContentValues.get(Puzzle.TYPE) == SimplePuzzle.TYPE_VALUE
        puzzleContentValues.get(SimplePuzzle.QUESTION) == null
        puzzleContentValues.get(SimplePuzzle.ANSWER) == null
    }

    def "SimplePuzzle: set question"() {
        when:
        puzzleBuilder.question("question")

        then:
        puzzleContentValues.get(SimplePuzzle.QUESTION) == "question"
    }

    def "SimplePuzzle: set answer"() {
        when:
        puzzleBuilder.answer("answer")

        then:
        puzzleContentValues.get(SimplePuzzle.ANSWER) == "answer"
    }
}

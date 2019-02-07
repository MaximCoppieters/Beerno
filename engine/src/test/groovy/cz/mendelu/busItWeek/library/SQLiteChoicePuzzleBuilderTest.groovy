package cz.mendelu.busItWeek.library

import cz.mendelu.busItWeek.library.builder.ChoicePuzzleBuilder
import cz.mendelu.busItWeek.library.builder.TaskBuilder
import cz.mendelu.busItWeek.library.utils.SQLiteTaskSpecification
import org.robolectric.annotation.Config

/**
 * Created by Honza on 03.03.2017.
 */
@Config(manifest=Config.NONE)
class SQLiteChoicePuzzleBuilderTest extends SQLiteTaskSpecification {

    TaskBuilder taskBuilder
    ChoicePuzzleBuilder puzzleBuilder
    def taskContentValues
    def puzzleContentValues
    def choicesContentValues

    def setup() {
        taskBuilder = storyLineBuilder.addBeaconTask("BeaconTask")
        taskContentValues = taskBuilder.contentValues
        puzzleBuilder = taskBuilder.choicePuzzle()
        puzzleContentValues = puzzleBuilder.contentValues
        choicesContentValues = puzzleBuilder.choicesContentValues
    }

    def "ChoicePuzzle builder init"() {
        when: doNothing()

        then:
        puzzleContentValues.get(Puzzle.TYPE) == ChoicePuzzle.TYPE_VALUE
        puzzleContentValues.get(ChoicePuzzle.QUESTION) == null
        choicesContentValues.size == 0
    }

    def "ChoicePuzzle builder: set question"() {
        when:
        puzzleBuilder.question("question")

        then:
        puzzleContentValues.get(ChoicePuzzle.QUESTION) == "question"
    }

    def "ChoicePuzzle builder: add choices"() {
        when:
        puzzleBuilder.addChoice("choice 1", true)
        puzzleBuilder.addChoice("choice 2", false)

        then:
        choicesContentValues.size == 2

        and:
        choicesContentValues[0].get(ChoicePuzzle.CHOICES_PUZZLE_ID) == null
        choicesContentValues[0].get(ChoicePuzzle.CHOICES_ANSWER) == "choice 1"
        choicesContentValues[0].get(ChoicePuzzle.CHOICES_CORRECT) == true

        and:
        choicesContentValues[1].get(ChoicePuzzle.CHOICES_PUZZLE_ID) == null
        choicesContentValues[1].get(ChoicePuzzle.CHOICES_ANSWER) == "choice 2"
        choicesContentValues[1].get(ChoicePuzzle.CHOICES_CORRECT) == false
    }

    def "ChoicePuzzle builder: after task done set correct task_id"() {
        setup:
        puzzleBuilder.addChoice("choice", true)

        when:
        puzzleBuilder.puzzleDone().taskDone()

        then:
        choicesContentValues[0].get(ChoicePuzzle.CHOICES_PUZZLE_ID) == 0
    }
}

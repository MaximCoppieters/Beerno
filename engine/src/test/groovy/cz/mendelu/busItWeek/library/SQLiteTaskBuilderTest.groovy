package cz.mendelu.busItWeek.library

import cz.mendelu.busItWeek.library.builder.TaskBuilder
import cz.mendelu.busItWeek.library.utils.SQLiteTaskSpecification
import org.robolectric.annotation.Config

/**
 * Created by Honza on 03.03.2017.
 */
@Config(manifest=Config.NONE)
class SQLiteTaskBuilderTest extends SQLiteTaskSpecification {

    TaskBuilder taskBuilder
    def taskContentValues

    def setup() {
        taskBuilder = storyLineBuilder.addGPSTask("BasicTask")
        taskContentValues = taskBuilder.contentValues
    }

    def "Task builder init"() {
        when: doNothing()

        then:
        taskContentValues.get(Task.NAME) == "BasicTask"
        taskContentValues.get(Task.VICTORY_POINTS) == 0
        taskContentValues.get(Task.STATUS_ENUM) == TaskStatus.CURRENT.name()
        taskContentValues.get(Task.PUZZLE_ID) == null
        taskContentValues.get(Task.HINT) == null
        taskContentValues.get(Task.LATITUDE)  == null
        taskContentValues.get(Task.LONGITUDE) == null
    }

    def "Task builder: set victoryPoints"() {
        when:
        taskBuilder.victoryPoints(1)

        then:
        taskContentValues.get(Task.VICTORY_POINTS) == 1
    }

    def "Task builder: set hint"() {
        when:
        taskBuilder.hint("Hint.")

        then:
        taskContentValues.get(Task.HINT) == "Hint."
    }


    def "Task builder: set location"() {
        when:
        taskBuilder.location(1.1, 1.2)

        then:
        taskContentValues.get(Task.LATITUDE)  == 1.1
        taskContentValues.get(Task.LONGITUDE) == 1.2
    }

    def "Done task: test correct store to db"() {
        when:
        taskBuilder.taskDone();

        then:
        1 * database.insertOrThrow(Task.TABLE, null, _)
    }

    def "Create assignment puzzle: test correct store to db"() {
        when:
        taskBuilder.assignmentPuzzle().puzzleDone()

        then:
        1 * database.insertOrThrow(Puzzle.TABLE, null, _)
        taskContentValues.get(Task.PUZZLE_ID) != null
    }

    def "Create choice puzzle: test correct store to db"() {
        when:
        taskBuilder.choicePuzzle().puzzleDone()

        then:
        1 * database.insertOrThrow(Puzzle.TABLE, null, _)
        taskContentValues.get(Task.PUZZLE_ID) != null
    }

    def "Create image select puzzle: test correct store to db"() {
        when:
        taskBuilder.imageSelectPuzzle().puzzleDone()

        then:
        1 * database.insertOrThrow(Puzzle.TABLE, null, _)
        taskContentValues.get(Task.PUZZLE_ID) != null
    }

    def "Create simple puzzle: test correct store to db"() {
        when:
        taskBuilder.simplePuzzle().puzzleDone()

        then:
        1 * database.insertOrThrow(Puzzle.TABLE, null, _)
        taskContentValues.get(Task.PUZZLE_ID) != null
    }


}

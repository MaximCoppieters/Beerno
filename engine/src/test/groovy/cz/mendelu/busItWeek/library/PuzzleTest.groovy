package cz.mendelu.busItWeek.library

import android.content.ContentValues
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import org.robospock.RoboSpecification

/**
 * Created by Honza on 10.03.2017.
 */
@Config(manifest=Config.NONE)
class PuzzleTest extends RoboSpecification {

    def context = RuntimeEnvironment.application
    def storyLineOpenHelper = new DummyStoryLineOpenHelper(context)

    def "Load AssignmentPuzzle"() {
        setup:
        def pId = storyLineOpenHelper.puzzle([
                "$Puzzle.TYPE": AssignmentPuzzle.TYPE_VALUE])

        when:
        def puzzle = storyLineOpenHelper.openReadableTaskDatabase().findPuzzleById(pId);

        then:
        puzzle instanceof AssignmentPuzzle
    }

    def "Load ChoicePuzzle"() {
        setup:
        def pId = storyLineOpenHelper.puzzle([
                "$Puzzle.TYPE": ChoicePuzzle.TYPE_VALUE])

        when:
        def puzzle = storyLineOpenHelper.openReadableTaskDatabase().findPuzzleById(pId);

        then:
        puzzle instanceof ChoicePuzzle
    }

    def "Load ImageSelectPuzzle"() {
        setup:
        def pId = storyLineOpenHelper.puzzle([
                "$Puzzle.TYPE": ImageSelectPuzzle.TYPE_VALUE])

        when:
        def puzzle = storyLineOpenHelper.openReadableTaskDatabase().findPuzzleById(pId);

        then:
        puzzle instanceof ImageSelectPuzzle
    }

    def "Load SimplePuzzle"() {
        setup:
        def pId = storyLineOpenHelper.puzzle([
                "$Puzzle.TYPE": SimplePuzzle.TYPE_VALUE])

        when:
        def puzzle = storyLineOpenHelper.openReadableTaskDatabase().findPuzzleById(pId);

        then:
        puzzle instanceof SimplePuzzle
    }

    def "Load Illegal type"() {
        setup:
        def contentValues = new ContentValues()
            contentValues.put(Puzzle.TYPE, -1)

        when:
        Puzzle.load(null, contentValues)

        then:
        thrown(StoryLineRuntimeException.class)
    }

    def "GetPuzzleTime"() {
        setup:
        def pId = storyLineOpenHelper.puzzle([
                "$Puzzle.TYPE"        : SimplePuzzle.TYPE_VALUE,
                "$Puzzle.PUZZLE_TIME" : 123
        ])

        when:
        Puzzle puzzle = storyLineOpenHelper.openReadableTaskDatabase().findPuzzleById(pId);

        then:
        puzzle.getPuzzleTime() == 123
    }

    def "GetHint"() {
        setup:
        def pId = storyLineOpenHelper.puzzle([
                "$Puzzle.TYPE" : SimplePuzzle.TYPE_VALUE,
                "$Puzzle.HINT" : "Puzzle hint"
        ])

        when:
        def puzzle = storyLineOpenHelper.openReadableTaskDatabase().findPuzzleById(pId);

        then:
        puzzle.getHint() == "Puzzle hint"
    }
}

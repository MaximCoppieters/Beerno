package cz.mendelu.busItWeek.library

import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import org.robospock.RoboSpecification

/**
 * Created by Honza on 10.03.2017.
 */
@Config(manifest=Config.NONE)
class SimplePuzzleTest extends RoboSpecification {

    def context = RuntimeEnvironment.application
    def storyLineOpenHelper = new DummyStoryLineOpenHelper(context)

    def "GetQuestion"() {
        setup:
        def pId = storyLineOpenHelper.puzzle([
                "$Puzzle.TYPE"          : SimplePuzzle.TYPE_VALUE,
                "$SimplePuzzle.QUESTION": "Puzzle question"
        ])

        when:
        SimplePuzzle puzzle = storyLineOpenHelper.openReadableTaskDatabase().findPuzzleById(pId);

        then:
        puzzle.getQuestion() == "Puzzle question"
    }

    def "GetAnswer"() {
        setup:
        def pId = storyLineOpenHelper.puzzle([
                "$Puzzle.TYPE" : SimplePuzzle.TYPE_VALUE,
                "$SimplePuzzle.ANSWER" : "Puzzle answer"
        ])

        when:
        SimplePuzzle puzzle = storyLineOpenHelper.openReadableTaskDatabase().findPuzzleById(pId);

        then:
        puzzle.getAnswer() == "Puzzle answer"
    }
}

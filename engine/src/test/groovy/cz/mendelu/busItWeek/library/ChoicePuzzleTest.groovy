package cz.mendelu.busItWeek.library

import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import org.robospock.RoboSpecification

/**
 * Created by Honza on 10.03.2017.
 */
@Config(manifest=Config.NONE)
class ChoicePuzzleTest extends RoboSpecification {

    def context = RuntimeEnvironment.application
    def storyLineOpenHelper = new DummyStoryLineOpenHelper(context)

    def "GetQuestion"() {
        setup:
        def pId = storyLineOpenHelper.puzzle([
                "$Puzzle.TYPE"          : ChoicePuzzle.TYPE_VALUE,
                "$ChoicePuzzle.QUESTION": "Puzzle question"
        ])

        when:
        ChoicePuzzle puzzle = storyLineOpenHelper.openReadableTaskDatabase().findPuzzleById(pId);

        then:
        puzzle.getQuestion() == "Puzzle question"
    }

    def "GetChoices"() {
        setup:
        def pId = storyLineOpenHelper.puzzle([
                "$Puzzle.TYPE" : ChoicePuzzle.TYPE_VALUE
        ])
        storyLineOpenHelper.choice([
                "$ChoicePuzzle.CHOICES_PUZZLE_ID" : pId,
                "$ChoicePuzzle.CHOICES_ANSWER"    : "choice answer 1",
                "$ChoicePuzzle.CHOICES_CORRECT"   : true
        ])
        storyLineOpenHelper.choice([
                "$ChoicePuzzle.CHOICES_PUZZLE_ID" : pId,
                "$ChoicePuzzle.CHOICES_ANSWER"    : "choice answer 2",
                "$ChoicePuzzle.CHOICES_CORRECT"   : false
        ])

        when:
        ChoicePuzzle puzzle = storyLineOpenHelper.openReadableTaskDatabase().findPuzzleById(pId);

        then:
        puzzle.getChoices().size() == 2;
        puzzle.getChoices()['choice answer 1'] == true
        puzzle.getChoices()['choice answer 2'] == false
    }

    def "GetChoices answer by order"() {
        setup:
        def pId = storyLineOpenHelper.puzzle([
                "$Puzzle.TYPE" : ChoicePuzzle.TYPE_VALUE
        ])
        storyLineOpenHelper.choice([
                "$ChoicePuzzle.CHOICES_PUZZLE_ID" : pId,
                "$ChoicePuzzle.CHOICES_ANSWER"    : "choice answer 1",
                "$ChoicePuzzle.CHOICES_CORRECT"   : true
        ])
        storyLineOpenHelper.choice([
                "$ChoicePuzzle.CHOICES_PUZZLE_ID" : pId,
                "$ChoicePuzzle.CHOICES_ANSWER"    : "choice answer 2",
                "$ChoicePuzzle.CHOICES_CORRECT"   : false
        ])

        when:
        ChoicePuzzle puzzle = storyLineOpenHelper.openReadableTaskDatabase().findPuzzleById(pId);

        then:
        puzzle.getAnswerForChoice(0) == true
        puzzle.getAnswerForChoice(1) == false
    }
}

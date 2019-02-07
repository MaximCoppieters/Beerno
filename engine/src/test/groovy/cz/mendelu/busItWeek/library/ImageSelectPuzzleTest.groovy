package cz.mendelu.busItWeek.library

import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import org.robospock.RoboSpecification

/**
 * Created by Honza on 10.03.2017.
 */
@Config(manifest=Config.NONE)
class ImageSelectPuzzleTest extends RoboSpecification {

    def context = RuntimeEnvironment.application
    def storyLineOpenHelper = new DummyStoryLineOpenHelper(context)

    def "GetQuestion"() {
        setup:
        def pId = storyLineOpenHelper.puzzle([
                "$Puzzle.TYPE"               : ImageSelectPuzzle.TYPE_VALUE,
                "$ImageSelectPuzzle.QUESTION": "Puzzle question"
        ])

        when:
        ImageSelectPuzzle puzzle = storyLineOpenHelper.openReadableTaskDatabase().findPuzzleById(pId);

        then:
        puzzle.getQuestion() == "Puzzle question"
    }

    def "GetImages"() {
        setup:
        def pId = storyLineOpenHelper.puzzle([
                "$Puzzle.TYPE" : ImageSelectPuzzle.TYPE_VALUE
        ])
        storyLineOpenHelper.image([
                "$ImageSelectPuzzle.IMAGES_PUZZLE_ID" : pId,
                "$ImageSelectPuzzle.IMAGES_RESOURCE"  : 123,
                "$ImageSelectPuzzle.IMAGES_CORRECT"   : true
        ])
        storyLineOpenHelper.image([
                "$ImageSelectPuzzle.IMAGES_PUZZLE_ID" : pId,
                "$ImageSelectPuzzle.IMAGES_RESOURCE"  : 456,
                "$ImageSelectPuzzle.IMAGES_CORRECT"   : false
        ])

        when:
        ImageSelectPuzzle puzzle = storyLineOpenHelper.openReadableTaskDatabase().findPuzzleById(pId);

        then:
        puzzle.getImages().size() == 2;
        puzzle.getImages()[123] == true
        puzzle.getImages()[456] == false
    }

    def "GetImages answer by order"() {
        setup:
        def pId = storyLineOpenHelper.puzzle([
                "$Puzzle.TYPE" : ImageSelectPuzzle.TYPE_VALUE
        ])
        storyLineOpenHelper.image([
                "$ImageSelectPuzzle.IMAGES_PUZZLE_ID" : pId,
                "$ImageSelectPuzzle.IMAGES_RESOURCE"  : 123,
                "$ImageSelectPuzzle.IMAGES_CORRECT"   : true
        ])
        storyLineOpenHelper.image([
                "$ImageSelectPuzzle.IMAGES_PUZZLE_ID" : pId,
                "$ImageSelectPuzzle.IMAGES_RESOURCE"  : 456,
                "$ImageSelectPuzzle.IMAGES_CORRECT"   : false
        ])

        when:
        ImageSelectPuzzle puzzle = storyLineOpenHelper.openReadableTaskDatabase().findPuzzleById(pId);

        then:
        puzzle.getAnswerForImage(0) == true
        puzzle.getAnswerForImage(1) == false
    }
}

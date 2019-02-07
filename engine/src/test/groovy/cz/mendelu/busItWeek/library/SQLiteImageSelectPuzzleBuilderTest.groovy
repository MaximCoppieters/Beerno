package cz.mendelu.busItWeek.library

import cz.mendelu.busItWeek.library.builder.ImageSelectPuzzleBuilder
import cz.mendelu.busItWeek.library.builder.TaskBuilder
import cz.mendelu.busItWeek.library.utils.SQLiteTaskSpecification
import org.robolectric.annotation.Config

/**
 * Created by Honza on 03.03.2017.
 */
@Config(manifest=Config.NONE)
class SQLiteImageSelectPuzzleBuilderTest extends SQLiteTaskSpecification {

    TaskBuilder taskBuilder
    ImageSelectPuzzleBuilder puzzleBuilder
    def taskContentValues
    def puzzleContentValues
    def imagesContentValues

    def setup() {
        taskBuilder = storyLineBuilder.addBeaconTask("BeaconTask")
        taskContentValues = taskBuilder.contentValues
        puzzleBuilder = taskBuilder.imageSelectPuzzle()
        puzzleContentValues = puzzleBuilder.contentValues
        imagesContentValues = puzzleBuilder.imagesContentValues
    }

    def "ImageSelectPuzzle builder init"() {
        when: doNothing()

        then:
        puzzleContentValues.get(Puzzle.TYPE) == ImageSelectPuzzle.TYPE_VALUE
        puzzleContentValues.get(ImageSelectPuzzle.QUESTION) == null
        imagesContentValues.size == 0
    }

    def "ImageSelectPuzzle builder: set question"() {
        when:
        puzzleBuilder.question("question")

        then:
        puzzleContentValues.get(ImageSelectPuzzle.QUESTION) == "question"
    }

    def "ImageSelectPuzzle builder: add image"() {
        when:
        puzzleBuilder.addImage(1, true)
        puzzleBuilder.addImage(2, false)

        then:
        imagesContentValues.size == 2

        and:
        imagesContentValues[0].get(ImageSelectPuzzle.IMAGES_PUZZLE_ID) == null
        imagesContentValues[0].get(ImageSelectPuzzle.IMAGES_RESOURCE) == 1
        imagesContentValues[0].get(ImageSelectPuzzle.IMAGES_CORRECT) == true

        and:
        imagesContentValues[1].get(ImageSelectPuzzle.IMAGES_PUZZLE_ID) == null
        imagesContentValues[1].get(ImageSelectPuzzle.IMAGES_RESOURCE) == 2
        imagesContentValues[1].get(ImageSelectPuzzle.IMAGES_CORRECT) == false
    }

    def "ImageSelectPuzzle builder: after task done set correct task_id"() {
        setup:
        puzzleBuilder.addImage(1, true)

        when:
        puzzleBuilder.puzzleDone().taskDone()

        then:
        imagesContentValues[0].get(ImageSelectPuzzle.IMAGES_PUZZLE_ID) == 0
    }
}

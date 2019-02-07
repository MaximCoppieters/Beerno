package cz.mendelu.busItWeek.library.builder;

/**
 * Created by Honza on 03.03.2017.
 */

public interface TaskBuilder<T> {

    StoryLineBuilder taskDone();

    T victoryPoints(int victoryPoints);

    T hint(String hint);

    T location(double latitude, double longitude);

    AssignmentPuzzleBuilder<T> assignmentPuzzle();

    ChoicePuzzleBuilder<T> choicePuzzle();

    ImageSelectPuzzleBuilder<T> imageSelectPuzzle();

    SimplePuzzleBuilder<T> simplePuzzle();

}

package cz.mendelu.busItWeek.library.builder;

/**
 * Created by Honza on 07.03.2017.
 */

public interface ChoicePuzzleBuilder<T> extends PuzzleBuilder<ChoicePuzzleBuilder, T> {

    ChoicePuzzleBuilder<T> puzzleTime(long time);

    ChoicePuzzleBuilder<T> hint(String hint);

    ChoicePuzzleBuilder<T> question(String question);

    ChoicePuzzleBuilder<T> addChoice(String answer, boolean correct);

}

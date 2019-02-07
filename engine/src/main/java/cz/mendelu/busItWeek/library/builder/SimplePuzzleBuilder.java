package cz.mendelu.busItWeek.library.builder;

/**
 * Created by Honza on 07.03.2017.
 */

public interface SimplePuzzleBuilder<T> extends PuzzleBuilder<SimplePuzzleBuilder, T> {

    SimplePuzzleBuilder<T> puzzleTime(long time);

    SimplePuzzleBuilder<T> hint(String hint);

    SimplePuzzleBuilder<T> question(String question);

    SimplePuzzleBuilder<T> answer(String answer);

}

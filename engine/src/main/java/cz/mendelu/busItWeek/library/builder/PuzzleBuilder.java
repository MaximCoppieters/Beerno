package cz.mendelu.busItWeek.library.builder;

/**
 * Created by Honza on 07.03.2017.
 */

public interface PuzzleBuilder<P, T> {

    P puzzleTime(long time);

    P hint(String hint);

    T puzzleDone();
}

package cz.mendelu.busItWeek.library.builder;

/**
 * Created by Honza on 07.03.2017.
 */

public interface AssignmentPuzzleBuilder<T> extends PuzzleBuilder<AssignmentPuzzleBuilder, T> {

    AssignmentPuzzleBuilder<T> puzzleTime(long time);

    AssignmentPuzzleBuilder<T> hint(String hint);

    AssignmentPuzzleBuilder<T> assignment(String assignment);

    AssignmentPuzzleBuilder<T> qrCode(String qrCode);

    AssignmentPuzzleBuilder<T> ean(int ean);

    AssignmentPuzzleBuilder<T> beacon(int major, int minor);
}

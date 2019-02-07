package cz.mendelu.busItWeek.library.builder;

/**
 * Created by Honza on 07.03.2017.
 */

public interface ImageSelectPuzzleBuilder<T> extends PuzzleBuilder<ImageSelectPuzzleBuilder, T> {

    ImageSelectPuzzleBuilder<T> puzzleTime(long time);

    ImageSelectPuzzleBuilder<T> hint(String hint);

    ImageSelectPuzzleBuilder<T> question(String question);

    ImageSelectPuzzleBuilder<T> addImage(int resourceId, boolean correct);

}

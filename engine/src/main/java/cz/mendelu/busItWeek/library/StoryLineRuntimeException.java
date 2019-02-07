package cz.mendelu.busItWeek.library;

/**
 * Created by Honza on 02.03.2017.
 */

public class StoryLineRuntimeException extends RuntimeException {

    public StoryLineRuntimeException(String message) {
        super(message);
    }

    public StoryLineRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

}

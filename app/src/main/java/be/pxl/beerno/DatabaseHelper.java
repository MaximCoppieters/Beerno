package be.pxl.beerno;

import cz.mendelu.busItWeek.library.StoryLineDatabaseHelper;
import cz.mendelu.busItWeek.library.builder.StoryLineBuilder;

public class DatabaseHelper extends StoryLineDatabaseHelper {
    public DatabaseHelper() {
        super(1);
    }

    @Override
    protected void onCreate(StoryLineBuilder builder) {
        builder.addGPSTask("1")
                .location(49.188452, 16.591402)
                .radius(100)
                .victoryPoints(10)
                .simplePuzzle()
                .answer("Welcome to the pub")
                .puzzleDone()
                .taskDone();
    }
}

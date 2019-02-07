package be.pxl.beerno;

import cz.mendelu.busItWeek.library.StoryLineDatabaseHelper;
import cz.mendelu.busItWeek.library.builder.StoryLineBuilder;

public class DatabaseHelper extends StoryLineDatabaseHelper {
    public DatabaseHelper() {
        super(28);
    }

    @Override
    protected void onCreate(StoryLineBuilder builder) {

        builder.addGPSTask("1")
                .location(49.209742, 16.614986)
                .radius(100)
                .victoryPoints(10)
                .taskDone();

        builder.addGPSTask("2")
                .location(49.210006, 16.614782)
                .radius(100)
                .victoryPoints(10)
                .taskDone();

        builder.addGPSTask("3")
                .location(49.209782, 16.614963)
                .radius(100)
                .victoryPoints(10)
                .taskDone();
    }
}

package be.pxl.beerno;

import cz.mendelu.busItWeek.library.StoryLineDatabaseHelper;
import cz.mendelu.busItWeek.library.builder.StoryLineBuilder;

public class DatabaseHelper extends StoryLineDatabaseHelper {
    public DatabaseHelper() {
        super(2);
    }

    @Override
    protected void onCreate(StoryLineBuilder builder) {
        builder.addGPSTask("0")
                .location(49.188452, 16.591402)
                .radius(100)
                .victoryPoints(10)
                .taskDone();

        builder.addGPSTask("1")
                .location(49.209742, 16.614986)
                .radius(100)
                .victoryPoints(10)
                .taskDone();

        builder.addGPSTask("2")
                .location(49.219742, 16.624986)
                .radius(100)
                .victoryPoints(10)
                .taskDone();

        builder.addGPSTask("3")
                .location(49.239742, 16.644986)
                .radius(100)
                .victoryPoints(10)
                .taskDone();
    }
}

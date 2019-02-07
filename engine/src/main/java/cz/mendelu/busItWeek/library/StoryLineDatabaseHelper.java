package cz.mendelu.busItWeek.library;

import cz.mendelu.busItWeek.library.builder.StoryLineBuilder;

/**
 * Created by Honza on 01.03.2017.
 */

public abstract class StoryLineDatabaseHelper{

    private final int version;

    public StoryLineDatabaseHelper(int version) {
        this.version = version;
    }

    protected abstract void onCreate(StoryLineBuilder builder);

    int getVersion() {
        return version;
    }
}

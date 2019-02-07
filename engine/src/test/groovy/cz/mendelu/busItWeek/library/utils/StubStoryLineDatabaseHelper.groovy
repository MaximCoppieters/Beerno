package cz.mendelu.busItWeek.library.utils

import cz.mendelu.busItWeek.library.StoryLineDatabaseHelper
import cz.mendelu.busItWeek.library.builder.StoryLineBuilder

/**
 * Created by Honza on 01.03.2017.
 */
class StubStoryLineDatabaseHelper extends StoryLineDatabaseHelper {

    protected static int STUB_VERSION = 10


    public StubStoryLineDatabaseHelper() {
        super(STUB_VERSION)
    }

    @Override
    protected void onCreate(StoryLineBuilder builder) {

    }
}

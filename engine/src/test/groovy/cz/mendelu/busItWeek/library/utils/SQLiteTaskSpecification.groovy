package cz.mendelu.busItWeek.library.utils

import android.database.sqlite.SQLiteDatabase
import cz.mendelu.busItWeek.library.SQLiteStoryLineBuilder
import org.robolectric.annotation.Config
import org.robospock.RoboSpecification

/**
 * Created by Honza on 03.03.2017.
 */
@Config(manifest=Config.NONE)
class SQLiteTaskSpecification extends RoboSpecification {

    def database
    def storyLineBuilder

    def setup() {
        database = Mock(SQLiteDatabase)
        storyLineBuilder = new SQLiteStoryLineBuilder(database)
    }

    def doNothing() {}
}

package cz.mendelu.busItWeek.library;

/**
 * Created by Honza on 10.03.2017.
 */

interface TaskDatabase {

    ReadableTaskDatabase openReadableTaskDatabase();

    WritableTaskDatabase beginTaskDatabaseTransaction();

}

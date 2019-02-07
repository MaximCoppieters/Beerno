package cz.mendelu.busItWeek.library.builder;

/**
 * Created by Honza on 06.03.2017.
 */

public interface AlternativeTaskBuilder {

    NFCTaskBuilder alternativeTaskDone();

    interface IsForBeaconTask extends BeaconTaskBuilder, AlternativeTaskBuilder {

    }

    interface IsForCodeTask extends CodeTaskBuilder, AlternativeTaskBuilder {

    }

    interface IsForGPSTask extends GPSTaskBuilder, AlternativeTaskBuilder {

    }

}

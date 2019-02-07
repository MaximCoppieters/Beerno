package cz.mendelu.busItWeek.library.builder;

/**
 * Created by Honza on 01.03.2017.
 */
public interface StoryLineBuilder {

    BeaconTaskBuilder addBeaconTask(String name);

    CodeTaskBuilder addCodeTask(String name);

    GPSTaskBuilder addGPSTask(String name);

    NFCTaskBuilder addNFCTask(String name);
}

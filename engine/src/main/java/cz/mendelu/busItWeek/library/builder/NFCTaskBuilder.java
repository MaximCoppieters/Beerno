package cz.mendelu.busItWeek.library.builder;


/**
 * Created by Honza on 03.03.2017.
 */
public interface NFCTaskBuilder extends TaskBuilder<NFCTaskBuilder> {

    NFCTaskBuilder nfc(byte[] fnc);

    BeaconTaskBuilder alternativeBeaconTask();

    CodeTaskBuilder alternativeCodeTask();

    GPSTaskBuilder alternativeGPSTask();
}
package cz.mendelu.busItWeek.library.builder;

/**
 * Created by Honza on 03.03.2017.
 */

public interface BeaconTaskBuilder extends TaskBuilder<BeaconTaskBuilder> {

    BeaconTaskBuilder beacon(int major, int minor);
}

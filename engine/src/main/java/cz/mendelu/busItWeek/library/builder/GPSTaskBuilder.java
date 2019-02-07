package cz.mendelu.busItWeek.library.builder;

/**
 * Created by Honza on 03.03.2017.
 */

public interface GPSTaskBuilder extends TaskBuilder<GPSTaskBuilder> {
    
    GPSTaskBuilder radius(double radius);
}

package cz.mendelu.busItWeek.library.builder;

/**
 * Created by Honza on 03.03.2017.
 */

public interface CodeTaskBuilder extends TaskBuilder<CodeTaskBuilder> {

    CodeTaskBuilder ean(int ean);

    CodeTaskBuilder qr(String qr);
}

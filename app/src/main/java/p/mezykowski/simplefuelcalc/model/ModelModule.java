package p.mezykowski.simplefuelcalc.model;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import p.mezykowski.simplefuelcalc.dataAccess.saveLoad.DatabaseManager;
import p.mezykowski.simplefuelcalc.dataAccess.saveLoad.IDatabaseManager;
import p.mezykowski.simplefuelcalc.model.consumption.ConsumptionModel;

/**
 * Created by pawel on 2014-08-22.
 */
@Module(
        library = true,
        complete = false
)
public class ModelModule {

    @Provides @Singleton
    ConsumptionModel providesConsumptionModel() {
        return new ConsumptionModel();
    }
}

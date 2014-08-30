package p.mezykowski.simplefuelcalc.businessLogic;

import com.squareup.otto.Bus;

import dagger.Module;
import dagger.Provides;
import p.mezykowski.simplefuelcalc.businessLogic.calculation.ConsumptionCalculator;
import p.mezykowski.simplefuelcalc.businessLogic.calculation.IConsumptionCalculator;
import p.mezykowski.simplefuelcalc.businessLogic.history.HistoryKeeper;
import p.mezykowski.simplefuelcalc.businessLogic.history.IHistoryKeeper;
import p.mezykowski.simplefuelcalc.dataAccess.saveLoad.IDatabaseManager;
import p.mezykowski.simplefuelcalc.model.consumption.ConsumptionModel;

/**
 * Created by pawel on 2014-07-31.
 */
@Module (
        library = true,
        complete = false
)
public class BLLModule  {

    @Provides
    IConsumptionCalculator provideConsumptionCalculator() {
        return new ConsumptionCalculator();
    }

    @Provides
    IHistoryKeeper provideHistoryKeeper(IDatabaseManager databaseManager, ConsumptionModel model, Bus bus) {
        return new HistoryKeeper(databaseManager, model, bus);
    }
}

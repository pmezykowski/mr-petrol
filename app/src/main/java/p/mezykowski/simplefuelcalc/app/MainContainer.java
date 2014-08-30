package p.mezykowski.simplefuelcalc.app;

import android.content.Context;

import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import p.mezykowski.simplefuelcalc.businessLogic.BLLModule;
import p.mezykowski.simplefuelcalc.dataAccess.DALModule;
import p.mezykowski.simplefuelcalc.model.ModelModule;
import p.mezykowski.simplefuelcalc.ui.UIModule;

/**
 * Created by pawel on 2014-07-30.
 */

@Module (
        includes = {
                ModelModule.class,
                DALModule.class,
                BLLModule.class,
                UIModule.class
        },
        injects = {
            SimpleFuelCalcApplication.class
        },
        complete = false
)
public class MainContainer {

    private final SimpleFuelCalcApplication app;

    public MainContainer(SimpleFuelCalcApplication app) {
        this.app = app;
    }

    @Provides @Singleton
    Bus providesEventBus () {
        return new Bus();
    }

    @Provides
    Context providesApplicationContext () {
        return app.getApplicationContext();
    }
}

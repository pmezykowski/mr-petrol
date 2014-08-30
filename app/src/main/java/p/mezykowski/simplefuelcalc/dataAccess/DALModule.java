package p.mezykowski.simplefuelcalc.dataAccess;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import p.mezykowski.simplefuelcalc.dataAccess.saveLoad.DatabaseManager;
import p.mezykowski.simplefuelcalc.dataAccess.saveLoad.IDatabaseManager;

/**
 * Created by pawel on 2014-08-21.
 */
@Module (
        library = true,
        complete = false
)
public class DALModule {

    @Provides
    IDatabaseManager providesDatabaseManager(Context context) {
        return new DatabaseManager(context);
    }

}

package p.mezykowski.simplefuelcalc.dataAccess.saveLoad.upgrades;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by pawel on 2014-08-24.
 */
public interface IDatabaseUpgrade {

    public void upgradeVersion(SQLiteDatabase db);
}

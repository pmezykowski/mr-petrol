package p.mezykowski.simplefuelcalc.dataAccess.saveLoad.upgrades;

import android.database.sqlite.SQLiteDatabase;

import p.mezykowski.simplefuelcalc.dataAccess.saveLoad.DatabaseConsts;

/**
 * Created by pawel on 2014-08-24.
 */
public class DatabaseUpgradeV2 implements IDatabaseUpgrade {

    @Override
    public void upgradeVersion(SQLiteDatabase db) {
        db.execSQL("ALTER TABLE "+ DatabaseConsts.DB_CONSUMPTIONS_TABLE+" ADD COLUMN "+DatabaseConsts.DESCRIPTION+" TEXT DEFAULT ''");
        db.execSQL("ALTER TABLE "+ DatabaseConsts.DB_CONSUMPTIONS_TABLE+" ADD COLUMN "+DatabaseConsts.DATE+" TEXT DEFAULT ''");

    }
}

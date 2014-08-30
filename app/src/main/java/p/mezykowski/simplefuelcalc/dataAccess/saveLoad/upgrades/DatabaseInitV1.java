package p.mezykowski.simplefuelcalc.dataAccess.saveLoad.upgrades;

import android.database.sqlite.SQLiteDatabase;

import p.mezykowski.simplefuelcalc.dataAccess.saveLoad.DatabaseConsts;

/**
 * Created by pawel on 2014-08-24.
 */
public class DatabaseInitV1 implements IDatabaseUpgrade {
    @Override
    public void upgradeVersion(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+ DatabaseConsts.DB_CONSUMPTIONS_TABLE+" ("+
                        DatabaseConsts.ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                        DatabaseConsts.GAS_VOLUME + " REAL," +
                        DatabaseConsts.DISTANCE + " REAL," +
                        DatabaseConsts.CONSUMPTION + " REAL," +
                        DatabaseConsts.PRICE + " REAL," +
                        DatabaseConsts.TOTAL_COST + " REAL" +
                        ");"
        );
    }
}

package p.mezykowski.simplefuelcalc.dataAccess.saveLoad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Debug;
import android.provider.ContactsContract;
import android.util.Log;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import p.mezykowski.simplefuelcalc.dataAccess.saveLoad.upgrades.DatabaseInitV1;
import p.mezykowski.simplefuelcalc.dataAccess.saveLoad.upgrades.DatabaseUpgradeV2;
import p.mezykowski.simplefuelcalc.dataAccess.saveLoad.upgrades.IDatabaseUpgrade;
import p.mezykowski.simplefuelcalc.model.consumption.ConsumptionDataObject;

/**
 * Created by pawel on 2014-08-21.
 */
public class DatabaseManager extends SQLiteOpenHelper implements IDatabaseManager {

    private HashMap<Integer, IDatabaseUpgrade> upgrades;

    public DatabaseManager(Context context) {
        super(context, DatabaseConsts.DB_NAME, null, DatabaseConsts.DB_VERSION);
        initUpgrades();
    }

    private void initUpgrades() {
        upgrades = new HashMap<Integer, IDatabaseUpgrade>();
        upgrades.put(1, new DatabaseInitV1());
        upgrades.put(2, new DatabaseUpgradeV2());
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        onUpgrade(sqLiteDatabase, 0, DatabaseConsts.DB_VERSION);
    }

    public long storeConsumptionValues (ConsumptionDataObject data) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = createContentValuesFromConsuption(data);
        long id = db.insertOrThrow(DatabaseConsts.DB_CONSUMPTIONS_TABLE, null, values);
        return id;
    }

    private ContentValues createContentValuesFromConsuption(ConsumptionDataObject data) {
        ContentValues values = new ContentValues();
        values.put(DatabaseConsts.GAS_VOLUME, data.getValue(ConsumptionDataObject.Keys.GasVolume));
        values.put(DatabaseConsts.DISTANCE, data.getValue(ConsumptionDataObject.Keys.Distance));
        values.put(DatabaseConsts.CONSUMPTION, data.getValue(ConsumptionDataObject.Keys.Consumption));
        values.put(DatabaseConsts.PRICE, data.getValue(ConsumptionDataObject.Keys.Price));
        values.put(DatabaseConsts.TOTAL_COST, data.getValue(ConsumptionDataObject.Keys.TotalCost));
        values.put(DatabaseConsts.DESCRIPTION, data.getDescription());
        values.put(DatabaseConsts.DATE, convertDateToSQLiteFormat(data.getDate()));
        return values;
    }

    private String convertDateToSQLiteFormat(Date date) {
        if (date == null)
        {
            return "";
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }

    private Date convertDateStringFromSQLiteFormat(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition position = new ParsePosition(0);
        Date date = dateFormat.parse(dateString, position);
        if (position.getErrorIndex() >=0)
        {
            return null;
        }
        return date;
    }

    public List<ConsumptionDataObject> getAllConsumptions() {
        List<ConsumptionDataObject> result = new ArrayList<ConsumptionDataObject>();
        Cursor cursor = getAllConsumptionsCursor();
        ConsumptionDataObject tempObject;
        while (cursor.moveToNext()) {
            tempObject = new ConsumptionDataObject();
            tempObject.setId(cursor.getInt(0));
            tempObject.setValue(ConsumptionDataObject.Keys.GasVolume, cursor.getDouble(1));
            tempObject.setValue(ConsumptionDataObject.Keys.Distance, cursor.getDouble(2));
            tempObject.setValue(ConsumptionDataObject.Keys.Consumption, cursor.getDouble(3));
            tempObject.setValue(ConsumptionDataObject.Keys.Price, cursor.getDouble(4));
            tempObject.setValue(ConsumptionDataObject.Keys.TotalCost, cursor.getDouble(5));
            tempObject.setDescription(cursor.getString(6));
            tempObject.setDate(convertDateStringFromSQLiteFormat(cursor.getString(7)));
            result.add(tempObject);
        }

        return result;
    }

    @Override
    public void removeConsumptionValues(List<ConsumptionDataObject> selectedItems) {
        SQLiteDatabase db = getWritableDatabase();
        String idList = createIdStringList(selectedItems);
        int rowsAffected = db.delete(DatabaseConsts.DB_CONSUMPTIONS_TABLE, DatabaseConsts.ID +" IN ( "+ idList + " )", null);
        Log.w("petrol db", "DELETE, rows affected: "+rowsAffected);
    }

    @Override
    public void updateValuesForId(long id, ConsumptionDataObject valuesState) {
        String idString = ""+id;
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = createContentValuesFromConsuption(valuesState);
        int rowsAffected = db.update(DatabaseConsts.DB_CONSUMPTIONS_TABLE, values, DatabaseConsts.ID +" = ?", new String[] {idString});
        Log.w("petrol db", "UPDATES, rows affected: "+rowsAffected);
    }

    private String createIdStringList(List<ConsumptionDataObject> items) {
        String result = "";
        for (int i = 0; i<items.size()-1; i++) {
            result += items.get(i).getId()+", ";
        }
        result += items.get(items.size()-1).getId();

        return result;
    }

    private Cursor getAllConsumptionsCursor () {
        String [] columns = {
                DatabaseConsts.ID,
                DatabaseConsts.GAS_VOLUME,
                DatabaseConsts.DISTANCE,
                DatabaseConsts.CONSUMPTION,
                DatabaseConsts.PRICE,
                DatabaseConsts.TOTAL_COST,
                DatabaseConsts.DESCRIPTION,
                DatabaseConsts.DATE
        };
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(DatabaseConsts.DB_CONSUMPTIONS_TABLE, columns, null, null, null,null, DatabaseConsts.DATE);
        return cursor;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        while (oldVersion != newVersion) {
            upgrades.get(oldVersion+1).upgradeVersion(sqLiteDatabase);
            oldVersion++;
        }

    }

}

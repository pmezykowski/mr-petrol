package p.mezykowski.simplefuelcalc.dataAccess.saveLoad;

import android.database.Cursor;

import java.util.List;

import p.mezykowski.simplefuelcalc.model.consumption.ConsumptionDataObject;

/**
 * Created by pawel on 2014-08-21.
 */
public interface IDatabaseManager {

    long storeConsumptionValues (ConsumptionDataObject data);

    List<ConsumptionDataObject> getAllConsumptions ();

    void removeConsumptionValues(List<ConsumptionDataObject> selectedItems);

    void updateValuesForId(long id, ConsumptionDataObject valuesState);
}

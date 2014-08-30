package p.mezykowski.simplefuelcalc.businessLogic.history;

import java.util.List;

import p.mezykowski.simplefuelcalc.model.consumption.ConsumptionDataObject;

/**
 * Created by pawel on 2014-07-31.
 */
public interface IHistoryKeeper {

    void saveConsumption(ConsumptionDataObject valuesState);

    List<ConsumptionDataObject> getConsumptions();

    void loadSavedHistory();

    void remove(List<ConsumptionDataObject> selectedItems);

    ConsumptionDataObject getConsumptionById(long consumptionDataId);
}

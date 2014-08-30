package p.mezykowski.simplefuelcalc.businessLogic.history;

import android.database.Cursor;

import com.squareup.otto.Bus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import p.mezykowski.simplefuelcalc.dataAccess.saveLoad.IDatabaseManager;
import p.mezykowski.simplefuelcalc.model.consumption.ConsumptionDataObject;
import p.mezykowski.simplefuelcalc.model.consumption.ConsumptionModel;

/**
 * Created by pawel on 2014-08-21.
 */
public class HistoryKeeper implements IHistoryKeeper {

    private IDatabaseManager databaseManager;
    private ConsumptionModel consumptionModel;
    private Bus eventBus;

    public HistoryKeeper(IDatabaseManager databaseManager, ConsumptionModel model, Bus bus) {
        this.databaseManager = databaseManager;
        this.consumptionModel = model;
        this.eventBus = bus;
    }

    @Override
    public void saveConsumption(ConsumptionDataObject valuesState) {
        if (valuesState.isUnsavedItem()) {
            addNewConsumption(valuesState);
        } else {
            changeExistingConsumption(valuesState);
        }

        dispatchConsumptionUpdated();
    }

    private void changeExistingConsumption(ConsumptionDataObject valuesState) {
        this.databaseManager.updateValuesForId(valuesState.getId(), valuesState);
    }

    private void addNewConsumption(ConsumptionDataObject valuesState) {
        this.consumptionModel.add(valuesState);
        long generatedId = this.databaseManager.storeConsumptionValues(valuesState);
        valuesState.setId(generatedId);
    }

    @Override
    public List<ConsumptionDataObject> getConsumptions() {
         return this.consumptionModel.getConsumptions();
    }

    @Override
    public void loadSavedHistory() {
        this.consumptionModel.setConsumptions(this.databaseManager.getAllConsumptions());
        dispatchConsumptionUpdated();
    }

    @Override
    public void remove(List<ConsumptionDataObject> selectedItems) {
        this.consumptionModel.removeAll(selectedItems);
        this.databaseManager.removeConsumptionValues(selectedItems);
    }

    @Override
    public ConsumptionDataObject getConsumptionById(long consumptionDataId) {
        return this.consumptionModel.getConsumptionById(consumptionDataId);
    }

    private void dispatchConsumptionUpdated() {
        eventBus.post(new ConsumptionListUpdatedEvent());
    }
}

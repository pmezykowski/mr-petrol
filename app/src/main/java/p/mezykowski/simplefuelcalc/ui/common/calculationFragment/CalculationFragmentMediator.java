package p.mezykowski.simplefuelcalc.ui.common.calculationFragment;

import android.widget.Toast;

import javax.inject.Inject;

import p.mezykowski.simplefuelcalc.businessLogic.calculation.IConsumptionCalculator;
import p.mezykowski.simplefuelcalc.businessLogic.history.IHistoryKeeper;
import p.mezykowski.simplefuelcalc.model.consumption.ConsumptionDataObject;
import p.mezykowski.simplefuelcalc.ui.base.fragments.FragmentMediatorBase;
import pl.pawelmezykowski.myapplication.R;

/**
 * Created by pawel on 2014-08-12.
 */
public class CalculationFragmentMediator extends FragmentMediatorBase<CalculationFragment> {

    @Inject
    IConsumptionCalculator calculatorEngine;

    @Inject
    IHistoryKeeper historyKeeper;

    public CalculationFragmentMediator(CalculationFragment fragment) {
        super (fragment);
    }

    public void updateValue(ConsumptionDataObject.Keys key, double value) {
        calculatorEngine.setValue(key, value);
        refreshView();
    }

    public void clearValue(ConsumptionDataObject.Keys key) {
        calculatorEngine.clearValue(key);
        refreshView();
    }

    private void refreshView() {
        calculatorEngine.calculate();
        ConsumptionDataObject state = calculatorEngine.getStateObjectCopy();

        for (ConsumptionDataObject.Keys key : state.getUsedValueKeys()) {
            RefreshEditTextFromStateWithKey(state, key);
        }

    }

    private void RefreshEditTextFromStateWithKey(ConsumptionDataObject state, ConsumptionDataObject.Keys key) {
        if (state.isValueSet(key)) {
            if (!state.isValueSetManually(key)) {
                getFragment().setValue(key, calculatorEngine.getValue(key));
            }
        } else {
            getFragment().clearValue(key);
        }
    }

    public void saveCurrentValues() {
        ConsumptionDataObject objectToSave = calculatorEngine.getStateObjectCopy();
        saveData(objectToSave);
    }

    private void saveData(ConsumptionDataObject objectToSave) {
        objectToSave.setDescription(getFragment().getDescriptionText());
        objectToSave.setDate(getFragment().getCalculationDate());
        try {
            historyKeeper.saveConsumption(objectToSave);
        } catch (Exception e) {
            Toast.makeText(this.getFragment().getActivity().getApplicationContext(), R.string.save_toast_error, Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this.getFragment().getActivity().getApplicationContext(), R.string.save_toast_success, Toast.LENGTH_SHORT).show();
    }

    public void updateDataObject(ConsumptionDataObject dataObject) {
        saveData(dataObject);
    }
}

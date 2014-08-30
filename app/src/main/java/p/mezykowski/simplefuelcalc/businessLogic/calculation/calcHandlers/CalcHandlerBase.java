package p.mezykowski.simplefuelcalc.businessLogic.calculation.calcHandlers;

import p.mezykowski.simplefuelcalc.model.consumption.ConsumptionDataObject;

/**
 * Created by pawel on 2014-07-30.
 */
public abstract class CalcHandlerBase {

    protected abstract double doCalc();

    protected abstract ConsumptionDataObject.Keys getValueToCalculate();

    private CalcHandlerBase nextHandler;

    protected ConsumptionDataObject values;

    public void setNextHandler(CalcHandlerBase nextHandler) {
        this.nextHandler = nextHandler;
    }

    public void handle(ConsumptionDataObject valuesState) {
        this.values = valuesState;
        tryCalculate();
        tryHandleNext();
    }

    private void tryHandleNext() {
        if (nextHandler != null) {
            nextHandler.handle(values);
        }
    }

    private void tryCalculate() {
        if (!values.isValueSet(getValueToCalculate())) {
            double calcResult = doCalc();
            if (!Double.isNaN(calcResult)) {
                values.setValue(getValueToCalculate(), calcResult);
            }
        }
    }

    protected boolean canCalculate(ConsumptionDataObject.Keys[] necessaryKeys) {
        return values.areValuesSet(necessaryKeys);
    }

}

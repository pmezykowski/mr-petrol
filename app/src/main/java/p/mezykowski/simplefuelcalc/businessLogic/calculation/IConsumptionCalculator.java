package p.mezykowski.simplefuelcalc.businessLogic.calculation;

import p.mezykowski.simplefuelcalc.model.consumption.ConsumptionDataObject;

/**
 * Created by pawel on 2014-07-30.
 */
public interface IConsumptionCalculator {

    void clear();

    void setValue(ConsumptionDataObject.Keys valueKey, double value);

    void clearValue(ConsumptionDataObject.Keys valueKey);

    void calculate();

    double getValue(ConsumptionDataObject.Keys valueKey);

    ConsumptionDataObject getStateObjectCopy();

}

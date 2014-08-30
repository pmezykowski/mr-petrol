package p.mezykowski.simplefuelcalc.businessLogic.calculation.calcHandlers;

import p.mezykowski.simplefuelcalc.model.consumption.ConsumptionDataObject;

/**
 * Created by pawel on 2014-07-30.
 */
public class DistanceCalcHandler extends  CalcHandlerBase{

    @Override
    protected ConsumptionDataObject.Keys getValueToCalculate() {
        return ConsumptionDataObject.Keys.Distance;
    }

    @Override
    protected double doCalc() {
        if (canCalculate(valueKeysForCalc1())){
            return calc1();
        } else if (canCalculate(valueKeysForCalc2())) {
            return  calc2();
        }
        return Double.NaN;
    }

    private static ConsumptionDataObject.Keys[] valueKeysForCalc1() {
        return new ConsumptionDataObject.Keys[] {
                ConsumptionDataObject.Keys.GasVolume,
                ConsumptionDataObject.Keys.Consumption
        };
    }

    private double calc1 () {
        double volume = values.getValue(ConsumptionDataObject.Keys.GasVolume);
        double consumption = values.getValue(ConsumptionDataObject.Keys.Consumption);
        return volume*100/consumption;
    }

    private static ConsumptionDataObject.Keys[] valueKeysForCalc2() {
        return new ConsumptionDataObject.Keys[] {
                ConsumptionDataObject.Keys.TotalCost,
                ConsumptionDataObject.Keys.Price,
                ConsumptionDataObject.Keys.Consumption
        };
    }

    private double calc2 () {
        double totalCost = values.getValue(ConsumptionDataObject.Keys.TotalCost);
        double price = values.getValue(ConsumptionDataObject.Keys.Price);
        double consumption = values.getValue(ConsumptionDataObject.Keys.Consumption);
        return totalCost*100/(price*consumption);
    }
}

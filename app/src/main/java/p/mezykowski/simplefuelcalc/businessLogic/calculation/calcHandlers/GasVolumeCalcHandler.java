package p.mezykowski.simplefuelcalc.businessLogic.calculation.calcHandlers;

import p.mezykowski.simplefuelcalc.model.consumption.ConsumptionDataObject;

/**
 * Created by pawel on 2014-07-31.
 */
public class GasVolumeCalcHandler extends CalcHandlerBase {

    @Override
    protected ConsumptionDataObject.Keys getValueToCalculate() {
        return ConsumptionDataObject.Keys.GasVolume;
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
                ConsumptionDataObject.Keys.Distance,
                ConsumptionDataObject.Keys.Consumption
        };
    }

    private double calc1 () {
        double distance = values.getValue(ConsumptionDataObject.Keys.Distance);
        double consumption = values.getValue(ConsumptionDataObject.Keys.Consumption);
        return distance*consumption/100;
    }

    private static ConsumptionDataObject.Keys[] valueKeysForCalc2() {
        return new ConsumptionDataObject.Keys[] {
                ConsumptionDataObject.Keys.TotalCost,
                ConsumptionDataObject.Keys.Price
        };
    }

    private double calc2 () {
        double totalCost = values.getValue(ConsumptionDataObject.Keys.TotalCost);
        double price = values.getValue(ConsumptionDataObject.Keys.Price);
        return totalCost/price;
    }
}

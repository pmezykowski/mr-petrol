package p.mezykowski.simplefuelcalc.businessLogic.calculation.calcHandlers;

import p.mezykowski.simplefuelcalc.model.consumption.ConsumptionDataObject;

/**
 * Created by pawel on 2014-07-31.
 */
public class ConsumptionCalcHandler extends CalcHandlerBase {

    protected ConsumptionDataObject.Keys getValueToCalculate() {
        return ConsumptionDataObject.Keys.Consumption;
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
                ConsumptionDataObject.Keys.Distance
        };
    }

    private double calc1 () {
        double volume = values.getValue(ConsumptionDataObject.Keys.GasVolume);
        double distance = values.getValue(ConsumptionDataObject.Keys.Distance);
        return volume*100/distance;
    }

    private static ConsumptionDataObject.Keys[] valueKeysForCalc2() {
        return new ConsumptionDataObject.Keys[] {
                ConsumptionDataObject.Keys.TotalCost,
                ConsumptionDataObject.Keys.Price,
                ConsumptionDataObject.Keys.Distance
        };
    }

    private double calc2 () {
        double totalCost = values.getValue(ConsumptionDataObject.Keys.TotalCost);
        double price = values.getValue(ConsumptionDataObject.Keys.Price);
        double distance = values.getValue(ConsumptionDataObject.Keys.Distance);
        return totalCost*100/(price*distance);
    }
}

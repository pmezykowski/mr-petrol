package p.mezykowski.simplefuelcalc.businessLogic.calculation.calcHandlers;

import p.mezykowski.simplefuelcalc.model.consumption.ConsumptionDataObject;

/**
 * Created by pawel on 2014-07-31.
 */
public class TotalCostCalcHandler extends CalcHandlerBase {


    @Override
    protected ConsumptionDataObject.Keys getValueToCalculate() {
        return ConsumptionDataObject.Keys.TotalCost;
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
                ConsumptionDataObject.Keys.Price,
                ConsumptionDataObject.Keys.GasVolume
        };
    }

    private double calc1 () {
        double price = values.getValue(ConsumptionDataObject.Keys.Price);
        double volume = values.getValue(ConsumptionDataObject.Keys.GasVolume);
        return price*volume;
    }

    private static ConsumptionDataObject.Keys[] valueKeysForCalc2() {
        return new ConsumptionDataObject.Keys[] {
                ConsumptionDataObject.Keys.Price,
                ConsumptionDataObject.Keys.Distance,
                ConsumptionDataObject.Keys.Consumption
        };
    }

    private double calc2 () {
        double price = values.getValue(ConsumptionDataObject.Keys.Price);
        double dist = values.getValue(ConsumptionDataObject.Keys.Distance);
        double consumption = values.getValue(ConsumptionDataObject.Keys.Consumption);

        return price*dist*consumption/100;
    }
}

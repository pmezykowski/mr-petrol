package p.mezykowski.simplefuelcalc.businessLogic.calculation;

import java.util.ArrayList;
import java.util.List;

import p.mezykowski.simplefuelcalc.businessLogic.calculation.calcHandlers.CalcHandlerBase;
import p.mezykowski.simplefuelcalc.businessLogic.calculation.calcHandlers.ConsumptionCalcHandler;
import p.mezykowski.simplefuelcalc.businessLogic.calculation.calcHandlers.DistanceCalcHandler;
import p.mezykowski.simplefuelcalc.businessLogic.calculation.calcHandlers.GasVolumeCalcHandler;
import p.mezykowski.simplefuelcalc.businessLogic.calculation.calcHandlers.PriceCalcHandler;
import p.mezykowski.simplefuelcalc.businessLogic.calculation.calcHandlers.TotalCostCalcHandler;
import p.mezykowski.simplefuelcalc.model.consumption.ConsumptionDataObject;

/**
 * Created by pawel on 2014-07-30.
 */
public class ConsumptionCalculator implements IConsumptionCalculator {

    private ConsumptionDataObject state;

    private boolean isCalculated;

    private CalcHandlerBase calcChain;

    public ConsumptionCalculator() {
        this.state = new ConsumptionDataObject();
        clear();
        configureChain();   //chain of responsibility
    }

    private void configureChain() {
        List<CalcHandlerBase> handlers = new ArrayList<CalcHandlerBase>();
        handlers.add(new DistanceCalcHandler());
        handlers.add(new GasVolumeCalcHandler());
        handlers.add(new PriceCalcHandler());
        handlers.add(new ConsumptionCalcHandler());
        handlers.add(new TotalCostCalcHandler());

        for (int i = 0; i<handlers.size()-1; i++) { //for each without last
            handlers.get(i).setNextHandler(handlers.get(i+1));
        }
        calcChain = handlers.get(0);
    }

    @Override
    public void clear() {
        state.clear();
    }

    @Override
    public void setValue(ConsumptionDataObject.Keys valueKey, double value) {
        state.setValue(valueKey, value);
        state.setValueIsSetManually(valueKey, true);
    }

    @Override
    public void clearValue(ConsumptionDataObject.Keys valueKey) {
        state.setValueIsSet(valueKey, false);
    }

    @Override
    public void calculate() {
        this.state.clearCalculation();
        calcChain.handle(this.state);
    }

    @Override
    public double getValue(ConsumptionDataObject.Keys valueKey) {
        return state.getValue(valueKey);
    }

    public ConsumptionDataObject getStateObjectCopy() {
        return state.copy();
    }
}

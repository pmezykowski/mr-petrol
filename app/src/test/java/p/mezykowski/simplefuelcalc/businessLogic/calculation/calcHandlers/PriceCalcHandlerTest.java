package p.mezykowski.simplefuelcalc.businessLogic.calculation.calcHandlers;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import p.mezykowski.simplefuelcalc.model.consumption.ConsumptionDataObject;

/**
 * Created by pawel on 2014-08-12.
 */
@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class PriceCalcHandlerTest extends PriceCalcHandler{

    private ConsumptionDataObject state;

    @Before
    public void setup() {
        state = new ConsumptionDataObject();
    }

    @Test
    public void shouldCalculatePriceWithTotalCostAndGasVolume() {

        state.setValue(ConsumptionDataObject.Keys.TotalCost, 100);
        state.setValue(ConsumptionDataObject.Keys.GasVolume, 20);
        this.values = state;

        double result = this.doCalc();

        Assert.assertEquals(5.0, result);
    }

    @Test
    public void shouldCalculatePriceWithTotalCostDistanceAndConsumption() {
        state.setValue(ConsumptionDataObject.Keys.TotalCost, 100);
        state.setValue(ConsumptionDataObject.Keys.Distance, 200);
        state.setValue(ConsumptionDataObject.Keys.Consumption, 10);
        this.values = state;

        double result = this.doCalc();

        Assert.assertEquals(5.0, result);
    }
}

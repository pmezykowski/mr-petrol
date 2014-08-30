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
public class ConsumptionCalcHandlerTest extends ConsumptionCalcHandler{

    private ConsumptionDataObject state;

    @Before
    public void setup() {
        state = new ConsumptionDataObject();
    }

    @Test
    public void shouldCalculateConsumptionWithDistanceAndGasVolume() {

        state.setValue(ConsumptionDataObject.Keys.Distance, 200);
        state.setValue(ConsumptionDataObject.Keys.GasVolume, 20);
        this.values = state;

        double result = this.doCalc();

        Assert.assertEquals(10.0, result);
    }

    @Test
    public void shouldCalculateConsumptionWithDistanceTotalCostAndGasPrice() {
        state.setValue(ConsumptionDataObject.Keys.Distance, 200);
        state.setValue(ConsumptionDataObject.Keys.Price, 5);
        state.setValue(ConsumptionDataObject.Keys.TotalCost, 100);
        this.values = state;

        double result = this.doCalc();

        Assert.assertEquals(10.0, result);
    }
}

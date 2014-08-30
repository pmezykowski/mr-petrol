package p.mezykowski.simplefuelcalc.model.consumption;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Singleton;

/**
 * Created by pawel on 2014-08-22.
 */

@Singleton
public class ConsumptionModel {

    private List<ConsumptionDataObject> consumptions;
    private HashMap<Long, ConsumptionDataObject> consumptionsMap;

    public ConsumptionModel () {
        consumptions = new ArrayList<ConsumptionDataObject>();
        consumptionsMap = new HashMap<Long, ConsumptionDataObject>();
    }

    public List<ConsumptionDataObject> getConsumptions() {
        return new ArrayList<ConsumptionDataObject>(consumptions);
    }

    public void setConsumptions(List<ConsumptionDataObject> consumptions) {
        this.consumptions = consumptions;
        consumptionsMap.clear();
        for (ConsumptionDataObject consumption : consumptions) {
            consumptionsMap.put(consumption.getId(), consumption);
        }
    }

    public void add(ConsumptionDataObject consumption) {
        consumptions.add(consumption);
        consumptionsMap.put(consumption.getId(), consumption);
    }

    public void removeAll(List<ConsumptionDataObject> consumptions) {
        this.consumptions.removeAll(consumptions);
        for (ConsumptionDataObject consumption : consumptions) {
            consumptionsMap.remove(consumption.getId());
        }
    }

    public ConsumptionDataObject getConsumptionById(long consumptionDataId) {
        return consumptionsMap.get(consumptionDataId);
    }
}

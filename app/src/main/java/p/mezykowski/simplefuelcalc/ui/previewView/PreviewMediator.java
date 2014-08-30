package p.mezykowski.simplefuelcalc.ui.previewView;

import javax.inject.Inject;

import p.mezykowski.simplefuelcalc.businessLogic.history.IHistoryKeeper;
import p.mezykowski.simplefuelcalc.model.consumption.ConsumptionDataObject;
import p.mezykowski.simplefuelcalc.ui.base.ActivityMediatorBase;
import p.mezykowski.simplefuelcalc.ui.base.fragments.MediatorBase;

/**
 * Created by pawel on 2014-08-27.
 */
public class PreviewMediator extends ActivityMediatorBase<PreviewActivity> {

    @Inject
    IHistoryKeeper historyKeeper;

    public PreviewMediator(PreviewActivity boundActivity) {
        super(boundActivity);
    }

    public ConsumptionDataObject getConsumptionDataById(long consumptionDataId) {
        return historyKeeper.getConsumptionById(consumptionDataId);
    }
}

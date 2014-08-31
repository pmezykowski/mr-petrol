package p.mezykowski.simplefuelcalc.ui.mainView.historyFragment;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import p.mezykowski.simplefuelcalc.businessLogic.history.ConsumptionListUpdatedEvent;
import p.mezykowski.simplefuelcalc.businessLogic.history.IHistoryKeeper;
import p.mezykowski.simplefuelcalc.model.consumption.ConsumptionDataObject;
import p.mezykowski.simplefuelcalc.ui.base.fragments.FragmentMediatorBase;

/**
 * Created by pawel on 2014-08-21.
 */
public class HistoryFragmentMediator extends FragmentMediatorBase<HistoryFragment> {

    @Inject
    IHistoryKeeper historyKeeper;

    @Inject
    Bus eventBus;

    private List<ConsumptionDataObject> consumptions;

    public HistoryFragmentMediator(HistoryFragment boundFragment) {
        super(boundFragment);
        consumptions = new ArrayList<ConsumptionDataObject>();
    }

    @Subscribe
    public void onConsumptionListUpdated(ConsumptionListUpdatedEvent event) {
        refreshListValues();
    }

    @Override
    protected void afterBind() {
        super.afterBind();
        eventBus.register(this);
        recreateValuesList();
    }

    private void recreateValuesList() {
        historyKeeper.loadSavedHistory();
        refreshListValues();
    }

    private void refreshListValues() {
        consumptions = historyKeeper.getConsumptions();
        getFragment().refreshListView();
    }

    @Override
    protected void beforeUnbind() {
        super.beforeUnbind();
        eventBus.unregister(this);
    }

    public List<ConsumptionDataObject> getConsumptions() {
        return consumptions;
    }

    void removeConsumptions(List<ConsumptionDataObject> selectedItems) {
        historyKeeper.remove(selectedItems);
        refreshListValues();
    }
}

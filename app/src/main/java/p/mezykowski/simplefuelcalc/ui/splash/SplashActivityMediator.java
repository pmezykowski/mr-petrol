package p.mezykowski.simplefuelcalc.ui.splash;

import javax.inject.Inject;

import p.mezykowski.simplefuelcalc.businessLogic.history.IHistoryKeeper;
import p.mezykowski.simplefuelcalc.ui.base.ActivityMediatorBase;

/**
 * Created by pawel on 2014-08-22.
 */
public class SplashActivityMediator extends ActivityMediatorBase<SplashActivity> {

    @Inject
    IHistoryKeeper historyKeeper;


    public SplashActivityMediator(SplashActivity boundActivity) {
        super(boundActivity);
    }

    @Override
    protected void afterBind() {
        super.afterBind();
        initialize();
        this.activity.launchApplication();
    }

    private void initialize() {
        historyKeeper.loadSavedHistory();
    }
}

package p.mezykowski.simplefuelcalc.ui.base;

import android.app.Activity;

import p.mezykowski.simplefuelcalc.app.BaseDaggerApp;
import p.mezykowski.simplefuelcalc.ui.base.fragments.MediatorBase;

/**
 * Created by pawel on 2014-07-03.
 */
public class ActivityMediatorBase<T extends Activity> extends MediatorBase {

    protected T activity;

    public ActivityMediatorBase(T boundActivity) {
        this.activity = boundActivity;
        this.context = (BaseDaggerApp) activity.getApplicationContext();
        makeBinding();
    }

    @Override
    protected void unbindConcreteView() {
        this.activity = null;
    }
}

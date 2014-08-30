package p.mezykowski.simplefuelcalc.ui.base;

import android.support.v7.app.ActionBarActivity;

/**
 * Created by pawel on 2014-07-07.
 */
public class ActionbarActivityCompatBase<T extends ActivityMediatorBase> extends ActionBarActivity implements IViewBase<T> {

    protected T mediator;

    @Override
    public void setMediator(T mediator) {
        this.mediator = mediator;
    }

    @Override
    public T getMediator() {
        return this.mediator;
    }
}

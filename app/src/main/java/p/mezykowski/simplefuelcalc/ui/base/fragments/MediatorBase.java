package p.mezykowski.simplefuelcalc.ui.base.fragments;

import java.util.List;

import p.mezykowski.simplefuelcalc.app.BaseDaggerApp;
import p.mezykowski.simplefuelcalc.model.consumption.ConsumptionDataObject;

/**
 * Created by pawel on 2014-08-22.
 */
public abstract class MediatorBase {

    protected BaseDaggerApp context;


    protected void beforeBind () {

    }

    protected void makeBinding() {
        beforeBind();
        bindToFramework();
        afterBind();
    }

    protected void afterBind() {

    }

    private void bindToFramework() {
        this.context.inject(this);
    }

    protected void beforeUnbind() {

    }

    protected void afterUnbind() {

    }

    public void unbindView() {
        beforeUnbind();
        unbindConcreteView();
        afterUnbind();
    }

    protected abstract void unbindConcreteView();


}

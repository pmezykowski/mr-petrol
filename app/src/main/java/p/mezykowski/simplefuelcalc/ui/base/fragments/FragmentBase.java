package p.mezykowski.simplefuelcalc.ui.base.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import p.mezykowski.simplefuelcalc.ui.base.IViewBase;

/**
 * Created by pawel on 2014-08-12.
 */
public class FragmentBase <T extends FragmentMediatorBase> extends Fragment {

    protected T mediator;

    protected void setMediator(T mediator) {
        this.mediator = mediator;
        this.mediator.makeBinding();
    }

    protected T getMediator() {
        return mediator;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediator.unbindView();
        this.mediator = null;
    }
}

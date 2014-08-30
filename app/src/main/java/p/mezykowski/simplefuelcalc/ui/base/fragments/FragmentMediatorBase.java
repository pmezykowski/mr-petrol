package p.mezykowski.simplefuelcalc.ui.base.fragments;

import p.mezykowski.simplefuelcalc.app.BaseDaggerApp;

/**
 * Created by pawel on 2014-08-12.
 */
public class FragmentMediatorBase<T extends FragmentBase> extends MediatorBase {

    private T fragment;

    protected T getFragment() {
        return fragment;
    }

    public FragmentMediatorBase(T boundFragment) {
        this.fragment = boundFragment;
        this.context = (BaseDaggerApp) fragment.getActivity().getApplicationContext();
    }

    @Override
    protected void unbindConcreteView() {
        this.fragment = null;
    }

}

package p.mezykowski.simplefuelcalc.ui.base;

/**
 * Created by pawel on 2014-07-07.
 */
public interface IViewBase<T extends ActivityMediatorBase> {

    void setMediator(T mediator);

    T getMediator();
}

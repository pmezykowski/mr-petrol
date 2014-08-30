package p.mezykowski.simplefuelcalc.ui.base;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by pawel on 2014-07-03.
 */
public class ActivityBase<T extends ActivityMediatorBase> extends Activity implements IViewBase<T> {

    protected T mediator;

    @Override
    public void setMediator(T mediator) {
        this.mediator = mediator;
    }

    @Override
    public T getMediator() {
        return mediator;
    }

    protected void startActivity(Class<? extends Activity> activityClass) {
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediator.unbindView();
        this.mediator = null;
    }


}

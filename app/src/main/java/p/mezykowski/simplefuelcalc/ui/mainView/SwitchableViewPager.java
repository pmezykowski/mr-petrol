package p.mezykowski.simplefuelcalc.ui.mainView;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by pawel on 2014-08-26.
 */
public class SwitchableViewPager extends ViewPager {


    protected boolean isPageChangingEnabled;

    private boolean isSwiping;

    public boolean isPageChangingEnabled() {
        return isPageChangingEnabled;
    }

    public void setPageChangingEnabled(boolean isPageChangingEnabled) {
        this.isPageChangingEnabled = isPageChangingEnabled;
    }

    public SwitchableViewPager(Context context) {
        super(context);
        initPageChanging();
    }

    public SwitchableViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPageChanging();
    }

    private void initPageChanging() {
        isPageChangingEnabled = true;
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent ev) {
//        if (!isPageChangingEnabled) {
//            if (ev.getAction() == MotionEvent.ACTION_DOWN) {
//                isSwiping = true;
//            } else if (ev.getAction() == MotionEvent.ACTION_UP )
//            {
//                isSwiping = false;
//            }
//            return true;
//        } else {
//            return super.onTouchEvent(ev);
//        }
//    }
//
//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        if (!isPageChangingEnabled && isSwiping) {
//            return true;
//        } else {
//            return super.onInterceptTouchEvent(ev);
//        }
//    }
}

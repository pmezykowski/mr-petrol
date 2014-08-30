package p.mezykowski.simplefuelcalc.app;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import dagger.ObjectGraph;

/**
 * Created by pawel on 2014-07-30.
 */
public class SimpleFuelCalcApplication extends BaseDaggerApp {

    @Override
    protected List<Object> CreateModules() {
        List<Object> result = new ArrayList<Object>();
        result.add(new MainContainer(this));
        return result;
    }

}

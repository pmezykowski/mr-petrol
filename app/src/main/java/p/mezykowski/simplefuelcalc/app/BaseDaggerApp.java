package p.mezykowski.simplefuelcalc.app;

import android.app.Application;

import java.util.List;

import dagger.ObjectGraph;

/**
 * Created by pawel on 2014-08-01.
 */
public abstract class BaseDaggerApp extends Application {

    protected ObjectGraph graph;

    @Override
    public void onCreate() {
        super.onCreate();
        graph = ObjectGraph.create(CreateModules().toArray());
        graph.inject(this);
    }

    protected abstract List<Object> CreateModules();

    public void inject(Object injectedObject) {
        this.graph.inject(injectedObject);
    }
}

package p.mezykowski.simplefuelcalc.ui.mainView;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.ArrayMap;
import android.widget.Adapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Created by pawel on 2014-08-01.
 */
public class MainViewAdapter extends FragmentPagerAdapter {

    List<Fragment> fragments;
    List<Integer> textIds;
    Context context;

    public MainViewAdapter(FragmentManager fm, ArrayMap<Integer, Fragment> fragmentByTextIdCollection, Context context) {
        super(fm);
        this.fragments = new ArrayList<Fragment>(fragmentByTextIdCollection.values());
        this.textIds = new ArrayList<Integer>(fragmentByTextIdCollection.keySet());
        this.context = context;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return context.getResources().getString(textIds.get(position));
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}

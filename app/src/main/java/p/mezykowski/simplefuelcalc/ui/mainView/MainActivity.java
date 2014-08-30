package p.mezykowski.simplefuelcalc.ui.mainView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v4.util.ArrayMap;
import android.view.View;

import java.util.Iterator;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import p.mezykowski.simplefuelcalc.ui.base.ActionbarActivityCompatBase;
import p.mezykowski.simplefuelcalc.ui.common.calculationFragment.CalculationFragment;
import p.mezykowski.simplefuelcalc.ui.mainView.historyFragment.HistoryFragment;
import pl.pawelmezykowski.myapplication.R;

/**
 * Created by pawel on 2014-08-01.
 */
public class MainActivity extends ActionbarActivityCompatBase<MainActivityMediator> {

    @InjectView(R.id.pager_title_strip)
    PagerTitleStrip titleStrip;

    @InjectView(R.id.mainview_pager)
    SwitchableViewPager viewPager;

    private ActionBar actionBar;

    private MainViewAdapter adapter;

    private ArrayMap<Integer, Fragment> fragmentsByTabIdentifier;

    private HistoryFragment historyFragment;
    private CalculationFragment calculationFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMediator(new MainActivityMediator(this));
        setContentView(R.layout.main_view_layout);
        ButterKnife.inject(this);

        configurePager();
    }

    private void configurePager() {
        createTabs();
        createPager();
        //initializeTabs();
    }

    private void createPager() {
        adapter = new MainViewAdapter(getSupportFragmentManager(), fragmentsByTabIdentifier, getApplicationContext());
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener( new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                historyFragment.finishActionMode();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void createTabs() {
        calculationFragment = new CalculationFragment();
        historyFragment = new HistoryFragment();

        fragmentsByTabIdentifier = new ArrayMap<Integer, Fragment>();
        fragmentsByTabIdentifier.put(R.string.calculation_tab_header, calculationFragment);
        fragmentsByTabIdentifier.put(R.string.history_tab_header, historyFragment);

    }

    private void initializeTabs() {
        actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

            }
        };

        Iterator<Map.Entry<Integer, Fragment>> it = fragmentsByTabIdentifier.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Integer, Fragment> pairs = it.next();

            ActionBar.Tab tab = actionBar.newTab();
            tab.setText(pairs.getKey());
            tab.setTabListener(tabListener);
            actionBar.addTab(tab);
        }

    }

    public void setPagesChangeEnabled(boolean enabled) {
        titleStrip.setVisibility(enabled ? View.VISIBLE : View.INVISIBLE);
        viewPager.setPageChangingEnabled(enabled);
    }
}

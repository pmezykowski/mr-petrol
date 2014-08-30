package p.mezykowski.simplefuelcalc.ui;

/**
 * Created by pawel on 2014-08-12.
 */

import dagger.Module;
import p.mezykowski.simplefuelcalc.ui.mainView.MainActivityMediator;
import p.mezykowski.simplefuelcalc.ui.common.calculationFragment.CalculationFragmentMediator;
import p.mezykowski.simplefuelcalc.ui.mainView.historyFragment.HistoryFragmentMediator;
import p.mezykowski.simplefuelcalc.ui.previewView.PreviewMediator;
import p.mezykowski.simplefuelcalc.ui.splash.SplashActivityMediator;

@Module(
        library = true,
        complete = false,
        injects = {
                SplashActivityMediator.class,
                MainActivityMediator.class,
                CalculationFragmentMediator.class,
                HistoryFragmentMediator.class,
                PreviewMediator.class
        }
)
public class UIModule {

}

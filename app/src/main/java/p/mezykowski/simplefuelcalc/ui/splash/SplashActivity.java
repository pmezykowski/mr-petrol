package p.mezykowski.simplefuelcalc.ui.splash;

import android.content.Intent;
import android.os.Bundle;

import butterknife.ButterKnife;
import p.mezykowski.simplefuelcalc.ui.base.ActivityBase;
import p.mezykowski.simplefuelcalc.ui.mainView.MainActivity;
import p.mezykowski.simplefuelcalc.ui.mainView.MainActivityMediator;
import pl.pawelmezykowski.myapplication.R;

/**
 * Created by pawel on 2014-08-22.
 */
public class SplashActivity extends ActivityBase<SplashActivityMediator> {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMediator(new SplashActivityMediator(this));
    }

    void launchApplication() {
        Intent intent = new Intent(this.getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}

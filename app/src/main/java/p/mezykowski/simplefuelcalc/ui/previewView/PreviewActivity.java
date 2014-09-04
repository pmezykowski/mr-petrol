package p.mezykowski.simplefuelcalc.ui.previewView;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import butterknife.InjectView;
import p.mezykowski.simplefuelcalc.model.consumption.ConsumptionDataObject;
import p.mezykowski.simplefuelcalc.ui.base.ActionbarActivityCompatBase;
import p.mezykowski.simplefuelcalc.ui.base.ActivityBase;
import p.mezykowski.simplefuelcalc.ui.common.calculationFragment.CalculationFragment;
import pl.pawelmezykowski.myapplication.R;

/**
 * Created by pawel on 2014-08-27.
 */
public class PreviewActivity extends ActionbarActivityCompatBase<PreviewMediator> {

    public static final String INTENT_CONSUMPTION_ID_ARG = "consumption_id";
    public static final String INTENT_IN_EDIT_MODE_ARG = "in_edit_mode";

    private ConsumptionDataObject viewedObject;

    private CalculationFragment calculationFragment;

    private ActionMode actionMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setMediator(new PreviewMediator(this));
        prepareFragment();
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.preview_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void prepareFragment() {
        long consumptionDataId = getIntent().getLongExtra(INTENT_CONSUMPTION_ID_ARG, -1);
        if (consumptionDataId != -1) {
            viewedObject = getMediator().getConsumptionDataById(consumptionDataId);
        } else {
            viewedObject = new ConsumptionDataObject();
        }
        boolean inEditMode = getIntent().getBooleanExtra(INTENT_IN_EDIT_MODE_ARG, false);

        setContentView(R.layout.preview_view_layout);
        getSupportActionBar().setTitle(R.string.consumption_activity_name);
        calculationFragment = (CalculationFragment) getSupportFragmentManager().findFragmentById(R.id.calcFragment);
        calculationFragment.populateWithData(viewedObject);
        calculationFragment.setEditEnabled(inEditMode);

        if (inEditMode) {
            startEditing();
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.edit_item:
                startEditing();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void startEditing() {
        calculationFragment.setEditEnabled(true);
        if (actionMode == null) {
            actionMode = startSupportActionMode(actionModeCallback);
            actionMode.invalidate();
        }
    }


    private void stopEditing() {
        calculationFragment.setEditEnabled(false);
        if (actionMode != null) {
            actionMode.finish();
        }
        calculationFragment.save();
    }

    private ActionMode.Callback actionModeCallback = new ActionMode.Callback() {

        // Called when the action mode is created; startActionMode() was called
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            return true;
        }

        // Called each time the action mode is shown. Always called after onCreateActionMode, but
        // may be called multiple times if the mode is invalidated.
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false; // Return false if nothing is done
        }

        // Called when the user selects a contextual menu item
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            return false;
        }

        // Called when the user exits the action mode
        @Override
        public void onDestroyActionMode(ActionMode mode) {
            actionMode = null;
            stopEditing();
        }
    };

}

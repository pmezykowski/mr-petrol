package p.mezykowski.simplefuelcalc.ui.mainView.historyFragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.support.v7.view.ActionMode;

import java.util.ArrayList;
import java.util.List;
import butterknife.ButterKnife;
import butterknife.InjectView;
import p.mezykowski.simplefuelcalc.model.consumption.ConsumptionDataObject;
import p.mezykowski.simplefuelcalc.ui.base.fragments.FragmentBase;
import p.mezykowski.simplefuelcalc.ui.mainView.historyFragment.historyList.HistoryArrayAdapter;
import p.mezykowski.simplefuelcalc.ui.previewView.PreviewActivity;
import pl.pawelmezykowski.myapplication.R;

/**
 * Created by pawel on 2014-08-01.
 */
public class HistoryFragment extends FragmentBase<HistoryFragmentMediator> {

    private View view;

    private ActionMode actionMode;

    private HistoryArrayAdapter adapter;

    @InjectView(R.id.historyListView)
    ListView historyListView;

    List<ConsumptionDataObject> adapterUsedList;

    List<ConsumptionDataObject> selectedItems;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.setMediator(new HistoryFragmentMediator(this));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.history_view, container, false);
        ButterKnife.inject(this, view);
        initializeView();
        refreshListView();
        selectedItems = new ArrayList<ConsumptionDataObject>();
        return view;
    }

    private void initializeView() {

        this.historyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (actionMode == null) {
                    openPeviewConsumption(adapterUsedList.get(position), false);
                    return;
                }
                changeViewAndItemSelection(view, position);
            }
        });

        this.historyListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                if (actionMode == null) {
                    actionMode = ((ActionBarActivity) getActivity()).startSupportActionMode(actionModeCallback);
                    actionMode.invalidate();
                }
                changeViewAndItemSelection(view, position);
                return true;
            }
        });

    }

    private void changeViewAndItemSelection(View view, int position) {
        CheckBox cb = (CheckBox)view.findViewById(R.id.checked_checkbox);
        cb.setChecked(!cb.isChecked());
        ConsumptionDataObject clickedObject = adapterUsedList.get(position);
        clickedObject.setSelected(cb.isChecked());
        if (cb.isChecked())
        {
            cb.setVisibility(View.VISIBLE);
        } else {
            cb.setVisibility(View.INVISIBLE);
        }
        if (clickedObject.isSelected()) {
            selectedItems.add(clickedObject);
        } else if (selectedItems.contains(clickedObject)) {
            selectedItems.remove(clickedObject);
        }
        manageActions();
    }

    private void manageActions() {

        int itemsCount = getSelectedItems().size();
        if (actionMode != null) {
//            if(itemsCount == 0) {
//                actionMode.finish();
//            } else
            if (itemsCount == 1) {
                currentMenu.getItem(0).setVisible(true);
            } else {
                currentMenu.getItem(0).setVisible(false);
            }
        }

    }

    private List<ConsumptionDataObject> getSelectedItems() {
        return selectedItems;
    }

    void refreshListView() {

        if (historyListView == null) {
            return;
        }

        adapterUsedList =  getMediator().getConsumptions();

        adapter = new HistoryArrayAdapter(this.getActivity(), adapterUsedList);
        historyListView.setAdapter(adapter);
    }

    private Menu currentMenu;

    private ActionMode.Callback actionModeCallback = new ActionMode.Callback() {

        // Called when the action mode is created; startActionMode() was called
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // Inflate a menu resource providing context menu items
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.edit_history_menu, menu);
            currentMenu = menu;
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
            switch (item.getItemId()) {
                case R.id.remove_item:
                    removeSelectedItems();
                    finishActionMode();
                    return true;
                case R.id.edit_item:
                    openPeviewConsumption(selectedItems.get(0), true);
                default:
                    return false;
            }
        }

        // Called when the user exits the action mode
        @Override
        public void onDestroyActionMode(ActionMode mode) {
            clearSelectedItems();
            actionMode = null;
            currentMenu = null;
        }
    };

    private void openPeviewConsumption(ConsumptionDataObject consumptionDataObject, boolean withEdit) {
        Intent intent = new Intent(this.getActivity(), PreviewActivity.class);
        intent.putExtra(PreviewActivity.INTENT_CONSUMPTION_ID_ARG, consumptionDataObject.getId());
        intent.putExtra(PreviewActivity.INTENT_IN_EDIT_MODE_ARG, withEdit);
        this.getActivity().startActivity(intent);
    }

    public void finishActionMode() {
        if (actionMode != null) {
            actionMode.finish();
        }
    }

    private void removeSelectedItems() {
        List<ConsumptionDataObject> selectedItems = getSelectedItems();
        if (selectedItems.isEmpty()) {
            return;
        }
        adapterUsedList.removeAll(selectedItems);
        getMediator().removeConsumptions(selectedItems);
        clearSelectedItems();
    }

    private void clearSelectedItems() {
        for (ConsumptionDataObject consumptionObject : selectedItems) {
            consumptionObject.setSelected(false);
        }
        selectedItems.clear();
        adapter.notifyDataSetChanged();
    }
}

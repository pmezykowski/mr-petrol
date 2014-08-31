package p.mezykowski.simplefuelcalc.ui.common.calculationFragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import p.mezykowski.simplefuelcalc.model.consumption.ConsumptionDataObject;
import p.mezykowski.simplefuelcalc.ui.base.fragments.FragmentBase;
import pl.pawelmezykowski.myapplication.R;

/**
 * Created by pawel on 2014-08-01.
 */
public class CalculationFragment extends FragmentBase<CalculationFragmentMediator> {

    private View view;

    @InjectView(R.id.distanceEditText)
    EditText distanceET;

    @InjectView(R.id.gasVolumeEditText)
    EditText gasVolumeET;

    @InjectView(R.id.consumptionEditText)
    EditText consumptionET;

    @InjectView(R.id.totalCostEditText)
    EditText totalCostET;

    @InjectView(R.id.priceEditText)
    EditText priceET;

    @InjectView(R.id.descriptionEditText)
    EditText descriptionET;

    @InjectView(R.id.dateText)
    TextView dateTextView;

    @InjectView(R.id.changeDateBtn)
    Button changeDateButton;

    @InjectView(R.id.gasVolumeClear)
    ImageButton gasVolumeClearBtn;

    @InjectView(R.id.distanceClear)
    ImageButton distanceClearBtn;

    @InjectView(R.id.consumptionClear)
    ImageButton consumptionClearBtn;

    @InjectView(R.id.priceClear)
    ImageButton priceClearBtn;

    @InjectView(R.id.totalCostClear)
    ImageButton totalCostClearBtn;

    @InjectView(R.id.saveBtn)
    Button saveBtnBtn;

    @InjectView(R.id.valuesContainer)
    View valuesContainer;

    @InjectView(R.id.dateContainer)
    View dateContainer;

    @InjectView(R.id.descContainer)
    View descContainer;


    private Calendar calendar;

    private Dialog datePickerDialog;

    ConsumptionDataObject dataObject;

    @OnClick(R.id.changeDateBtn)
    public void onDateClick() {
        showDatePicker();
    }

    public void populateWithData(ConsumptionDataObject data) {
        this.dataObject = data;
        beginSilentManipulate();
        for (ConsumptionDataObject.Keys key : dataObject.getUsedValueKeys()) {
            keyWithTextEditBindings.get(key).setText(""+data.getValue(key));
        }

        descriptionET.setText(dataObject.getDescription());
        calendar = Calendar.getInstance();
        calendar.setTime(data.getDate());
        updateCalendar();
        endSilentManipulate();
    }

    public void setEditEnabled(boolean enabled) {
        for (EditText et : textEditWithKeyBindings.keySet()) {
            et.setEnabled(enabled);
        }
        for (ImageButton et : clearBtnsWithKeyBindings.keySet()) {
            et.setVisibility(enabled ? View.VISIBLE : View.INVISIBLE);
        }
        if (descriptionET.getText().toString().equals("")) {
            descriptionET.setVisibility(enabled ? View.VISIBLE : View.GONE);
        } else {
            descriptionET.setEnabled(enabled);
        }
        valuesContainer.setEnabled(enabled);
        descContainer.setEnabled(enabled);
        dateContainer.setEnabled(enabled);

        changeDateButton.setVisibility(enabled ? View.VISIBLE : View.INVISIBLE);
        saveBtnBtn.setVisibility(View.GONE);
    }

    private void showDatePicker() {
        if (datePickerDialog == null) {
            datePickerDialog = new DatePickerDialog(this.getActivity(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    calendar.set(year, month, day);
                    updateCalendar();
                }

            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        }
        datePickerDialog.show();
    }

    private HashMap<ConsumptionDataObject.Keys, EditText> keyWithTextEditBindings;
    private HashMap<EditText, ConsumptionDataObject.Keys> textEditWithKeyBindings;

    private void createTextEditBindings() {
        keyWithTextEditBindings = new HashMap<ConsumptionDataObject.Keys, EditText>();
        textEditWithKeyBindings = new HashMap<EditText, ConsumptionDataObject.Keys>();

        putTextEditBinding(ConsumptionDataObject.Keys.Consumption, consumptionET);
        putTextEditBinding(ConsumptionDataObject.Keys.Distance, distanceET);
        putTextEditBinding(ConsumptionDataObject.Keys.GasVolume, gasVolumeET);
        putTextEditBinding(ConsumptionDataObject.Keys.TotalCost, totalCostET);
        putTextEditBinding(ConsumptionDataObject.Keys.Price, priceET);
    }

    private void putTextEditBinding(ConsumptionDataObject.Keys key, EditText editText) {
        keyWithTextEditBindings.put(key, editText);
        textEditWithKeyBindings.put(editText, key);
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.setMediator(new CalculationFragmentMediator(this));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.calculation_view, container, false);
        ButterKnife.inject(this, view);
        initializeEditTexts();
        initializeButtons();
        updateCalendar();
        return view;
    }

    private HashMap<ConsumptionDataObject.Keys, ImageButton> keyWithClearBtnsBindings;
    private HashMap<ImageButton, ConsumptionDataObject.Keys> clearBtnsWithKeyBindings;

    private void initializeButtons() {
        keyWithClearBtnsBindings = new HashMap<ConsumptionDataObject.Keys, ImageButton>();
        clearBtnsWithKeyBindings = new HashMap<ImageButton, ConsumptionDataObject.Keys>();

        putClearBtnBinding(ConsumptionDataObject.Keys.Consumption, consumptionClearBtn);
        putClearBtnBinding(ConsumptionDataObject.Keys.Distance, distanceClearBtn);
        putClearBtnBinding(ConsumptionDataObject.Keys.GasVolume, gasVolumeClearBtn);
        putClearBtnBinding(ConsumptionDataObject.Keys.Price, priceClearBtn);
        putClearBtnBinding(ConsumptionDataObject.Keys.TotalCost, totalCostClearBtn);

        for (ImageButton clearButton : clearBtnsWithKeyBindings.keySet()) {
            clearButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClearBtnClicked((ImageButton) view);
                }
            });
        }
    }

    private void onClearBtnClicked(ImageButton button) {
        ConsumptionDataObject.Keys key = clearBtnsWithKeyBindings.get(button);
        getMediator().clearValue(key);
    }

    private void putClearBtnBinding(ConsumptionDataObject.Keys key, ImageButton clearButton) {
        keyWithClearBtnsBindings.put(key, clearButton);
        clearBtnsWithKeyBindings.put(clearButton, key);
    }

    private void updateCalendar() {
        if (calendar == null) {
            calendar = Calendar.getInstance();
        }
        Date date = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        this.dateTextView.setText(format.format(date));
    }

    private boolean editTextModificationFlag;

    private void beginSilentManipulate() {
        this.editTextModificationFlag = true;
    }

    private void endSilentManipulate() {
        this.editTextModificationFlag = false;
    }

    void setValue (ConsumptionDataObject.Keys key, double value) {
        beginSilentManipulate();
        EditText et = keyWithTextEditBindings.get(key);
        et.setText("" + value);
        et.setTextColor(getResources().getColor(R.color.calc_green));
        endSilentManipulate();
    }


    private void initializeEditTexts() {
        createTextEditBindings();

        for (EditText et : textEditWithKeyBindings.keySet()) {
            et.addTextChangedListener(new EditTextsTextWatcher(et));
        }

        endSilentManipulate();
    }

    void clearValue(ConsumptionDataObject.Keys valueKey) {
        beginSilentManipulate();
        keyWithTextEditBindings.get(valueKey).setText("");
        endSilentManipulate();
    }

    Date getCalculationDate() {
        return calendar.getTime();
    }

    private class EditTextsTextWatcher implements TextWatcher {

        ConsumptionDataObject.Keys senderKey;

        EditTextsTextWatcher(EditText origin) {
            this.senderKey = textEditWithKeyBindings.get(origin);
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (editTextModificationFlag) {
                return;
            }
            String valueText;
            double value;

            valueText = editable.toString().replace(",", ".");
            if (valueText.equals("")) {
                getMediator().clearValue(senderKey);
                return;
            }

            try {
                value = Double.parseDouble(valueText);
                keyWithTextEditBindings.get(senderKey).setTextColor(Color.BLACK);
            } catch (Exception e) {
                keyWithTextEditBindings.get(senderKey).setTextColor(Color.RED);
                return;
            }

            getMediator().updateValue(senderKey, value);
        }
    }

    @OnClick(R.id.saveBtn)
    void onSaveClick() {
        save();
    }

    public void save() {
        if (dataObject != null) {
            getMediator().updateDataObject(dataObject);
        } else {
            getMediator().saveCurrentValues();
        }
    }

    String getDescriptionText() {
        String descContent = descriptionET.getText().toString();
        if (descContent != "") {
            return descriptionET.getText().toString();
        } else {
            return null;
        }
    }
}

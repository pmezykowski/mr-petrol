package p.mezykowski.simplefuelcalc.model.consumption;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Created by pawel on 2014-07-30.
 */
public class ConsumptionDataObject {

    public static final long UNSAVED_ITEM_ID = -1;

    private long id;

    private List<Keys> usedValueKeys;

    private HashMap<Keys, Double> values;
    private HashMap<Keys, Boolean> manuallySetFlags;
    private HashMap<Keys, Boolean> setFlags;
    private String description;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    private boolean isSelected;

    private Date date;

    public List<Keys> getUsedValueKeys() {
        return usedValueKeys;
    }

    public enum Keys { Distance, GasVolume, Consumption, Price, TotalCost;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date != null ? date : new Date();
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isUnsavedItem() {
        return this.id == UNSAVED_ITEM_ID;
    }

    public ConsumptionDataObject() {
        id = UNSAVED_ITEM_ID;
        isSelected = false;
        usedValueKeys = new ArrayList<Keys>();
        values = new HashMap<Keys, Double>();
        manuallySetFlags = new HashMap<Keys, Boolean>();
        setFlags = new HashMap<Keys, Boolean>();

        configureValueKeys();
        clear();
    }

    private void configureValueKeys() {
        usedValueKeys.add(Keys.GasVolume);
        usedValueKeys.add(Keys.Distance);
        usedValueKeys.add(Keys.Consumption);
        usedValueKeys.add(Keys.TotalCost);
        usedValueKeys.add(Keys.Price);
    }

    public void clear() {
        clearValues();
        clearFlags();
    }

    private void clearFlags() {

        for (Keys key : usedValueKeys) {
            this.setValueIsSetManually(key, false);
            this.setValueIsSet(key, false);
        }

    }

    private void clearValues() {

        for (Keys key : usedValueKeys) {
            this.values.put(key, 0.0);
        }

    }

    public boolean isValueSetManually(Keys valueKey) {
        return this.manuallySetFlags.get(valueKey);
    }

    public void setValueIsSetManually(Keys valueKey, boolean isSetManually) {
        this.manuallySetFlags.put(valueKey, isSetManually);
    }

    public boolean isValueSet(Keys valueKey) {
        return this.setFlags.get(valueKey);
    }

    public boolean areValuesSet(Keys... valueKeys) {
        for (Keys key : valueKeys) {
            if (isValueSet(key) == false) {
                return false;
            }
        }
        return true;
    }

    public void setValueIsSet(Keys valueKey, boolean valueIsSet) {
        this.setFlags.put(valueKey, valueIsSet);
    }

    public double getValue(Keys valueKey) {
        return this.values.get(valueKey);
    }

    public void setValue(Keys valueKey, double value) {
        this.values.put(valueKey, value);
        this.setValueIsSet(valueKey, true);
    }

    public void clearCalculation() {

        for (Keys key : usedValueKeys) {
            if (!this.isValueSetManually(key)) {
                this.setValueIsSet(key, false);
            }
        }
    }


    public ConsumptionDataObject copy() {
        ConsumptionDataObject state = new ConsumptionDataObject();


        state.values = new HashMap<Keys, Double>(this.values);
        state.usedValueKeys = new ArrayList<Keys>(this.usedValueKeys);
        state.manuallySetFlags = new HashMap<Keys, Boolean>(this.manuallySetFlags);
        state.setFlags = new HashMap<Keys, Boolean>(this.setFlags);

        return state;
    }

    public void cloneValues(ConsumptionDataObject cloneSource) {
        for (Keys key : cloneSource.getUsedValueKeys()) {
            this.setValue(key, cloneSource.getValue(key));
        }
    }


    @Override
    public String toString() {
        return this.getValue(Keys.Distance)+" km,    " +
                this.getValue(Keys.GasVolume) + " l,    "+
                this.getValue(Keys.Consumption) + " l/100 km,    "+
                this.getValue(Keys.Price)+ " PLN/l,    "+
                this.getValue(Keys.TotalCost)+" PLN";
    }

    public String getDescription() {
        return description == null ? "" : description;
    }

}

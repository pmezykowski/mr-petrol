package p.mezykowski.simplefuelcalc.ui.mainView.historyFragment.historyList;

import android.content.Context;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import p.mezykowski.simplefuelcalc.model.consumption.ConsumptionDataObject;
import pl.pawelmezykowski.myapplication.R;

/**
 * Created by pawel on 2014-08-24.
 */
public class HistoryArrayAdapter extends ArrayAdapter<ConsumptionDataObject> {

    private final Context context;
    private final List<ConsumptionDataObject> values;

    public HistoryArrayAdapter(Context context, List<ConsumptionDataObject> objects) {
        super(context, R.layout.history_list_item, objects);
        this.context = context;
        this.values = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;

        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.history_list_item, null);

            HistoryViewHolder holder = new HistoryViewHolder();
            holder.desc = (TextView) rowView.findViewById(R.id.item_desc);
            holder.gasVolume = (TextView) rowView.findViewById(R.id.item_gasVolume);
            holder.distance = (TextView) rowView.findViewById(R.id.item_distance);
            holder.consumption = (TextView) rowView.findViewById(R.id.item_consumption);
            holder.price = (TextView) rowView.findViewById(R.id.item_price);
            holder.cost = (TextView) rowView.findViewById(R.id.item_cost);
            holder.checkedCheckBox = (CheckBox) rowView.findViewById(R.id.checked_checkbox);
            holder.dateDayMonth = (TextView) rowView.findViewById(R.id.day_month);
            holder.dateYear = (TextView) rowView.findViewById(R.id.date_year);
            rowView.setTag(holder);
        }

        HistoryViewHolder holder = (HistoryViewHolder) rowView.getTag();
        ConsumptionDataObject currentObj = values.get(position);
        holder.gasVolume.setText(String.format("%.1f",currentObj.getValue(ConsumptionDataObject.Keys.GasVolume)));
        holder.distance.setText(String.format("%.0f",currentObj.getValue(ConsumptionDataObject.Keys.Distance)));
        holder.consumption.setText(String.format("%.2f",currentObj.getValue(ConsumptionDataObject.Keys.Consumption)));
        holder.price.setText(String.format("%.2f",currentObj.getValue(ConsumptionDataObject.Keys.Price)));
        holder.cost.setText(String.format("%.1f",currentObj.getValue(ConsumptionDataObject.Keys.TotalCost)));
        if (holder.checkedCheckBox != null) {
            holder.checkedCheckBox.setChecked(currentObj.isSelected());
            holder.checkedCheckBox.setVisibility(currentObj.isSelected() ? View.VISIBLE : View.INVISIBLE);
        }
        if (holder.desc != null) {
            holder.desc.setText(currentObj.getDescription());
        }
        Date date = currentObj.getDate();
        if (date == null) {
            holder.dateYear.setText("?");
            holder.dateDayMonth.setText("?");
        } else {
            SimpleDateFormat dayMonthFormat = new SimpleDateFormat("dd.MM");
            SimpleDateFormat yearFormat = new SimpleDateFormat(".yyyy");

            holder.dateYear.setText(yearFormat.format(date));
            holder.dateDayMonth.setText(dayMonthFormat.format(date));
        }


        return rowView;
    }

    static class HistoryViewHolder {
        public TextView dateYear;
        public TextView dateDayMonth;
        public CheckBox checkedCheckBox;
        public TextView desc;
        public TextView gasVolume;
        public TextView distance;
        public TextView consumption;
        public TextView price;
        public TextView cost;
    }
}

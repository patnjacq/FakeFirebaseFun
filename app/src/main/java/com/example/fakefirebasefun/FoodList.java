package com.example.fakefirebasefun;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class FoodList extends ArrayAdapter<FoodItem> {
    private Activity context;
    ArrayList<FoodItem> inventory;

    public FoodList(Activity context, ArrayList<FoodItem> _inventory) {
        super(context, R.layout.activity_layout_food_list, _inventory);
        this.context = context;
        this.inventory = _inventory;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_layout_food_list, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        TextView textViewType = (TextView) listViewItem.findViewById(R.id.dateString);

        FoodItem food = inventory.get(position);
        textViewName.setText(food.getName());
        textViewType.setText(food.getFoodType().toString());

        return listViewItem;
    }

}
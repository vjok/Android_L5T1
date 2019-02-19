package com.example.predator.l5t1;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class PartArrayAdapter extends ArrayAdapter<Part> {

    static final int VIEW_TYPE_WORKOUT= 0;
    static final int VIEW_TYPE_PAUSE = 1;
    static final int VIEW_TYPE_COUNT = 2;

    public PartArrayAdapter(Context context, ArrayList<Part> parts)
    {
        super(context,0,parts);
    }

    @Override
    public int getViewTypeCount() {
        return VIEW_TYPE_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        Part part = getItem(position);

        if(part instanceof Workout)
        {
            return VIEW_TYPE_WORKOUT;
        }
        else
        {
            return  VIEW_TYPE_PAUSE;
        }
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Part part = getItem(position);

        if(convertView == null)
        {
            int layoutId = 0;
            if(getItemViewType(position) == VIEW_TYPE_WORKOUT)
            {
                layoutId = R.layout.workout_list_item;
            }
            else
            {
                layoutId = R.layout.pause_list_item;
            }
            convertView = LayoutInflater.from(getContext()).inflate(layoutId,parent,false);
        }


        TextView partText = convertView.findViewById(R.id.part_name);
        partText.setText(part.getName() + "\n" + part.getTime());

        return convertView;
    }
}

package com.salome.tagthebus;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by 54088 on 21/04/2018.
 */

public class CustomPictureAdapter extends ArrayAdapter<PictureInBdd>{
    private Context context;
    private ArrayList<PictureInBdd> picturesInBdd;

    public CustomPictureAdapter(Context ctx, ArrayList<PictureInBdd> _picturesInBdd){
        super(ctx, 0, _picturesInBdd);
        this.context = ctx;
        this.picturesInBdd = _picturesInBdd;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final PictureInBdd pictureInBdd = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.picture_item_layout,
                    parent, false);
        }
        TextView titles = (TextView) convertView.findViewById(R.id.BusStopStreetName);
        titles.setText(pictureInBdd.getTitle());

        return convertView;
    }

}

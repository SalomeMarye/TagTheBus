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

public class CustomBusAdapter extends ArrayAdapter<BusStop>{
    private Context context;
    private ArrayList<BusStop> busStops;

    public CustomBusAdapter(Context ctx, ArrayList<BusStop> _busStops){
        super(ctx, 0, _busStops);
        this.context = ctx;
        this.busStops = _busStops;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BusStop busStop = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.bus_stop_item_layout,
                    parent, false);
        }
        TextView titles = (TextView) convertView.findViewById(R.id.BusStopStreetName);
        titles.setText(busStop.streetName);

        /*titles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditingActivity_.class);
                Bundle bundle = new Bundle();
                bundle.putInt("Id", passedTask.id);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });*/

        return convertView;
    }

}

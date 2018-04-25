package com.salome.tagthebus;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

@EActivity
public class MainActivity extends AppCompatActivity {

    @ViewById
    ListView busStopListView;

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        handler = new Handler(Looper.getMainLooper());
        getBusStops(this);
    }

    public void getBusStops (final Context context){
        Request request = new Request.Builder()
                .url("http://barcelonaapi.marcpous.com/bus/stations.json")
                .build();

        BarcelonaRequest.getINSTANCE().SendRequest(request, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final ArrayList<BusStop> busStopsList = new ArrayList<BusStop>();

                try {
                    JSONObject data = new JSONObject(response.body().string());
                    JSONArray items = data.getJSONObject("data").getJSONArray("tmbs");

                    for(int i=0; i<items.length() ;i++) {
                        JSONObject item = items.getJSONObject(i);

                        int id = item.getInt("id");
                        String name = item.getString("street_name");

                        busStopsList.add(new BusStop(id, name));
                    }
                }
                catch (JSONException i){
                    i.printStackTrace();
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        CustomBusAdapter adapter = new CustomBusAdapter(context, busStopsList);
                        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                        busStopListView.setItemsCanFocus(true);
                        busStopListView.setAdapter(adapter);
                    }
                });
            }
        });
    }

}

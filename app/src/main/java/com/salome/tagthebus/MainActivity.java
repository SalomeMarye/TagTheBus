package com.salome.tagthebus;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import org.androidannotations.annotations.EActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@EActivity
public class MainActivity extends AppCompatActivity {

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        handler = new Handler(Looper.getMainLooper());
        testing();
    }

    public void testing (){
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
                try {
                    JSONObject data = new JSONObject(response.body().string());
                    JSONArray items = data.getJSONObject("data").getJSONArray("tmbs");

                    System.out.println(items);
                    for(int i=0; i<items.length() ;i++) {
                        JSONObject item = items.getJSONObject(i);
                        System.out.println(item);
                    }
                }
                catch (JSONException i){
                    i.printStackTrace();
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                    }
                });
            }
        });
    }

}

package com.salome.tagthebus;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

@EActivity
public class ABusStopActivity extends AppCompatActivity {

    @ViewById
    ListView pictureListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abus_stop);
        Bundle bundle = getIntent().getExtras();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(bundle.getString("BusStopName"));
        setSupportActionBar(toolbar);

        PictureBdd pictureBdd = new PictureBdd(this);
        pictureBdd.open();

        ArrayList<PictureInBdd> for_list = pictureBdd.getAllPictureFromBusStopId(bundle.getInt("Id"));

        CustomPictureAdapter adapter = new CustomPictureAdapter(this, for_list);
        pictureListView.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}

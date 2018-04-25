package com.salome.tagthebus;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@EActivity
public class ABusStopActivity extends AppCompatActivity {

    private String busStopName;
    private int busStopId;

    private Date currentDatePicture;
    private String currentPathPicture;

    private PictureBdd pictureBdd;

    @ViewById
    ListView pictureListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abus_stop);
        Bundle bundle = getIntent().getExtras();

        busStopName = bundle.getString("BusStopName");
        busStopId = bundle.getInt("BusStopID");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(busStopName);
        setSupportActionBar(toolbar);

        pictureBdd = new PictureBdd(this);
        pictureBdd.open();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });

        updateListView();
    }

    private void updateListView()
    {
        ArrayList<PictureInBdd> for_list = pictureBdd.getAllPictureFromBusStopId(busStopId);

        CustomPictureAdapter adapter = new CustomPictureAdapter(this, for_list);
        pictureListView.setAdapter(adapter);
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            Uri file = Uri.fromFile(getFile());
            intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, file);
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private File getFile() {
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        currentDatePicture = new Date();

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(currentDatePicture);
        String imageFileName = "JPEG_"+ timeStamp + "_";

        File image = null;
        try {
            image = File.createTempFile(
                    imageFileName,
                    ".jpg",
                    storageDir
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        currentPathPicture = image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(ABusStopActivity.this);
                Camera.CameraInfo cameraInfo = new Camera.CameraInfo();

                final boolean isFrontCamera = cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT;

                alertDialog.setTitle("Titre");
                alertDialog.setMessage("Entrer le Titre");

                final EditText input = new EditText(ABusStopActivity.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                alertDialog.setView(input);

                alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        PictureInBdd newPicture = new PictureInBdd(
                                input.getText().toString(),
                                busStopId,
                                currentPathPicture,
                                currentDatePicture,
                                isFrontCamera);
                        pictureBdd.insertPicture(newPicture);

                        updateListView();
                    }
                });

                alertDialog.show();
            }
        }
    }
}

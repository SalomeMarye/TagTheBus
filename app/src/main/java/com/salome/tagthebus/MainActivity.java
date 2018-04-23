package com.salome.tagthebus;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@EActivity
public class MainActivity extends AppCompatActivity {

    /*@ViewById
    ImageView imageView;*/

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

        //dispatchTakePictureIntent();
        //setPic("/storage/emulated/0/Android/data/com.salome.tagthebus/files/Pictures/JPEG_20180422_001742_481621314.jpg");
    }

    /*static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri file = Uri.fromFile(getFile());
        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, file);
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }*/

    /*private File getFile() {
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_"+ timeStamp + "_";

        File image = null;
        try {
            image = File.createTempFile(
                    imageFileName,  /* prefix */
                    /*".jpg",*/         /* suffix */
                    /*storageDir*/      /* directory */
       /*     );
        } catch (IOException e) {
            e.printStackTrace();
        }


        System.out.println(image.getAbsolutePath());
        return image;
    }*/
    ///storage/emulated/0/Android/data/com.salome.tagthebus/files/Pictures/JPEG_20180422_001742_481621314.jpg
    /*private void setPic(String path) {
        // Get the dimensions of the View
        int targetW = imageView.getMeasuredWidth();
        int targetH = imageView.getMeasuredHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(path, bmOptions);
        imageView.setImageBitmap(bitmap);
    }*/

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                System.out.println("ok");
            }
        }
    }*/

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

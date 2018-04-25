package com.salome.tagthebus;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
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
        TextView titles = (TextView) convertView.findViewById(R.id.PictureTitle);
        titles.setText(pictureInBdd.getTitle());

        TextView creationDate = (TextView)convertView.findViewById(R.id.PictureCreationDate);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d/MMM/yyyy \n HH:mm");
        String dateToShow = simpleDateFormat.format(pictureInBdd.getCreationDate());
        creationDate.setText(dateToShow);

        ImageView picture = (ImageView)convertView.findViewById(R.id.pictureImageView);
        setPic(pictureInBdd, picture);

        return convertView;
    }

    private void setPic(PictureInBdd pictureInBdd, ImageView picture) {
        Bitmap bitmap = BitmapFactory.decodeFile(pictureInBdd.getPathToPicture());
        picture.setImageBitmap(bitmap);
        int pictureW = picture.getDrawable().getIntrinsicWidth();
        int pictureH = picture.getDrawable().getIntrinsicHeight();

        int angle = pictureInBdd.getIsFrontCamera() ? 0 : 180;

        if (pictureH < pictureW)
            picture.setRotation(angle - 90);
        else
            picture.setRotation(angle);
    }
}

package com.salome.tagthebus;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by 54088 on 23/04/2018.
 */

public class PictureBdd {

    private static final int BDD_VERSION = 1;
    private static String BDD_NAME = "pictures.db";

    private static final String PICTURE_TABLE = "picture_table";

    private static final String COL_ID = "Id";
    private static final int NUM_ID_COL = 0;

    private static final String COL_BUS_STOP_ID = "BusStopId";
    private static final int NUM_BUS_STOP_ID_COL = 1;

    private static final String COL_TITLE = "Title";
    private static final int NUM_TITLE_COL = 2;

    private static final String COL_CREATION_DATE = "CreationDate";
    private static final int NUM_CREATION_DATE_COL = 3;

    private static final String COL_PICTURE_PATH = "PicturePath";
    private static final int NUM_PICTURE_PATH_COL = 4;

    private static final String COL_IS_FRONT_CAMERA = "FrontCamera";
    private static final int NUM_IS_FRONT_CAMERA_COL = 5;

    private SQLiteDatabase database;
    private MySqlLiteBase mySqlLiteBase;

    public PictureBdd(Context context)
    {
        mySqlLiteBase = new MySqlLiteBase(context, BDD_NAME, null, BDD_VERSION);
    }

    public void open()
    {
        database = mySqlLiteBase.getWritableDatabase();
    }

    public void close()
        {
            database.close();
        }

    public SQLiteDatabase getDatabase()
        {
            return database;
        }

    public long insertPicture(PictureInBdd newPictureInBdd)
    {
        ContentValues newPictureValues = new ContentValues();

        newPictureValues.put(COL_TITLE, newPictureInBdd.getTitle());
        newPictureValues.put(COL_CREATION_DATE, String.valueOf(newPictureInBdd.getCreationDate().getTime()));
        newPictureValues.put(COL_BUS_STOP_ID, newPictureInBdd.getBusStopId());
        newPictureValues.put(COL_PICTURE_PATH, newPictureInBdd.getPathToPicture());
        newPictureValues.put(COL_IS_FRONT_CAMERA, newPictureInBdd.getIsFrontCamera() ? 1 : 0);

        return database.insert(PICTURE_TABLE, null, newPictureValues);
    }

    public long updatePicture(int id, PictureInBdd updatePictureInBdd)
    {
        ContentValues updateValues = new ContentValues();

        updateValues.put(COL_TITLE, updatePictureInBdd.getTitle());
        updateValues.put(COL_CREATION_DATE, String.valueOf(updatePictureInBdd.getCreationDate().getTime()));

        return database.update(PICTURE_TABLE, updateValues, COL_ID + " = " + id , null);
    }

    public int removePictureWithId(int id)
    {
        return database.delete(PICTURE_TABLE, COL_ID + " = " + id, null);
    }

    private PictureInBdd cursorToPicture(Cursor cursor)
    {
        if (cursor.getCount() == 0)
            return null;

        cursor.moveToFirst();

        PictureInBdd pictureInBdd = new PictureInBdd(cursor.getString(NUM_TITLE_COL),
                cursor.getInt(NUM_BUS_STOP_ID_COL),
                cursor.getString(NUM_PICTURE_PATH_COL),
                cursor.getString(NUM_CREATION_DATE_COL),
                cursor.getInt(NUM_IS_FRONT_CAMERA_COL) == 1);
        pictureInBdd.setId(cursor.getInt(NUM_ID_COL));

        cursor.close();
        return pictureInBdd;
    }

    public PictureInBdd getPictureWithTitle(String title)
    {
        Cursor cursor = database.query(PICTURE_TABLE, new String[] {COL_ID, COL_TITLE, COL_CREATION_DATE}
        , COL_TITLE + "LIKE\"" + title + "\"", null, null, null, null);
        return cursorToPicture(cursor);
    }

    public PictureInBdd getPictureWithId(int _id)
    {
        Cursor cursor = database.query(PICTURE_TABLE, new String[] {COL_ID, COL_TITLE, COL_CREATION_DATE},
                COL_ID + " = " + _id, null, null , null, null);
        return cursorToPicture(cursor);
    }

    public ArrayList<PictureInBdd> getAllPictureFromBusStopId(int _busStopId)
    {
        ArrayList<PictureInBdd> to_return = new ArrayList<PictureInBdd>();
        System.out.println(COL_BUS_STOP_ID);
        System.out.println(_busStopId);
        Cursor cursor = database.query(PICTURE_TABLE, new String[] {COL_ID, COL_BUS_STOP_ID, COL_TITLE, COL_CREATION_DATE, COL_PICTURE_PATH, COL_IS_FRONT_CAMERA},
                COL_BUS_STOP_ID + " = " + _busStopId, null, null, null, COL_CREATION_DATE + " DESC");

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
        {
            PictureInBdd picture = new PictureInBdd(cursor.getString(NUM_TITLE_COL),
                    cursor.getInt(NUM_BUS_STOP_ID_COL),
                    cursor.getString(NUM_PICTURE_PATH_COL),
                    cursor.getString(NUM_CREATION_DATE_COL),
                    cursor.getInt(NUM_IS_FRONT_CAMERA_COL) == 1);
            to_return.add(picture);
        }
        return to_return;
    }

}

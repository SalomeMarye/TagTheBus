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

    private SQLiteDatabase database;
    private MySqlLiteBase mySqlLiteBase;

    public PictureBdd(Context context)
    {
        mySqlLiteBase = new MySqlLiteBase(context, BDD_NAME, null, BDD_VERSION);
    }

    public void open()
    {
        database = mySqlLiteBase.getWritableDatabase();
        //mySqlliteBase.dropDB(database);
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
        ContentValues newTaskValues = new ContentValues();

        newTaskValues.put(COL_TITLE, newPictureInBdd.getTitle());
        newTaskValues.put(COL_CREATION_DATE, String.valueOf(newPictureInBdd.getCreationDate().getTime()));

        return database.insert(PICTURE_TABLE, null, newTaskValues);
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

        PictureInBdd pictureInBdd = new PictureInBdd(cursor.getString(NUM_TITLE_COL), cursor.getInt(NUM_BUS_STOP_ID_COL));
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
                COL_ID + " = " + _id + ' ', null, null , null, null);
        return cursorToPicture(cursor);
    }

    public ArrayList<PictureInBdd> getAllPictureFromBusStopId(int _busStopId)
    {
        ArrayList<PictureInBdd> to_return = new ArrayList<PictureInBdd>();
        Cursor cursor = database.query(PICTURE_TABLE, new String[] {COL_ID, COL_BUS_STOP_ID, COL_TITLE, COL_CREATION_DATE},
                COL_BUS_STOP_ID + " = " + _busStopId + ' ', null, null, null, COL_CREATION_DATE);

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
        {
            PictureInBdd picture = new PictureInBdd(cursor.getString(NUM_TITLE_COL), cursor.getInt(NUM_BUS_STOP_ID_COL));
            to_return.add(picture);
        }
        return to_return;
    }

}

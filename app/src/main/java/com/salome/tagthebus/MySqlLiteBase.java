package com.salome.tagthebus;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 54088 on 23/04/2018.
 */

public class MySqlLiteBase extends SQLiteOpenHelper
{
    private static final String PICTURE_TABLE = "picture_table";
    private static final String COL_ID = "Id";
    private static final String COL_BUS_STOP_ID = "BusStopId";
    private static final String COL_TITLE = "Title";
    private static final String COL_CREATION_DATE = "CreationDate";
    private static final String COL_PATH_TO_PICTURE = "PicturePath";
    private static final String COL_FRONT_CAMERA = "FrontCamera";

    private static final String CREATE_BDD = "CREATE TABLE "
            + PICTURE_TABLE + " ("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_BUS_STOP_ID + " INTEGER NOT NULL, "
            + COL_TITLE + " TEXT NOT NULL, "
            + COL_CREATION_DATE + " TEXT NOT NULL,"
            + COL_PATH_TO_PICTURE + " TEXT NOT NULL,"
            + COL_FRONT_CAMERA + " INTEGER NOT NULL);";

    public MySqlLiteBase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase database)
        {
            database.execSQL(CREATE_BDD);
        }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion)
    {
        database.execSQL("DROP TABLE " + PICTURE_TABLE + ";");
        onCreate(database);
    }

    public void dropDB(SQLiteDatabase database)
    {
        database.execSQL("DROP TABLE " + PICTURE_TABLE + ";");
        onCreate(database);
    }
}

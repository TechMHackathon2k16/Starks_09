package com.example.hemantsaini.myresult;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Hemant Saini on 05-06-2016.
 */
public class DatabaseAdapterSubject extends SQLiteOpenHelper {



    public static final String DATABASE_NAME = "Databasename2";
    public static final String DATABASE_TABLE = "Databasetable2";
    public static final String SUBJECT = "SUBJECT";
    public static final String SEMESTER = "SEMESTER";
    public static final String LINK = "LINK";
    private static final String UID = "_id";
    private static final int VERSION = 1;

    private static final String DATABASE_Create = "CREATE TABLE " + DATABASE_TABLE + "("+UID+" INT PRIMARY KEY, "+SUBJECT+" VARCHAR(255),"+LINK+" VARCHAR(255),"+SEMESTER+" VARCHAR(255));";
    private static final String DROP_TABLE = "DROP TABLE "+DATABASE_TABLE+" IF EXISTS";



    public DatabaseAdapterSubject(Context context) {
        super(context, DATABASE_NAME,null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.d("hemant", "oncreate of subjefefnefik elper called");
        try {
            db.execSQL(DATABASE_Create);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("hemant", "onUpgrade of database helper called");
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }
}

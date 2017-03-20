package com.tourinho.fiap.filmes.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseSetup extends SQLiteOpenHelper {



    public static final String TABLE_MOVIE = "movies";
    public static final String MOVIE_NAME = "name";


    public static final String TABLE_USER = "users";
    public static final String USER_NAME = "user";
    public static final String USER_PASS = "pass";
    public static final String DATABASE_NAME = "movieli.db";
    public static final int DATABASE_VERSION = 1;

    public static final String CREATE_USER_TABLE = "create table " + TABLE_USER + " ( " +
            USER_NAME + " text not null, " +
            USER_PASS + " text not null);";

    public static final String CREATE_MOVIE_TABLE = "create table " + TABLE_MOVIE + " ( " +
            MOVIE_NAME + " text not null);";

    public DatabaseSetup(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_MOVIE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIE);
        onCreate(db);
    }
}

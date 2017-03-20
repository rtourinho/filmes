package com.tourinho.fiap.filmes.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;


public class MovieDAO {
    private SQLiteDatabase database;
    private DatabaseSetup baseDAO;

    private String[] columns = {DatabaseSetup.MOVIE_NAME
    };

    public MovieDAO(Context context) {
        baseDAO = new DatabaseSetup(context);
        open();
    }


    public void open() throws SQLException {
        database = baseDAO.getWritableDatabase();
    }

    public void close() {
        baseDAO.close();
    }


    public List<String> getAll() {
        List<String> movies = new ArrayList<>();
        Cursor cursor = database.query(DatabaseSetup.TABLE_MOVIE, columns, null, null, null, null, null);
        if (cursor .moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String name = cursor.getString(0);
                movies.add(name);
                cursor.moveToNext();
            }
        }

        return movies;
    }
    public long create(String name) {
        ContentValues values = new ContentValues();

        values.put(DatabaseSetup.MOVIE_NAME, name);
        return database.insert(DatabaseSetup.TABLE_MOVIE, null, values);
    }

    public void delete(String name) {
        database.delete(DatabaseSetup.TABLE_MOVIE,"name = '" + name + "'",null);
    }

    public void update(String oldName, String name) {
        ContentValues cv = new ContentValues();
        cv.put(DatabaseSetup.MOVIE_NAME,name);
        database.update(DatabaseSetup.TABLE_MOVIE,cv,"name = '" + oldName + "'", null);
    }
}
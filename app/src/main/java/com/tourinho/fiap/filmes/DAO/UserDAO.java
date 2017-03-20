package com.tourinho.fiap.filmes.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


public class UserDAO {
    private SQLiteDatabase database;
    private DatabaseSetup baseDAO;

    private String[] columns = {DatabaseSetup.USER_NAME,
            DatabaseSetup.USER_PASS
    };

    public UserDAO(Context context) {
        baseDAO = new DatabaseSetup(context);
        open();
    }


    public void open() throws SQLException {
        database = baseDAO.getWritableDatabase();
    }

    public void close() {
        baseDAO.close();
    }

    public boolean login(String login, String password)
    {
        Cursor cursor = database.query(DatabaseSetup.TABLE_USER, columns, DatabaseSetup.USER_NAME + " = '" + login + "' AND "  + DatabaseSetup.USER_PASS + " = '" + password + "'", null, null, null, null);
         boolean success = cursor.getCount() == 1;
        cursor.close();
        return success;
    }

    public long create(String login, String password) {
        ContentValues values = new ContentValues();

        values.put(DatabaseSetup.USER_NAME, login);
        values.put(DatabaseSetup.USER_PASS, password);
        return database.insert(DatabaseSetup.TABLE_USER, null, values);
    }


    public void clearAll(){
        database.execSQL("DELETE FROM " + DatabaseSetup.TABLE_USER);
    }

}
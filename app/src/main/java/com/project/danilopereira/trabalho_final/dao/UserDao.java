package com.project.danilopereira.trabalho_final.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.project.danilopereira.trabalho_final.model.User;

/**
 * Created by danilopereira on 01/04/17.
 */

public class UserDao {
    public static final String TABLE_USER = "`user`";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PASSWORD = "password";

    private SQLiteDatabase db;
    private DBOpenHelper openHelper;

    public UserDao(Context context){
        openHelper = new DBOpenHelper(context);
    }

    public void add(User user){
        long resultado = 0;
        db = openHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME, user.getName());
        values.put(COLUMN_PASSWORD, user.getPassword());

        resultado = db.insert(TABLE_USER, null, values);

        if(resultado == -1){
            Log.e(this.getClass().getSimpleName(), "ERROR: user not added");
        }else{
            Log.d(this.getClass().getSimpleName(), "SUCCESS: user added successful");
        }

        db.close();
    }

    public User findByUsernameAndPassword(String name, String password){
        db = openHelper.getReadableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_NAME};
        String where = "name = '" + name + "' and password = '" + password + "'";
        Cursor cursor = db.query(true, TABLE_USER, columns, where, null, null, null, null, null);

        User user = null;

        if(cursor!=null){
            cursor.moveToFirst();

            user = new User();
            user.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
            user.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
        }

        return user;
    }


}

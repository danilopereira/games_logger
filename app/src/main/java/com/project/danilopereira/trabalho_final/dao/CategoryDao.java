package com.project.danilopereira.trabalho_final.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.project.danilopereira.trabalho_final.model.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by danilopereira on 14/04/17.
 */

public class CategoryDao {
    public final static String TABLE_CATEGORY = "category";
    public final static String COLUMN_ID = "id";
    public final static String COLUMN_NAME = "name";

    private SQLiteDatabase db;
    private DBOpenHelper openHelper;

    public CategoryDao(Context context){
        this.openHelper = new DBOpenHelper(context);
    }

    public List<Category> list(){
        List<Category> categories = new ArrayList<>();

        String rawQuery = "SELECT * FROM " + TABLE_CATEGORY;
        db = openHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(rawQuery, null);

        Category category = null;
        if(cursor.moveToFirst()){
            do{
                category = new Category();
                category.setId(cursor.getInt(0));
                category.setName(cursor.getString(1));
                categories.add(category);
            }
            while(cursor.moveToNext());
        }

        db.close();

        return categories;
    }

    public Category findById(int id) {
        db = openHelper.getReadableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_NAME};
        String where = "id = "+id;
        Cursor cursor = db.query(true, TABLE_CATEGORY, columns, where, null, null, null, null, null);

        Category category = null;

        if(cursor != null){
            cursor.moveToFirst();
            category = new Category();
            category.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
            category.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
        }

        db.close();
        return category;
    }
}

package com.project.danilopereira.trabalho_final.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.project.danilopereira.trabalho_final.model.Category;
import com.project.danilopereira.trabalho_final.model.Game;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by danilopereira on 14/04/17.
 */

public class GameDao {
    public final static String TABLE_GAME = "game";
    public final static String COLUMN_ID = "id";
    public final static String COLUMN_NAME = "name";
    public final static String COLUMN_COVER = "cover";
    public final static String COLUMN_CATEGORY_ID = "category_id";

    private SQLiteDatabase db;
    private DBOpenHelper openHelper;

    public GameDao(Context context){
        this.openHelper = new DBOpenHelper(context);
    }

    public String add(Game game){
        long result;

        db = openHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME, game.getName());
        values.put(COLUMN_CATEGORY_ID, game.getCategory().getId());
        values.put(COLUMN_COVER, game.getCover());

        result = db.insert(TABLE_GAME, null, values);

        db.close();

        if(result == -1){
            return "ERROR to insert user";
        }
        else{
            return "SUCCESS";
        }
    }

    public List<Game> list(){
        List<Game> games = new ArrayList<>();

        String rawQuery = "SELECT g.*, c.* FROM " + TABLE_GAME +" g INNER JOIN " + CategoryDao.TABLE_CATEGORY + " c ON g."+ COLUMN_CATEGORY_ID +
                " = c." + CategoryDao.COLUMN_ID + " ORDER BY " + COLUMN_ID + " ASC";

        db = openHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(rawQuery, null);

        Game game = null;

        if(cursor.moveToFirst()){
            do{
                game = new Game();
                game.setId(cursor.getInt(0));
                game.setName(cursor.getString(1));
                game.setCover(cursor.getString(2));
                game.setCategory(new Category(cursor.getInt(4), cursor.getString(5)));
                games.add(game);
            }
            while(cursor.moveToNext());
        }

        db.close();

        return games;
    }

    public int delete(int id){
        db = openHelper.getReadableDatabase();
        int result = db.delete(TABLE_GAME, "id=" + id, null);
        db.close();
        return result;
    }

    public void update(Game game) {
    }
}

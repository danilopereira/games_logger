package com.project.danilopereira.trabalho_final.dao;

import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.project.danilopereira.trabalho_final.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by danilopereira on 01/04/17.
 */
public class DBOpenHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DB_NAME = "games.db";
    private static final int VERSION = 1;


    public DBOpenHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        readScript(db, context, R.raw.create_db);

        readScript(db, context, R.raw.insert_values);

    }

    private void readScript(SQLiteDatabase db, Context context, int resourceId) {
        Resources res = context.getResources();
        InputStream is = null;
        BufferedReader reader = null;
        try {
            is = res.openRawResource(resourceId);
            reader = new BufferedReader(new InputStreamReader(is));

            executeSqlScript(db, reader);
        } catch (Exception e) {
            Log.e(this.getClass().getSimpleName(), e.getMessage());
        } finally {
            try {
                is.close();
                reader.close();
            } catch (IOException e) {
                Log.e(this.getClass().getSimpleName(), e.getMessage());
            }
        }
    }

    private void executeSqlScript(SQLiteDatabase db, BufferedReader reader) throws IOException {
        String line;
        StringBuilder statement = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            statement.append(line);
            statement.append("\n");
            if (line.endsWith(";")) {
                String toExec = statement.toString();
                log("Executing script: " + toExec);
                db.execSQL(toExec);
                statement = new StringBuilder();
            }
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for(int i = oldVersion; i < newVersion; ++i) {
            String migrationFileName = String.format("from_%d_to_%d", i, (i+1));
            log("Looking for migration file: " + migrationFileName);
            int migrationFileResId = context.getResources()
                    .getIdentifier(migrationFileName, "raw", context.getPackageName());
            if(migrationFileResId != 0) {
                // execute script
                log("Found, executing");
                readScript(db, context, migrationFileResId);
            } else {
                log("Not found!");
            }
        }
    }

    private void log(String message){
        Log.d(this.getClass().getSimpleName(), message);
    }
}

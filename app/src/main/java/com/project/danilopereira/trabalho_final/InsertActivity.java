package com.project.danilopereira.trabalho_final;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.project.danilopereira.trabalho_final.dao.CategoryDao;
import com.project.danilopereira.trabalho_final.dao.GameDao;
import com.project.danilopereira.trabalho_final.model.Category;
import com.project.danilopereira.trabalho_final.model.Game;

import java.util.List;

public class InsertActivity extends AppCompatActivity {
    public static final int CODE_NEW_GAME= 1002;

    private static int RESULT_LOAD_IMG = 1;
    String imgDecodableString;

    private ImageView ivCover;
    private TextInputLayout tilName;
    private Spinner spCategory;
    private Button btnSave;

    private CategoryDao categoryDao;
    private GameDao gameDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        categoryDao = new CategoryDao(this);
        gameDao = new GameDao(this);

        ivCover = (ImageView) findViewById(R.id.ivInsertCover);
        tilName = (TextInputLayout) findViewById(R.id.tilInsertName);
        spCategory = (Spinner) findViewById(R.id.spCategory);
        btnSave = (Button) findViewById(R.id.btnSave);

        ivCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create intent to Open Image applications like Gallery, Google Photos
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                // Start the Intent
                startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
            }
        });

        List<Category> categories = categoryDao.list();
        ArrayAdapter<Category> categoryArrayAdapter = new ArrayAdapter<Category>(getApplicationContext(), R.layout.category_item, categories);
        categoryArrayAdapter.setDropDownViewResource(R.layout.category_item);
        spCategory.setAdapter(categoryArrayAdapter);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveGame();
            }
        });


        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            final Game game = (Game)bundle.get("game");
            ivCover.setImageBitmap(BitmapFactory
                    .decodeFile(game.getCover()));
            tilName.getEditText().setText(game.getName());

            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateGame(game);
                }
            });

        }

    }

    private void updateGame(Game game) {
        insertValues(game);
        gameDao.update(game);

        backToLastScreen();
    }

    private void saveGame() {
        Game game = new Game();
        insertValues(game);

        gameDao.add(game);

        backToLastScreen();
    }

    private void insertValues(Game game){
        if(imgDecodableString != null){
            game.setCover(imgDecodableString);
        }
        game.setName(tilName.getEditText().getText().toString());
        game.setCategory(categoryDao.findById(((Category)spCategory.getSelectedItem()).getId()));
    }

    private void backToLastScreen() {
        Intent intent = new Intent();
        setResult(CODE_NEW_GAME, intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                // Set the Image in ImageView after decoding the String
                ivCover.setImageBitmap(BitmapFactory
                        .decodeFile(imgDecodableString));

            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Log.e(this.getClass().getSimpleName(), e.getMessage());
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

    }
}

package com.project.danilopereira.trabalho_final;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.project.danilopereira.trabalho_final.dao.UserDao;
import com.project.danilopereira.trabalho_final.model.User;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SplashScreenActivity extends AppCompatActivity {

    private final static String URL = "http://www.mocky.io/v2/58b9b1740f0000b614f09d2f";
    private final int SPLASH_DISPLAY_LENGTH = 4500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        
        loadAnimation();

        new SearchData().execute(URL);
    }

    private void loadAnimation() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.animation_splash);

        animation.reset();

        ImageView iv = (ImageView) findViewById(R.id.splash);
        if (iv != null) {
            iv.clearAnimation();
            iv.startAnimation(animation);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this,
                        LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                SplashScreenActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }


    private class SearchData extends AsyncTask<String, Void, String>{
        private UserDao userDao;

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();

                connection.setReadTimeout(15000);
                connection.setConnectTimeout(10000);

                connection.setRequestMethod("GET");

                connection.setDoOutput(true);

                if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                    InputStream is = connection.getInputStream();
                    BufferedReader buffer = new BufferedReader(new InputStreamReader(is));

                    StringBuilder result = new StringBuilder();
                    String line;
                    while((line = buffer.readLine()) != null){
                        result.append(line);
                    }

                    return result.toString();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                connection.disconnect();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            userDao = new UserDao(SplashScreenActivity.this);
            if(s == null){
                Log.e(this.getClass().getSimpleName(), "ERROR: not possible to load content");
            }
            else{
                try {
                    JSONObject json = new JSONObject(s);
                    User user = new User();
                    user.setName(json.getString("usuario"));
                    user.setPassword(json.getString("senha"));

                    userDao.add(user);

                }
                catch (Exception e){
                    Log.e(this.getClass().getSimpleName(), e.getMessage());
                }
            }
        }
    }


}

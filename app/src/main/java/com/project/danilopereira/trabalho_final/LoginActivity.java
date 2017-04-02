package com.project.danilopereira.trabalho_final;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.project.danilopereira.trabalho_final.dao.UserDao;
import com.project.danilopereira.trabalho_final.model.User;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout tilLogin;
    private TextInputLayout tilPassword;
    private CheckBox cbConnected;
    private Button btLogin;

    private UserDao userDao;

    private final String KEY_APP_PREFERENCES = "login";
    private final String KEY_LOGIN = "login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tilLogin = (TextInputLayout) findViewById(R.id.tilLogin);
        tilPassword = (TextInputLayout) findViewById(R.id.tilPassword);
        cbConnected = (CheckBox) findViewById(R.id.cbConectado);
        btLogin = (Button) findViewById(R.id.btLogin);

        userDao = new UserDao(this);

        if(isConnected()){
            startApp();
        }
        else{
            btLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isValidLogin()){
                        if(cbConnected.isChecked()){
                            keepConnected();
                        }
                        startApp();
                    }
                }
            });
        }
    }

    private boolean isValidLogin() {
        String login = tilLogin.getEditText().getText().toString();
        String password = tilPassword.getEditText().getText().toString();

        User user = userDao.findByUsernameAndPassword(login, password);

        if(user != null){
            return true;
        }

        return false;

    }

    private void startApp() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void keepConnected(){
        String login = tilLogin.getEditText().getText().toString();
        SharedPreferences pref = getSharedPreferences(KEY_APP_PREFERENCES,
                MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(KEY_LOGIN, login);
        editor.apply();
    }

    private boolean isConnected() {
        SharedPreferences shared = getSharedPreferences(KEY_APP_PREFERENCES,MODE_PRIVATE);
        String login = shared.getString(KEY_LOGIN, "");
        if(login.equals(""))
            return false;
        else
            return true;
    }
}

package com.tourinho.fiap.filmes;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.tourinho.fiap.filmes.DAO.UserDAO;

public class LoginActivity extends Activity {


    private EditText mLoginView;
    private EditText mPasswordView;
    private CheckBox rememberCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("Preferences", 0);
        boolean auto_login = sharedPref.getBoolean("remember_me", false);
        if(auto_login)
            redirect();

        mLoginView = (EditText) findViewById(R.id.login);
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
            if (id == R.id.login || id == EditorInfo.IME_NULL) {
                attemptLogin();
                return true;
            }
            return false;
            }
        });

        rememberCheck = (CheckBox) findViewById(R.id.remember_me);
        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

    }

    private void redirect() {
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        LoginActivity.this.finish();
        startActivity(i);
    }

    private void attemptLogin() {
        mLoginView.setError(null);
        mPasswordView.setError(null);

        String login = mLoginView.getText().toString();
        String password = mPasswordView.getText().toString();
        UserDAO dao = new UserDAO(getApplicationContext());
        if (dao.login(login, password))
        {
            SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("Preferences", 0);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putBoolean("remember_me",  rememberCheck.isChecked());
            editor.apply();
            editor.commit();

            redirect();
        }
        else {
            mPasswordView.setError(getString(R.string.error_incorrect_password));
            mPasswordView.requestFocus();
        }
    }


}


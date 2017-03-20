package com.tourinho.fiap.filmes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.tourinho.fiap.filmes.DAO.DatabaseSetup;
import com.tourinho.fiap.filmes.DAO.UserDAO;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class SplashActivity extends AppCompatActivity {

    LinearLayout splashLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        splashLayout = (LinearLayout)findViewById(R.id.layout_splash);
        DatabaseSetup setup = new DatabaseSetup(getApplicationContext());
        UserDAO dao = new UserDAO(getApplicationContext());
        dao.clearAll();
        updateUser();
        fadeIn();




    }
    void updateUser() {

        new Thread() {
            @Override
            public void run() {
                URL url = null;
                UserDAO dao = new UserDAO(getApplicationContext());
                HttpURLConnection urlConnection = null;
                try {

                    url = new URL("http://www.mocky.io/v2/58b9b1740f0000b614f09d2f");
                    urlConnection = (HttpURLConnection) url.openConnection();

                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    String theString = getStringFromInputStream(in);
                    JSONObject obj = new JSONObject(theString);
                    dao.create(obj.getString("usuario"), obj.getString("senha"));

                } catch (Exception e) {
                    e.printStackTrace();
                }
                 finally {
                    urlConnection.disconnect();
                }
            }
        }.start();
    }

    public static String getStringFromInputStream(InputStream stream) throws IOException
    {
        int n = 0;
        char[] buffer = new char[1024 * 4];
        InputStreamReader reader = new InputStreamReader(stream, "UTF8");
        StringWriter writer = new StringWriter();
        while (-1 != (n = reader.read(buffer))) writer.write(buffer, 0, n);
        return writer.toString();
    }
    void fadeIn()
    {
        Animation FadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);

        FadeInAnimation.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {}
            public void onAnimationRepeat(Animation animation) {}
            public void onAnimationEnd(Animation animation) {
                Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                SplashActivity.this.finish();
                startActivity(i);
            }
        });
        splashLayout.startAnimation(FadeInAnimation);
    }

}

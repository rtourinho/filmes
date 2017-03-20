package com.tourinho.fiap.filmes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tourinho.fiap.filmes.DAO.MovieDAO;

public class NewMovieActivity extends Activity {

    private EditText mName;
    private boolean isEdit = false;
    Intent myIntent; // gets the previously created intent
    MovieDAO dao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_movie);
        myIntent = getIntent();
        dao = new MovieDAO(getApplicationContext());
        mName = (EditText) findViewById(R.id.movie_name);
        Button mSaveButton = (Button) findViewById(R.id.movie_save_button);
        Button mDeleteButton = (Button) findViewById(R.id.movie_delete_button);
        mSaveButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });

        mDeleteButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                delete();
            }
        });


        if(myIntent.hasExtra("name")) {
            isEdit = true;
            mName.setText(myIntent.getStringExtra("name"));
            mDeleteButton.setVisibility(View.VISIBLE);
        }

    }

    private void delete() {
        dao.delete(myIntent.getStringExtra("name"));
        Toast.makeText(getApplicationContext(), getText(R.string.movie_deleted),Toast.LENGTH_LONG ).show();
        Intent i = new Intent(NewMovieActivity.this, MainActivity.class);
        startActivity(i);
    }

    private void save() {


        if(myIntent.hasExtra("name")) {
            dao.update(myIntent.getStringExtra("name"), mName.getText().toString());
            Toast.makeText(getApplicationContext(), getText(R.string.movie_updated),Toast.LENGTH_LONG ).show();
        }
        else {
            dao.create(mName.getText().toString());
            Toast.makeText(getApplicationContext(), getText(R.string.movie_created),Toast.LENGTH_LONG ).show();
        }

        Intent i = new Intent(NewMovieActivity.this, MainActivity.class);
        startActivity(i);
    }


}


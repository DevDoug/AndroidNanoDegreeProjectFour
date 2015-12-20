package com.rayware.jokeview;

import android.app.ProgressDialog;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class DisplayJokeActivity extends AppCompatActivity {

    ProgressBar mProgressBar;
    String mJoke;
    TextView mJokeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_joke_view);

        Bundle extras = getIntent().getExtras();

        if(extras != null) {
            mJoke = extras.getString("JokeContent");
        }

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mJokeText = (TextView) findViewById(R.id.joke_text);

        mJokeText.setText(mJoke);
        mProgressBar.setVisibility(View.GONE);
    }
}

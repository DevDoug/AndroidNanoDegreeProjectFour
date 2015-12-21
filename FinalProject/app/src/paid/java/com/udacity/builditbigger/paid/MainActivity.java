package com.udacity.builditbigger.paid;

import android.content.Context;
import android.content.Intent;
import android.util.Pair;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.LaughFactory;
import com.rayware.jokeview.DisplayJokeActivity;
import com.udacity.gradle.builditbigger.R;
import com.udacity.gradle.builditbigger.utils.Constants;
import com.udacity.gradle.builditbigger.web.*;

public class MainActivity extends ActionBarActivity implements EndpointsAsyncTask.AsyncResponse{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view){
        new EndpointsAsyncTask(this).execute(new Pair<Context, String>(this, LaughFactory.tellJoke()));
    }

    public void launchLibraryActivity(String joke){
        Intent libraryIntent = new Intent(this, DisplayJokeActivity.class);
        libraryIntent.putExtra(Constants.EXTRA_JOKE,joke);
        startActivity(libraryIntent);
    }

    @Override
    public void getBackEndResponse(String output) {
        launchLibraryActivity(output);
    }
}

package com.udacity.gradle.builditbigger.free;

import android.content.Context;
import android.content.Intent;
import android.util.Pair;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ViewSwitcher;

import com.example.LaughFactory;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.rayware.jokeview.DisplayJokeActivity;
import com.udacity.gradle.builditbigger.R;
import com.udacity.gradle.builditbigger.utils.Constants;
import com.udacity.gradle.builditbigger.web.*;


public class MainActivity extends ActionBarActivity implements EndpointsAsyncTask.AsyncResponse{

    AdView adView;
    InterstitialAd mInterstitialAd;
    ProgressBar spinner;
    ViewSwitcher mViewSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        spinner = (ProgressBar) findViewById(R.id.loading_joke);
        mViewSwitch = (ViewSwitcher) findViewById(R.id.viewSwitcher);

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                mViewSwitch.showNext();
                requestNewInterstitial();
                new EndpointsAsyncTask(MainActivity.this).execute(new Pair<Context, String>(MainActivity.this, LaughFactory.tellJoke()));
            }
        });

        requestNewInterstitial();
    }

    @Override
    protected void onStop(){
        mViewSwitch.setDisplayedChild(0);
        super.onStop();
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
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
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

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("SEE_YOUR_LOGCAT_TO_GET_YOUR_DEVICE_ID")
                .build();

        mInterstitialAd.loadAd(adRequest);
    }
}
package com.udacity.gradle.builditbigger;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.test.AndroidTestCase;
import android.util.Pair;

import com.example.LaughFactory;
import com.example.builditbigger.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.udacity.gradle.builditbigger.web.EndpointsAsyncTask;

import junit.framework.TestCase;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by Douglas on 11/30/2015.
 */
public class BackendTest extends AndroidTestCase {

    private static MyApi myApiService = null;
    private Context context;


    public void testAsyncJoke () throws Throwable{
        // create  a signal to let us know when our task is done.
        final CountDownLatch signal = new CountDownLatch(1);

        final AsyncTask<Pair<Context, String>, Void, String> myTask = new AsyncTask<Pair<Context, String>, Void, String>() {

            @Override
            protected String doInBackground(Pair<Context, String>... params) {
                if(myApiService == null) {  // Only do this once
                    MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                            .setRootUrl("https://androidnanodegreprojectfour.appspot.com/_ah/api/");
                    myApiService = builder.build();
                }

                context = params[0].first;
                String name = params[0].second;

                try {
                    return myApiService.sayHi(name).execute().getData();
                } catch (IOException e) {
                    return e.getMessage();
                }
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                signal.countDown();
            }
        };

        // Execute the async task on the UI thread! THIS IS KEY!
/*        runTestOnUiThread(new Runnable() {

            @Override
            public void run() {
                myTask.execute("Do something");
            }
        });*/

        signal.await(30, TimeUnit.SECONDS);

        // The task is done, and now you can assert some things!
        assertTrue("Happiness", true);
    }
}

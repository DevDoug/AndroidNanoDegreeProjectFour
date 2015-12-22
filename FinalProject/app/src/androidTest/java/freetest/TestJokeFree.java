package freetest;

import android.content.Context;
import android.test.InstrumentationTestCase;
import android.util.Log;
import android.util.Pair;

import com.example.LaughFactory;
import com.udacity.gradle.builditbigger.free.MainActivity;
import com.udacity.gradle.builditbigger.web.EndpointsAsyncTask;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by Douglas on 12/20/2015.
 */
public class TestJokeFree extends InstrumentationTestCase implements EndpointsAsyncTask.AsyncResponse {
    private static final String TAG = "FetchRESTResponseTaskTests";
    private static final String URL = "https://androidnanodegreprojectfour.appspot.com/_ah/api/";
    private static boolean called;

    public TestJokeFree(){
        super();
    }

    protected void setUp() throws Exception {
        super.setUp();
        called = false;
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public final void testSuccessfulFetch() throws Throwable {
        // create  a signal to let us know when our task is done.
        final CountDownLatch signal = new CountDownLatch(1);

        // Execute the async task on the UI thread! THIS IS KEY!
        runTestOnUiThread(new Runnable() {
            @Override
            public void run() {
                new EndpointsAsyncTask(TestJokeFree.this).execute(new Pair<Context, String>(getInstrumentation().getContext(), LaughFactory.tellJoke()));

            }
        });

	    /* The testing thread will wait here until the UI thread releases it
	     * above with the countDown() or 30 seconds passes and it times out.
	     */
        signal.await(10, TimeUnit.SECONDS);
        assertTrue(called);
    }

    @Override
    public void getBackEndResponse(String output) {
        assertNotNull(output);
    }
}

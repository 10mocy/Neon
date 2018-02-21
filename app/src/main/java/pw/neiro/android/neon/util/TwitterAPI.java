package pw.neiro.android.neon.util;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import pw.neiro.android.neon.MainActivity;
import twitter4j.Twitter;
import twitter4j.TwitterException;

/**
 * Created by nirot1r on 2018/02/20.
 */

public class TwitterAPI {

    private static Twitter twitter;

    public static final void statusUpdate(String tweet) {
        twitter = TwitterUtil.getTwitterInstance(MainActivity.getContext());
        AsyncTask<String, Void, Boolean> task = new AsyncTask<String, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(String... strings) {
                try {
                    twitter.updateStatus(strings[0]);
                    return true;
                } catch (TwitterException e) {
                    e.printStackTrace();
                    return false;
                }
            }

            @Override
            protected void onPostExecute(Boolean result) {
                /*if(result) {
                    Toast.makeText(MainActivity.getContext(), "aaa", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.getContext(), "eee", Toast.LENGTH_SHORT).show();
                }*/
            }
        };
        task.execute(tweet);
    }
}

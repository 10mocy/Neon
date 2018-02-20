package pw.neiro.android.neon.callback;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import pw.neiro.android.neon.MainActivity;
import pw.neiro.android.neon.R;
import pw.neiro.android.neon.util.TwitterUtil;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

public class TwitterOAuth extends AppCompatActivity {

    private String callbackURL;
    private Twitter twitter;
    private RequestToken requestToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitter_oauth);

        callbackURL = getString(R.string.twitter_callback_url);
        twitter = TwitterUtil.getTwitterInstance(this);
    }

    public void onClickAuthorize(View v) {
        startAuthorize();
    }

    private void startAuthorize() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                try {
                    requestToken = twitter.getOAuthRequestToken(callbackURL);
                    return requestToken.getAuthenticationURL();
                } catch (TwitterException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String url) {
                if(url != null) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                } else {
                    // 失敗時の処理
                }
            }
        }.execute();
    }

    @Override
    public void onNewIntent(Intent intent) {
        if(intent == null
                || intent.getData() == null
                || !intent.getData().toString().startsWith(callbackURL)) {
            return;
        }
        String verifier = intent.getData().getQueryParameter("oauth_verifier");

        new AsyncTask<String, Void, AccessToken>() {
            @Override
            protected AccessToken doInBackground(String... params) {
                try {
                    return twitter.getOAuthAccessToken(requestToken, params[0]);
                } catch (TwitterException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(AccessToken accessToken) {
                if(accessToken != null) {
                    Toast.makeText(MainActivity.getContext(), "認証完了。", Toast.LENGTH_SHORT).show();
                    successOAuth(accessToken);
                } else {
                    Toast.makeText(MainActivity.getContext(), "認証失敗。", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute(verifier);
    }

    private void successOAuth(AccessToken accessToken) {
        TwitterUtil.storeAccessToken(this, accessToken);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}

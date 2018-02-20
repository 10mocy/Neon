package pw.neiro.android.neon;

import android.app.ActivityManager;
import android.content.Intent;
import android.provider.Settings;
import android.service.notification.NotificationListenerService;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import pw.neiro.android.neon.callback.TwitterOAuth;
import pw.neiro.android.neon.service.NotificationService;
import pw.neiro.android.neon.util.TwitterUtil;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class MainActivity extends AppCompatActivity {
    private static MainActivity mainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mainActivity = this;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent();

        if(!TwitterUtil.hasAccessToken(this)) {
            intent = new Intent(this, TwitterOAuth.class);
            startActivity(intent);
            finish();
        }

        intent.setAction(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS);
        startActivity(intent);
        Toast.makeText(MainActivity.getContext(), "zadaslk;fjsa", Toast.LENGTH_LONG).show();

    }

    public void onClick(View v) {
        stopService(new Intent(this, NotificationService.class));
        finishAndRemoveTask();
    }

    private static AccessToken loadAccessToken(int useId) {
        String token = "";
        String tokenSecret = "";

        return new AccessToken(token, tokenSecret);
    }

    public static MainActivity getContext() {
        return mainActivity;
    }
}

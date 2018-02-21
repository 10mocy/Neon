package pw.neiro.android.neon;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.service.notification.NotificationListenerService;
import android.support.v4.app.FragmentTransaction;
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
        //Toast.makeText(MainActivity.getContext(), "", Toast.LENGTH_LONG).show();

    }

    public void finishApplication(View v) {
        stopService(new Intent(this, NotificationService.class));
        finishAndRemoveTask();
    }

    public void settingApplication(View v) {
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
    }

    public void checkService(View v) {
        ActivityManager am = (ActivityManager)this.getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> listServiceInfo = am.getRunningServices(Integer.MAX_VALUE);
        boolean found = false;
        for (ActivityManager.RunningServiceInfo curr : listServiceInfo) {
            if (curr.service.getClassName().equals(NotificationService.class.getName())) {
                Toast.makeText(this, "サービス実行中", Toast.LENGTH_LONG).show();
                found = true;
                break;
            }
        }
        if (found == false) {
            Toast.makeText(this, "サービス停止中", Toast.LENGTH_LONG).show();
        }
    }

    public void _startService(View v) {
        startService(new Intent(this, NotificationService.class));
    }

    public void _stopService(View v) {
        stopService(new Intent(this, NotificationService.class));
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

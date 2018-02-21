package pw.neiro.android.neon.fragment;

import android.app.ActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.widget.Toast;

import java.util.List;

import pw.neiro.android.neon.MainActivity;
import pw.neiro.android.neon.R;
import pw.neiro.android.neon.service.NotificationService;

/**
 * Created by nirot1r on 2018/02/21.
 */

public class DebugFragment extends PreferenceFragment {
    public static MainActivity mainActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.debug);
        mainActivity = MainActivity.getContext();

        findPreference("service_check")
                .setOnPreferenceClickListener(
                        new Preference.OnPreferenceClickListener() {
                            @Override
                            public boolean onPreferenceClick(Preference pref) {

                                mainActivity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        ActivityManager am = (ActivityManager) mainActivity.getSystemService(mainActivity.ACTIVITY_SERVICE);
                                        List<ActivityManager.RunningServiceInfo> listServiceInfo = am.getRunningServices(Integer.MAX_VALUE);
                                        boolean found = false;
                                        for (ActivityManager.RunningServiceInfo curr : listServiceInfo) {
                                            if (curr.service.getClassName().equals(NotificationService.class.getName())) {
                                                Toast.makeText(mainActivity, "サービス実行中", Toast.LENGTH_LONG).show();
                                                found = true;
                                                break;
                                            }
                                        }
                                        if (found == false) {
                                            Toast.makeText(mainActivity, "サービス停止中", Toast.LENGTH_LONG).show();
                                        }
                                        mainActivity.startService(new Intent(mainActivity, NotificationService.class));
                                    }
                                });
                                return false;

                            }
                        }
                );

        findPreference("service_start")
                .setOnPreferenceClickListener(
                        new Preference.OnPreferenceClickListener() {
                            @Override
                            public boolean onPreferenceClick(Preference pref) {

                                mainActivity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mainActivity.startService(new Intent(mainActivity, NotificationService.class));
                                    }
                                });
                                return false;

                            }
                        }
                );
        findPreference("service_stop")
                .setOnPreferenceClickListener(
                        new Preference.OnPreferenceClickListener() {
                            @Override
                            public boolean onPreferenceClick(Preference pref) {

                                mainActivity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mainActivity.stopService(new Intent(mainActivity, NotificationService.class));
                                    }
                                });
                                return false;

                            }
                        }
                );
    }
}

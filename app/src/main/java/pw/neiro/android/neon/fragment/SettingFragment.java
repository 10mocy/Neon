package pw.neiro.android.neon.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import pw.neiro.android.neon.MainActivity;
import pw.neiro.android.neon.R;
import pw.neiro.android.neon.activity.DebugActivity;

/**
 * Created by nirot1r on 2018/02/21.
 */

public class SettingFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        findPreference("debug_info")
                .setOnPreferenceClickListener(
                        new Preference.OnPreferenceClickListener() {
                            @Override
                            public boolean onPreferenceClick(Preference pref) {
                                Intent intent = new Intent(MainActivity.getContext(), DebugActivity.class);
                                startActivity(intent);

                                return false;
                            }
                        }
                );
    }
}

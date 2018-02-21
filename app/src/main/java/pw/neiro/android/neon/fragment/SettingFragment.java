package pw.neiro.android.neon.fragment;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import pw.neiro.android.neon.R;

/**
 * Created by nirot1r on 2018/02/21.
 */

public class SettingFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}

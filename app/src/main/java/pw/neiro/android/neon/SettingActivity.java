package pw.neiro.android.neon;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import pw.neiro.android.neon.fragment.SettingFragment;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        SettingFragment fragment = new SettingFragment();
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, fragment)
                .commit();
    }
}

package pw.neiro.android.neon.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import pw.neiro.android.neon.R;
import pw.neiro.android.neon.fragment.DebugFragment;

public class DebugActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug);

        DebugFragment fragment = new DebugFragment();
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, fragment)
                .commit();

    }
}

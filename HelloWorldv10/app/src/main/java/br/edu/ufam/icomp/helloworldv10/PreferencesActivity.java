package br.edu.ufam.icomp.helloworldv10;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.view.MenuItem;

public class PreferencesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction().replace(android.R.id.content, new PreferencesFragment()).commit();
        //setContentView(R.layout.activity_preferences);
    }

    public static class PreferencesFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle bundle, String s) {
            setPreferencesFromResource(R.xml.preferences, s);
        }
    }
}

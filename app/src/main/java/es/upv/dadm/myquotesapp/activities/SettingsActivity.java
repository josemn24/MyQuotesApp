package es.upv.dadm.myquotesapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import es.upv.dadm.myquotesapp.R;
import es.upv.dadm.myquotesapp.fragments.SettingsFragment;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getSupportFragmentManager()
                .beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fcvSettings, SettingsFragment.class, null)
                .commit();
    }
}
package es.upv.dadm.myquotesapp.fragments;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import es.upv.dadm.myquotesapp.R;

public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences_settings, rootKey);
    }
}

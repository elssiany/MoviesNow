package com.kevinserrano.moviesnow.view.fragment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.util.Log;
import android.widget.Toast;

import com.kevinserrano.moviesnow.R;
import com.kevinserrano.moviesnow.utilities.ManagerSharedPreferences;

import java.net.MalformedURLException;
import java.net.URL;


public class SettingsFragment extends PreferenceFragment implements
        SharedPreferences.OnSharedPreferenceChangeListener, Preference.OnPreferenceChangeListener{


    public SettingsFragment() {
        //Required empty public constructor
    }


    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Load the Preferences from the XML file
        addPreferencesFromResource(R.xml.app_preferences);
        findPreference("pref_notifications_1").setOnPreferenceClickListener(onPreferenceClickListener);
        findPreference("pref_notifications_2").setOnPreferenceClickListener(onPreferenceClickListener);
        findPreference("more_apps").setOnPreferenceClickListener(onPreferenceClickListener);
        findPreference("politics_use").setOnPreferenceClickListener(onPreferenceClickListener);
        findPreference("pref_rating").setOnPreferenceClickListener(onPreferenceClickListener);
        findPreference("pref_about").setOnPreferenceClickListener(onPreferenceClickListener);
        findPreference("pref_send_comments").setOnPreferenceChangeListener(this);
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }



    private  Preference.OnPreferenceClickListener onPreferenceClickListener = preference -> {
        switch (preference.getKey()) {
            case "more_apps":
                getActivity().startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.ikevinsm.site/")));
                return true;
            case "pref_rating":
                // For all other preferences, set the summary to the value's
                // simple string representation.
                getActivity().startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.ikevinsm.site/")));
                return true;
            case "pref_notifications_1":
                // For all other preferences, set the summary to the value's
                // simple string representation.
                ManagerSharedPreferences.editPreferences("pref_notifications_1",preference
                        .getSharedPreferences().getBoolean("pref_notifications_1",true));
                return true;
            case "pref_notifications_2":
                // For all other preferences, set the summary to the value's
                // simple string representation.
                ManagerSharedPreferences.editPreferences("pref_notifications_2",preference
                        .getSharedPreferences().getBoolean("pref_notifications_2",true));
                return true;
            case "pref_notifications_3":
                // For all other preferences, set the summary to the value's
                // simple string representation.
                ManagerSharedPreferences.editPreferences("pref_notifications_3",preference
                        .getSharedPreferences().getBoolean("pref_notifications_3",true));
                return true;
            case "politics_use":
                getActivity().startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.ikevinsm.site/")));
                return true;
        }
        return false;
    };


    @Override
    public void onDestroy() {
        super.onDestroy();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

    }


    @Override
    public boolean onPreferenceChange(Preference preference, Object o) {
        String stringValue = preference.getSummary().toString();
        String key = preference.getKey();
       if(key.equals("pref_send_comments")) {
            if (!stringValue.isEmpty()) {
                Bundle comments = new Bundle();
                comments.putString("Comentarios", stringValue);
                Toast.makeText(getActivity(),"Gracias por tus ayudar a mejorar la app",
                        Toast.LENGTH_LONG).show();
            }
        }
            return false;
    }



}

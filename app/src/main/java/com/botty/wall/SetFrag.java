package com.botty.wall;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;
import android.widget.Toast;

/**
 * Created by ivanbotty on 30/10/14.
 */
public class SetFrag extends PreferenceFragment {

    public SetFrag() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);

        final CheckBoxPreference buyPref = (CheckBoxPreference) findPreference("pref_remove");
        buyPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener()
        {
            @Override
            public boolean onPreferenceChange(final Preference preference,
                                              final Object newValue)
            {
                boolean condition = false;
                Toast.makeText(getActivity(), "Not Ready Yet !!", Toast.LENGTH_SHORT).show();
                // set condition true or false here according to your needs.
                buyPref.setChecked(condition);
                SharedPreferences.Editor edit = preference.getEditor();
                edit.putBoolean("pref_remove", condition);
                edit.commit();
                return false;
            }
        });

        final SwitchPreference switchPreference = (SwitchPreference) findPreference("random");
        switchPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                boolean switchr = false;
                switchPreference.setChecked(switchr);
                SharedPreferences.Editor edit =preference.getEditor();
                edit.putBoolean("random",switchr);
                edit.commit();
                Toast.makeText(getActivity(),"I hope soon !!",Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        final PreferenceScreen screen = (PreferenceScreen) findPreference("about_us");
        screen.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Fragment fragment = null;
                Bundle args = new Bundle();
                fragment = new FragmentThree();
                fragment.setArguments(args);
                FragmentManager frgManager = getFragmentManager();
                frgManager.beginTransaction().replace(R.id.content_frame, fragment)
                        .commit();
                return false;
            }
        });
    }
}
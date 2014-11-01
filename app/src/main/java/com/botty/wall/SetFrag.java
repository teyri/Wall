package com.botty.wall;

import android.os.Bundle;
import android.preference.PreferenceFragment;

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
    }
}
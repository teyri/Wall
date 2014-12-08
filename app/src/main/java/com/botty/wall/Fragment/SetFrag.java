package com.botty.wall.Fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;
import android.util.Log;
import android.widget.Toast;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.botty.wall.R;

/**
 * Created by ivanbotty on 30/10/14.
 */
public class SetFrag extends PreferenceFragment  implements BillingProcessor.IBillingHandler {

    boolean purchased = false;
    BillingProcessor bp;
    CheckBoxPreference buyPref;
    private boolean readyToPurchase = false;
    private static final String LOG_TAG = "BottyShop";
    private static final String PRODUCT_ID = "removeads";
    private static final String LICENSE_KEY = "YOUR LICENSE KEY FOR THIS APPLICATION";

    public SetFrag() {

    }

    @Override
    public void onDestroy() {
        if (bp != null)
            bp.release();

        super.onDestroy();
    }

    @Override
    public void onProductPurchased(String s, TransactionDetails transactionDetails) {
    }

    @Override
    public void onPurchaseHistoryRestored() {
        for(String sku : bp.listOwnedProducts())
            Log.d(LOG_TAG, "Owned Managed Product: " + sku);
        for(String sku : bp.listOwnedSubscriptions())
            Log.d(LOG_TAG, "Owned Subscription: " + sku);
    }

    @Override
    public void onBillingError(int i, Throwable throwable) {
        showToast("onBillingError: " + Integer.toString(i));
    }

    @Override
    public void onBillingInitialized() {
        readyToPurchase = true;

    }


    private void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
        bp = new BillingProcessor(getActivity(), LICENSE_KEY, this);

        final CheckBoxPreference buyPref = (CheckBoxPreference) findPreference("pref_remove");
        buyPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener()
        {
            @Override
            public boolean onPreferenceChange(final Preference preference,
                                              final Object newValue)
            {
               // bp.purchase(PRODUCT_ID);
                buyPref.setChecked(purchased);
                SharedPreferences.Editor edit = preference.getEditor();
                edit.putBoolean("pref_remove",purchased);
                edit.commit();
                return false;
            }
        });

        final SwitchPreference switchPreference = (SwitchPreference) findPreference("random");
        switchPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                boolean switchr = true;
                switchPreference.setChecked(switchr);
                SharedPreferences.Editor edit =preference.getEditor();
                edit.putBoolean("random",switchr);
                edit.commit();
                getActivity().finish();
                return false;
            }
        });

        final SwitchPreference dl_wall = (SwitchPreference) findPreference("dl_wall");
        dl_wall.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                boolean dl_wall_ = true;
                switchPreference.setChecked(dl_wall_);
                SharedPreferences.Editor edit =preference.getEditor();
                edit.putBoolean("dl_wall",dl_wall_);
                edit.commit();
                getActivity().finish();
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

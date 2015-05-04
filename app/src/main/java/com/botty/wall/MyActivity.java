package com.botty.wall;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;

import com.botty.wall.Adapters.CustomDrawerAdapter;
import com.botty.wall.Adapters.DrawerItem;
import com.botty.wall.Fragment.FragmentOne;
import com.botty.wall.Fragment.FragmentTwo;
import com.botty.wall.Fragment.SetFrag;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.mikepenz.iconics.typeface.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MyActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    ImageView imageView;

    FrameLayout frameLayout;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    CustomDrawerAdapter adapter;

    List<DrawerItem> dataList;
    private Drawer.Result drawerMaterial = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        frameLayout = (FrameLayout)findViewById(R.id.content_frame);

        PreferenceManager.setDefaultValues(getApplicationContext(), R.xml.preferences, true);
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        if (settings.getBoolean("pref_remove", false)) {
            frameLayout.setPadding(0,0,0,0);
        }else {
            frameLayout.setPadding(0,0,0,150);
        }

        if (settings.getBoolean("dl_wall", false)) {
            if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                Log.d("MyApp", "No SDCARD");
            } else {
                File directory = new File(Environment.getExternalStorageDirectory()+File.separator+"Dl_Wall_app");
                directory.mkdirs();
            }
        }

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        drawerMaterial = new Drawer()
                .withActivity(this)
                .withToolbar(toolbar)
                .withActionBarDrawerToggle(true)
                .withHeader(R.layout.my_header)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("CyanogeMod").withIcon(FontAwesome.Icon.faw_download),
                        new PrimaryDrawerItem().withName("ParanoidAndroid").withIcon(FontAwesome.Icon.faw_circle_o),
                        new PrimaryDrawerItem().withName("Own Wall").withIcon(FontAwesome.Icon.faw_money),
                        new SectionDrawerItem().withName("Some Stuff"),
                        new SecondaryDrawerItem().withName("Donate").withIcon(FontAwesome.Icon.faw_info),
                        new SecondaryDrawerItem().withName("Setting").withIcon(FontAwesome.Icon.faw_info),
                        new SecondaryDrawerItem().withName("About App").withIcon(FontAwesome.Icon.faw_info)
        )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {
                    }
                })
                .withSelectedItem(0)
                .build();

    }
    

    /**
     * This class makes the ad request and loads the ad.
     */
    public static class AdFragment extends Fragment {

        private AdView mAdView;

        public AdFragment() {
        }

        @Override
        public void onActivityCreated(Bundle bundle) {
            super.onActivityCreated(bundle);

            // Gets the ad view defined in layout/ad_fragment.xml with ad unit ID set in
            // values/strings.xml.
            mAdView = (AdView) getView().findViewById(R.id.adView);

            PreferenceManager.setDefaultValues(getActivity(), R.xml.preferences, true);
            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getActivity());

            if (settings.getBoolean("pref_remove", false)) {
                mAdView.setVisibility(View.INVISIBLE);
            }else {
                mAdView.setVisibility(View.VISIBLE);
            }
            // Create an ad request. Check logcat output for the hashed device ID to
            // get test ads on a physical device. e.g.
            // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
            AdRequest adRequest = new AdRequest.Builder()
                    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                    .build();

            // Start loading the ad in the background.
            mAdView.loadAd(adRequest);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_ad, container, false);
        }

        /** Called when leaving the activity */
        @Override
        public void onPause() {
            if (mAdView != null) {
                mAdView.pause();
            }
            super.onPause();
        }

        /** Called when returning to the activity */
        @Override
        public void onResume() {
            super.onResume();
            if (mAdView != null) {
                mAdView.resume();
            }
        }

        /** Called before the activity is destroyed */
        @Override
        public void onDestroy() {
            if (mAdView != null) {
                mAdView.destroy();
            }
            super.onDestroy();
        }

    }
}
package com.botty.wall.Fragment;

import android.app.ActivityOptions;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.botty.wall.Activites.SetWall2;
import com.botty.wall.Adapters.CustomGrid;
import com.botty.wall.R;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.io.InputStream;
import java.net.URL;
import java.util.Random;

public class FragmentTwo  extends Fragment {

    private String[] own = { "http://gnexushd.altervista.org/wallpapers/gnexushd/wall_sky_sunset.jpg",
    "http://gnexushd.altervista.org/wallpapers/gnexushd/wallpaper_batman_beyod.jpg",
    "http://gnexushd.altervista.org/wallpapers/gnexushd/wallpaper_batman_logo2.jpg",
    "http://gnexushd.altervista.org/wallpapers/gnexushd/wallpaper_cm11_leaks.png",
    "http://gnexushd.altervista.org/wallpapers/gnexushd/wallpaper_cm_wall.png",
    "http://gnexushd.altervista.org/wallpapers/gnexushd/wallpaper_cyngn.png",
    "http://gnexushd.altervista.org/wallpapers/gnexushd/wallpaper_eva1.jpg",
    "http://gnexushd.altervista.org/wallpapers/gnexushd/wallpaper_kitkat.jpg",
    "http://gnexushd.altervista.org/wallpapers/gnexushd/wallpaper_nexus.jpg",
    "http://www.gnexushd.altervista.org/im/zeo_bg_wall_dark.jpg",
    "http://gnexushd.altervista.org/im/zeo_bg_wall.jpg",
    "http://gnexushd.altervista.org/im/wall_skyblue.jpg",
    "http://gnexushd.altervista.org/im/wall_batgirl.jpg",
    "http://gnexushd.altervista.org/wallpapers/gnexushd/wallpaper_prospere.jpg",
    "http://gnexushd.altervista.org/wallpapers/gnexushd/wallpapers_back_sky.png",
    "http://gnexushd.altervista.org/wallpapers/gnexushd/wallpapers_digimon_anniversary.jpg",
    "http://gnexushd.altervista.org/wallpapers/gnexushd/wallpapers_digi_sky.jpg",
    "http://gnexushd.altervista.org/wallpapers/gnexushd/wallpapers_logo_tri.png",
    "http://gnexushd.altervista.org/wallpapers/gnexushd/wallpapers_fall_tri.png"};

    GridView grid;
    ProgressDialog myProgressDialog;
    ImageButton mBtRandom;

    Random rand = new Random();
    int casual = rand.nextInt(own.length-1);

    public FragmentTwo() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_wall, container,
                false);
        final CustomGrid adapter = new CustomGrid(getActivity(), own);
        grid=(GridView)view.findViewById(R.id.grid);
        mBtRandom = (ImageButton)view.findViewById(R.id.random);
        YoYo.with(Techniques.SlideInUp).playOn(mBtRandom);

        grid.setVisibility(View.VISIBLE);

        PreferenceManager.setDefaultValues(getActivity(), R.xml.preferences, true);
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getActivity());

        if (settings.getBoolean("random", false)) {
            mBtRandom.setVisibility(View.VISIBLE);
        }else {
            mBtRandom.setVisibility(View.INVISIBLE);
        }

        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // Check if we're running on Android 5.0 or higher
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    // Call some material design APIs here
                    Intent i = new Intent(getActivity(), SetWall2.class);
                    i.putExtra("posi",position);
                    Log.i("positionFrag", own[position]);
                    startActivity(i, ActivityOptions.makeSceneTransitionAnimation(getActivity(), view.findViewById(R.id.grid_image), "robot").toBundle());
                } else {
                    // Implement this feature without material design
                    Intent i = new Intent(getActivity(), SetWall2.class);
                    i.putExtra("posi",position);
                    Log.i("positionFrag", own[position]);
                    startActivity(i);
                }
            }
        });

        mBtRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SetWallpaperAsyncTask().execute(own[casual]);
            }
        });
        return view;
    }

    private class SetWallpaperAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String URL = own[casual];
            setWallpaper(URL);
            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {
            myProgressDialog.dismiss();
            Toast.makeText(getActivity(), "We think this is perfect for you :D",
                    Toast.LENGTH_LONG).show();
            getActivity().finish();
        }

        @Override
        protected void onPreExecute() {
            dialog();
        }

        @Override
        protected void onProgressUpdate(Void... values) {

        }

        private void setWallpaper(String url) {
            try {
                WallpaperManager wpm = WallpaperManager.getInstance(getActivity());
                InputStream ins = new URL(url).openStream();
                wpm.setStream(ins);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void dialog(){
        myProgressDialog = new ProgressDialog(getActivity());
        myProgressDialog.setCancelable(false);
        myProgressDialog.setMessage("Loading the stuff...");
        myProgressDialog.show();
    }
}

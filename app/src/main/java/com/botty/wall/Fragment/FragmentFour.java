package com.botty.wall.Fragment;

import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.botty.wall.Activites.setWall3;
import com.botty.wall.Adapters.CustomGrid;
import com.botty.wall.R;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.io.InputStream;
import java.net.URL;
import java.util.Random;

/**
 * Created by BottyIvan on 04/05/15.
 */
public class FragmentFour extends Fragment {

    private String[] PA = {"http://gnexushd.altervista.org/wallpapers/paranoid/AOSPA%20Humble%20Huckleberry%20-%203966.png",
            "http://gnexushd.altervista.org/wallpapers/paranoid/AOSPA%20Sweater%20Weather%20-%203966.png",
            "http://gnexushd.altervista.org/wallpapers/paranoid/AOSPA%20Triangles%20Abode%20-%203966.png",
            "http://gnexushd.altervista.org/wallpapers/paranoid/wallpaper_0.jpg",
            "http://gnexushd.altervista.org/wallpapers/paranoid/wallpaper_1.jpg",
            "http://gnexushd.altervista.org/wallpapers/paranoid/wallpaper_2.jpg",
            "http://gnexushd.altervista.org/wallpapers/paranoid/wallpaper_3.jpg",
            "http://gnexushd.altervista.org/wallpapers/paranoid/wallpaper_4.jpg",
            "http://gnexushd.altervista.org/wallpapers/paranoid/wallpaper_5.jpg",
            "http://gnexushd.altervista.org/wallpapers/paranoid/wallpaper_6.jpg",
            "http://gnexushd.altervista.org/wallpapers/paranoid/wallpaper_7.jpg",
            "http://gnexushd.altervista.org/wallpapers/paranoid/wallpaper_8.jpg"};

    GridView grid;
    ProgressDialog myProgressDialog;
    ImageButton mBtRandom;

    Random rand = new Random();
    int casual = rand.nextInt(PA.length - 1);

    public FragmentFour() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.activity_wall,container,
                false);

        final CustomGrid adapter = new CustomGrid(getActivity(), PA);
        grid = (GridView) view.findViewById(R.id.grid);
        mBtRandom = (ImageButton) view.findViewById(R.id.random);
        YoYo.with(Techniques.SlideInUp).playOn(mBtRandom);

        grid.setVisibility(View.VISIBLE);

        PreferenceManager.setDefaultValues(getActivity(), R.xml.preferences, true);
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getActivity());

        if (settings.getBoolean("random", false)) {
            mBtRandom.setVisibility(View.VISIBLE);
        } else {
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
                    Intent i = new Intent(getActivity(), setWall3.class);
                    i.putExtra("posi", position);
                    Log.i("positionFrag", PA[position]);
                    startActivity(i);
                } else {
                    // Implement this feature without material design
                    Intent i = new Intent(getActivity(), setWall3.class);
                    i.putExtra("posi", position);
                    Log.i("positionFrag", PA[position]);
                    startActivity(i);
                }
            }
        });

        mBtRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SetWallpaperAsyncTask().execute(PA[casual]);
            }
        });
        return view;
    }

    private class SetWallpaperAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String URL = PA[casual];
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

    private void dialog() {
        myProgressDialog = new ProgressDialog(getActivity());
        myProgressDialog.setCancelable(false);
        myProgressDialog.setMessage("Loading the stuff...");
        myProgressDialog.show();
    }
}
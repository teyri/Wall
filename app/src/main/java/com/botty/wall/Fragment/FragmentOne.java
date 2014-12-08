package com.botty.wall.Fragment;

import android.annotation.TargetApi;
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

import com.botty.wall.Adapters.CustomGrid;
import com.botty.wall.R;
import com.botty.wall.Activites.setWall;

import java.io.InputStream;
import java.net.URL;
import java.util.Random;

public class FragmentOne extends Fragment {

    private String[] cyngn = {"http://gnexushd.altervista.org/wallpapers/cyanogen/hanksite.jpg",
            "http://gnexushd.altervista.org/wallpapers/cyanogen/heresjohnny.jpg",
            "http://gnexushd.altervista.org/wallpapers/cyanogen/hexography_blue.png",
            "http://gnexushd.altervista.org/wallpapers/cyanogen/hexography_salmon.png",
            "http://gnexushd.altervista.org/wallpapers/cyanogen/hextract.png",
            "http://gnexushd.altervista.org/wallpapers/cyanogen/network.jpg",
            "http://gnexushd.altervista.org/wallpapers/cyanogen/quartz.jpg",
            "http://gnexushd.altervista.org/wallpapers/cyanogen/bladesplusdroplets.jpg",
            "http://gnexushd.altervista.org/wallpapers/cyanogen/blueice_modcircle.jpg",
            "http://gnexushd.altervista.org/wallpapers/cyanogen/decay.jpg",
            "http://gnexushd.altervista.org/wallpapers/cyanogen/frostmaple.jpg",
            "http://gnexushd.altervista.org/wallpapers/cyanogen/light_bursting_out.jpg",
            "http://gnexushd.altervista.org/wallpapers/cyanogen/maplesunblaze.jpg",
            "http://gnexushd.altervista.org/wallpapers/cyanogen/mauve.jpg",
            "http://gnexushd.altervista.org/wallpapers/cyanogen/sunsetgrass.jpg",
            "http://gnexushd.altervista.org/wallpapers/cyanogen/tubetangle.jpg",
            "http://gnexushd.altervista.org/wallpapers/cyanogen/wallpaper_alexanderwislsperger_melbourne.jpg",
            "http://gnexushd.altervista.org/wallpapers/cyanogen/wallpaper_ashersimonds_cmpatternhololight.png",
            "http://gnexushd.altervista.org/wallpapers/cyanogen/wallpaper_eklipze_notquitemono.jpg",
            "http://gnexushd.altervista.org/wallpapers/cyanogen/wallpaper_eklipze_technicolorstripes.jpg",
            "http://gnexushd.altervista.org/wallpapers/cyanogen/wallpaper_nebkat_bokeh.jpg",
            "http://gnexushd.altervista.org/wallpapers/cyanogen/wallpaper_prash_ohaimark.png",
            "http://gnexushd.altervista.org/wallpapers/cyanogen/wallpaper_th_shadowchess.jpg",
            "http://gnexushd.altervista.org/wallpapers/cyanogen/wallpaper_tylerhodge_blueperfection.jpg",
            "http://gnexushd.altervista.org/wallpapers/cyanogen/winter_sunset.jpg",
            "http://gnexushd.altervista.org/wallpapers/cyanogen/dystopia.jpg",
            "http://gnexushd.altervista.org/wallpapers/cyanogen/maplebokeh.jpg",};

    GridView grid;
    ProgressDialog myProgressDialog;
    ImageButton mBtRandom;

    Random rand = new Random();
    int casual = rand.nextInt(cyngn.length-1);

    public FragmentOne() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_wall, container,
				false);
        final CustomGrid adapter = new CustomGrid(getActivity(), cyngn);
        grid=(GridView)view.findViewById(R.id.grid);
        mBtRandom = (ImageButton)view.findViewById(R.id.random);

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
            @TargetApi(Build.VERSION_CODES.L)
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // Check if we're running on Android 5.0 or higher
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    // Call some material design APIs here
                    Intent i = new Intent(getActivity(), setWall.class);
                    i.putExtra("pos", position);
                    Log.i("positionFrag", cyngn[position]);
                    startActivity(i, ActivityOptions.makeSceneTransitionAnimation(getActivity(), view.findViewById(R.id.grid_image), "robot").toBundle());
                } else {
                    // Implement this feature without material design
                    Intent i = new Intent(getActivity(), setWall.class);
                    i.putExtra("pos", position);
                    Log.i("positionFrag", cyngn[position]);
                    startActivity(i);
                }
            }
        });

        mBtRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SetWallpaperAsyncTask().execute(cyngn[casual]);
            }
        });
        return view;
	}

    private class SetWallpaperAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String URL = cyngn[casual];
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

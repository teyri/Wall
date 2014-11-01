package com.botty.wall;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URL;


public class setWall extends Activity {
    private String[] cyngn = { "http://gnexushd.altervista.org/wallpapers/cyanogen/acidwashedcid.jpg",
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
            "http://gnexushd.altervista.org/wallpapers/cyanogen/winter_sunset.jpg"
    };

    private int indexOfImage = 0;

    private ImageButton imageButton;
    ProgressDialog myProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_wall);

        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);

        imageButton = (ImageButton)findViewById(R.id.set_wall_ribbon);

        Intent i = getIntent();
        final int position = i.getIntExtra("pos", 0);
        Log.i("positionAct", cyngn[position]);
        ImageAdapter adapter = new ImageAdapter(setWall.this, cyngn);
        viewPager.setOnPageChangeListener(new MyPageChangeListener());
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(position);

        imageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new SetWallpaperAsyncTask().execute(cyngn[indexOfImage]);
                    }
                });

        }
    private class MyPageChangeListener extends
            ViewPager.SimpleOnPageChangeListener {
        @Override
        public void onPageSelected(int position) {
            indexOfImage = position;
        }
    }

    private class SetWallpaperAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String URL = cyngn[indexOfImage];
            setWallpaper(URL);
            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {
            myProgressDialog.dismiss();
            Toast.makeText(getApplicationContext(), "Wallpaper set :D",
                    Toast.LENGTH_LONG).show();
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
                WallpaperManager wpm = WallpaperManager.getInstance(setWall.this);
                InputStream ins = new URL(url).openStream();
                wpm.setStream(ins);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void dialog(){
        myProgressDialog = new ProgressDialog(setWall.this);
        myProgressDialog.setCancelable(false);
        myProgressDialog.setMessage("Loading...");
        myProgressDialog.show();
    }
}

package com.botty.wall.Activites;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.transition.Explode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.botty.wall.Adapters.ImageAdapter;
import com.botty.wall.R;

import org.apache.http.util.ByteArrayBuffer;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class setWall extends Activity {
    private String[] cyngn = { "http://gnexushd.altervista.org/wallpapers/cyanogen/hanksite.jpg",
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
            "http://gnexushd.altervista.org/wallpapers/cyanogen/maplebokeh.jpg",
    };

    private int indexOfImage = 0;

    private ImageButton imageButton;
    private ViewPager viewPager;
    ProgressDialog myProgressDialog;
    SharedPreferences settings;

    @TargetApi(Build.VERSION_CODES.L)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_wall);

        PreferenceManager.setDefaultValues(getApplicationContext(), R.xml.preferences, true);
        settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        viewPager = (ViewPager) findViewById(R.id.pager);

        imageButton = (ImageButton) findViewById(R.id.set_wall_ribbon);

        final Intent i = getIntent();
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.dl_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
            case R.id.dl_wall:
                if (settings.getBoolean("dl_wall", false)) {
                    DownloadFromUrl(cyngn[indexOfImage], "wall");
                }else Toast.makeText(getApplicationContext(),"Only on Pro :(",Toast.LENGTH_SHORT).show();                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void DownloadFromUrl(String imageURL, String fileName) {  //this is the downloader method
        try {
            URL url = new URL(cyngn[indexOfImage]); //you can write here any link
            File file = new java.io.File(Environment.getExternalStorageDirectory(), "Dl_Wall_app/wall_cyngn"+indexOfImage+".jpg");

            long startTime = System.currentTimeMillis();
            Log.d("ImageManager", "download begining");
            Log.d("ImageManager", "download url:" + url);
            Log.d("ImageManager", "downloaded file name:" + fileName);
                        /* Open a connection to that URL. */
            URLConnection ucon = url.openConnection();

                        /*
                         * Define InputStreams to read from the URLConnection.
                         */
            InputStream is = ucon.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);

                        /*
                         * Read bytes to the Buffer until there is nothing more to read(-1).
                         */
            ByteArrayBuffer baf = new ByteArrayBuffer(50);
            int current = 0;
            while ((current = bis.read()) != -1) {
                baf.append((byte) current);
            }

                        /* Convert the Bytes read to a String. */
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(baf.toByteArray());
            fos.close();
            Log.d("ImageManager", "download ready in"
                    + ((System.currentTimeMillis() - startTime) / 1000)
                    + " sec");

        } catch (IOException e) {
            Log.d("ImageManager", "Error: " + e);
        }

    }
}
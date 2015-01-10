package com.botty.wall.Activites;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.botty.wall.Adapters.ImageAdapter;
import com.botty.wall.R;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import org.apache.http.util.ByteArrayBuffer;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by BottyIvan on 26/12/14.
 */
public class SetWall2 extends Activity {
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

    private int indexOfImage = 0;

    private ImageButton imageButton;
    private ViewPager viewPager;
    ProgressDialog myProgressDialog;
    SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_wall);

        PreferenceManager.setDefaultValues(getApplicationContext(), R.xml.preferences, true);
        settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        viewPager = (ViewPager) findViewById(R.id.pager);

        imageButton = (ImageButton) findViewById(R.id.set_wall_ribbon);

        final Intent i = getIntent();
        final int position = i.getIntExtra("posi", 0);
        Log.i("positionAct", own[position]);

        ImageAdapter adapter = new ImageAdapter(SetWall2.this, own);
        viewPager.setOnPageChangeListener(new MyPageChangeListener());
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(position);

        YoYo.with(Techniques.SlideInUp).playOn(imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SetWallpaperAsyncTask().execute(own[indexOfImage]);
            }
        });

    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        YoYo.with(Techniques.SlideOutDown).playOn(imageButton);
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
            String URL = own[indexOfImage];
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
                WallpaperManager wpm = WallpaperManager.getInstance(SetWall2.this);
                InputStream ins = new URL(url).openStream();
                wpm.setStream(ins);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void dialog(){
        myProgressDialog = new ProgressDialog(SetWall2.this);
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
                    DownloadFromUrl(own[indexOfImage], "wall");
                }else Toast.makeText(getApplicationContext(),"Only on Pro :(",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.home:
                YoYo.with(Techniques.SlideOutDown).playOn(imageButton);
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void DownloadFromUrl(String imageURL, String fileName) {  //this is the downloader method
        try {
            URL url = new URL(own[indexOfImage]); //you can write here any link
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
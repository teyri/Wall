package com.botty.wall;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by ivanbotty on 28/10/14.
 */
public class SetWall extends Activity {

    private String[] own = { "http://gnexushd.altervista.org/wallpapers/gnexushd/wallpaper_batman_beyod.jpg",
            "http://gnexushd.altervista.org/wallpapers/gnexushd/wallpaper_batman_logo2.jpg",
            "http://gnexushd.altervista.org/wallpapers/gnexushd/wallpaper_cm11_leaks.png",
            "http://gnexushd.altervista.org/wallpapers/gnexushd/wallpaper_cm_wall.png",
            "http://gnexushd.altervista.org/wallpapers/gnexushd/wallpaper_eva1.jpg",
            "http://gnexushd.altervista.org/wallpapers/gnexushd/wallpaper_kitkat.jpg",
            "http://gnexushd.altervista.org/wallpapers/gnexushd/wallpaper_nexus.jpg",
            "http://www.gnexushd.altervista.org/im/zeo_bg_wall_dark.jpg",
            "http://gnexushd.altervista.org/im/zeo_bg_wall.jpg",
            "http://gnexushd.altervista.org/im/wall_skyblue.jpg",
            "http://gnexushd.altervista.org/im/wall_batgirl.jpg"};

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
        final int position = i.getIntExtra("position", 0);
        ImageAdapter adapter = new ImageAdapter(SetWall.this, own);
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new MyPageChangeListener());
        // displaying selected image first
        viewPager.setCurrentItem(position);
        viewPager.setAdapter(adapter);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SetWallpaperAsyncTask().execute(own[indexOfImage]);
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
                WallpaperManager wpm = WallpaperManager.getInstance(SetWall.this);
                InputStream ins = new URL(url).openStream();
                wpm.setStream(ins);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void dialog(){
        myProgressDialog = new ProgressDialog(SetWall.this);
        myProgressDialog.setCancelable(false);
        myProgressDialog.setMessage("Loading...");
        myProgressDialog.show();
    }
}
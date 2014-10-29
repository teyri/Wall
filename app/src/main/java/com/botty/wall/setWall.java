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
    private String[] cyngn = { "http://gnexushd.altervista.org/wallpapers/cyanogen/acidwashedcid.jpg", "http://gnexushd.altervista.org/im/wall_skyblue.jpg",
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
        indexOfImage = i.getExtras().getInt("position");
        Log.i("position", cyngn[indexOfImage]);
        ImageAdapter adapter = new ImageAdapter(setWall.this, cyngn);
        viewPager.setOnPageChangeListener(new MyPageChangeListener());
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(indexOfImage);

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

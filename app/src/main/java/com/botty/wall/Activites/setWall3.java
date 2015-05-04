package com.botty.wall.Activites;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by BottyIvan on 04/05/15.
 */
public class setWall3 extends Activity {
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

    private int indexOfImage = 0;

    private ImageButton imageButton;
    private ViewPager viewPager;
    ProgressDialog myProgressDialog;
    SharedPreferences settings;
    static final String TAG = "WALL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_wall);

        // This example uses decor view, but you can use any visible view.
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_LOW_PROFILE;
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        setupTransparentSystemBarsForLmp();

        PreferenceManager.setDefaultValues(getApplicationContext(), R.xml.preferences, true);
        settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        viewPager = (ViewPager) findViewById(R.id.pager);

        imageButton = (ImageButton) findViewById(R.id.set_wall_ribbon);

        final Intent i = getIntent();
        final int position = i.getIntExtra("posi", 0);
        Log.i("positionAct", PA[position]);

        ImageAdapter adapter = new ImageAdapter(setWall3.this, PA);
        viewPager.setOnPageChangeListener(new MyPageChangeListener());
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(position);

        YoYo.with(Techniques.SlideInUp).playOn(imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SetWallpaperAsyncTask().execute(PA[indexOfImage]);
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
            String URL = PA[indexOfImage];
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
                WallpaperManager wpm = WallpaperManager.getInstance(setWall3.this);
                InputStream ins = new URL(url).openStream();
                wpm.setStream(ins);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void dialog(){
        myProgressDialog = new ProgressDialog(setWall3.this);
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
                DownloadFromUrl(PA[indexOfImage], "wall");
                return true;
            case R.id.home:
                YoYo.with(Techniques.SlideOutDown).playOn(imageButton);
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void DownloadFromUrl(String imageURL, String fileName) {  //this is the downloader method
        try {
            URL url = new URL(PA[indexOfImage]); //you can write here any link
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


    private void setupTransparentSystemBarsForLmp() {
        // TODO(sansid): use the APIs directly when compiling against L sdk.
        // Currently we use reflection to access the flags and the API to set the transparency
        // on the System bars.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            try {
                getWindow().getAttributes().systemUiVisibility |=
                        (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                        | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                Field drawsSysBackgroundsField = WindowManager.LayoutParams.class.getField(
                        "FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS");
                getWindow().addFlags(drawsSysBackgroundsField.getInt(null));

                Method setStatusBarColorMethod =
                        Window.class.getDeclaredMethod("setStatusBarColor", int.class);
                Method setNavigationBarColorMethod =
                        Window.class.getDeclaredMethod("setNavigationBarColor", int.class);
                setStatusBarColorMethod.invoke(getWindow(), Color.TRANSPARENT);
                setNavigationBarColorMethod.invoke(getWindow(), Color.TRANSPARENT);
            } catch (NoSuchFieldException e) {
                Log.w(TAG, "NoSuchFieldException while setting up transparent bars");
            } catch (NoSuchMethodException ex) {
                Log.w(TAG, "NoSuchMethodException while setting up transparent bars");
            } catch (IllegalAccessException e) {
                Log.w(TAG, "IllegalAccessException while setting up transparent bars");
            } catch (IllegalArgumentException e) {
                Log.w(TAG, "IllegalArgumentException while setting up transparent bars");
            } catch (InvocationTargetException e) {
                Log.w(TAG, "InvocationTargetException while setting up transparent bars");
            } finally {}
        }
    }
}
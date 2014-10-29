package com.botty.wall;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

public class FragmentTwo  extends Fragment {

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

    GridView grid;

    public FragmentTwo() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_wall, container,
                false);
        final CustomGrid adapter = new CustomGrid(getActivity(),own);
        grid=(GridView)view.findViewById(R.id.grid);
        grid.setVisibility(View.VISIBLE);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent i = new Intent(getActivity(),SetWall.class);
                i.putExtra("posi",position);
                Log.i("positionFrag", own[position]);
                startActivity(i);
            }
        });
        return view;
    }
}

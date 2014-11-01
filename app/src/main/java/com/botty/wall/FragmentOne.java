package com.botty.wall;

import android.app.ActivityOptions;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

public class FragmentOne extends Fragment {

    private String[] cyngn = {  "http://gnexushd.altervista.org/wallpapers/cyanogen/acidwashedcid.jpg",
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
            "http://gnexushd.altervista.org/wallpapers/cyanogen/winter_sunset.jpg"};

    GridView grid;

	public FragmentOne() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.activity_wall, container,
				false);
        final CustomGrid adapter = new CustomGrid(getActivity(), cyngn);
        grid=(GridView)view.findViewById(R.id.grid);
        grid.setVisibility(View.VISIBLE);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent i = new Intent(getActivity(),setWall.class);
                i.putExtra("pos", position);
                Log.i("positionFrag", cyngn[position]);
                startActivity(i);
            }
        });

		return view;
	}
}

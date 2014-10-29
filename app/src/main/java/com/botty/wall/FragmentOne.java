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

public class FragmentOne extends Fragment {

    private String[] cyngn = { "http://gnexushd.altervista.org/wallpapers/cyanogen/acidwashedcid.jpg", "http://gnexushd.altervista.org/im/wall_skyblue.jpg",
            "http://gnexushd.altervista.org/im/wall_batgirl.jpg"};

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
                i.putExtra("pos",position);
                Log.i("positionFrag", cyngn[position]);
                startActivity(i);
            }
        });

		return view;
	}
}

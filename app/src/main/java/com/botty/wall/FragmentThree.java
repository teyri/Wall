package com.botty.wall;

import android.app.ActionBar;
import android.app.Fragment;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;
import com.pkmmte.view.CircularImageView;

public class FragmentThree extends Fragment {

    ImageView imageView;
	public FragmentThree() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_layout_three, container,
				false);

        ActionBar actionBar = getActivity().getActionBar();
       // actionBar.setBackgroundDrawable(new ColorDrawable(0xaf44DDED));

        CircularImageView circularImageView = (CircularImageView)view.findViewById(R.id.me);
        imageView = (ImageView)view.findViewById(R.id.imgBack);

        UrlImageViewHelper.setUrlDrawable(imageView, "https://lh3.googleusercontent.com/-EpioF5dFo8g/VD2msKzHv4I/AAAAAAAAe2s/JM4LVuxs-bk/w891-h593-no/10700266_546449778790343_1360469627078645120_o.jpg");
        circularImageView.addShadow();

        return view;
	}

}

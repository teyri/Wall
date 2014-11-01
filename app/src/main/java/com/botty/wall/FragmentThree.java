package com.botty.wall;

import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;
import com.pkmmte.view.CircularImageView;

public class FragmentThree extends Fragment {

    ImageView imageView;
    TextView textView;
    ImageButton imageButton;

	public FragmentThree() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
        setHasOptionsMenu(true);

		View view = inflater.inflate(R.layout.fragment_layout_three, container,
				false);

        ActionBar actionBar = getActivity().getActionBar();
       // actionBar.setBackgroundDrawable(new ColorDrawable(0xaf44DDED));

        CircularImageView circularImageView = (CircularImageView)view.findViewById(R.id.me);
        imageView = (ImageView)view.findViewById(R.id.imgBack);
        textView = (TextView)view.findViewById(R.id.aboutME);
        imageButton = (ImageButton)view.findViewById(R.id.rate);

        UrlImageViewHelper.setUrlDrawable(imageView, "https://lh3.googleusercontent.com/-EpioF5dFo8g/VD2msKzHv4I/AAAAAAAAe2s/JM4LVuxs-bk/w891-h593-no/10700266_546449778790343_1360469627078645120_o.jpg");
        circularImageView.addShadow();
        textView.setText(Html.fromHtml("hi !! Sono interessato a Android, Google ed ubuntu e  all' informatica ingenerale. Mi piacciono i Manga/Anime e tra i miei preferiti ci sono indubbiamente Full Metal Panic, Ranma½, G.T.O. ,Kaichou wa Maid-sama!\n" +
                "ecc...\n" +
                "Poi di serie TV adoro The Big Bang Theory, Arrow,The Tomorrow People e come dimenticare Drake&Josh !!"));
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("market://details?id=com.botty.wall"));
                startActivity(intent);
            }
        });
        return view;
	}

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.about, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
            case R.id.support:
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://www.gnexushd.com/page/support.html"));
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}

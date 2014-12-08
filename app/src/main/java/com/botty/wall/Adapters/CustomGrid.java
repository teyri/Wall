package com.botty.wall.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.botty.wall.R;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

/**
 * Created by ivanbotty on 27/10/14.
 */

public class CustomGrid extends BaseAdapter{
   Context context;
    String[] image;

    public CustomGrid(Context context, String[] GalImages) {
        this.context = context;
        this.image = GalImages;
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return image.length;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View grid;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            grid = new View(context);
            grid = inflater.inflate(R.layout.grid_single, null);
        } else {
            grid = (View) convertView;
        }
        ImageView imageView = (ImageView)grid.findViewById(R.id.grid_image);
        UrlImageViewHelper.setUrlDrawable(imageView, image[position+0]);
        return grid;
    }
}
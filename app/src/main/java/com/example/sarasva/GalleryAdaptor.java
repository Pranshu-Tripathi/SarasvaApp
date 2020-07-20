package com.example.sarasva;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GalleryAdaptor extends BaseAdapter {

    Context context;
    ArrayList<String> urls;

    GalleryAdaptor(Context mContext, ArrayList<String> mUrls){

        context = mContext;
        urls = mUrls;
    }


    @Override
    public int getCount() {
        return urls.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View V = LayoutInflater.from(context).inflate(R.layout.gallary_image_item,null);
        ImageView imageView = V.findViewById(R.id.imagesEvent);
        Picasso.with(context)
                .load(urls.get(position))
                .placeholder(R.drawable.sarasvalogowhite)
                .into(imageView);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return V;
    }


}
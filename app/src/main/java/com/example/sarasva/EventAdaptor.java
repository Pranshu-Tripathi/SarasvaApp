package com.example.sarasva;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



public class EventAdaptor extends RecyclerView.Adapter<EventAdaptor.ImageViewHolder> {

    private int[] images;
    private Context context;

    public EventAdaptor(Context context1,int[] images){
        this.images = images;
        this.context = context1;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_list_design,parent,false);

        ImageViewHolder imageViewHolder = new ImageViewHolder(view);

        return imageViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        final int image_id = images[position];
        holder.imageView.setImageResource(image_id);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(image_id == R.drawable.tedx){
                    Intent intent = new Intent(context,TedxActivity.class);
                    context.startActivity(intent);
                }else if(image_id == R.drawable.mun){
                    Intent intent = new Intent(context,TedxActivity.class);
                    context.startActivity(intent);
                }else if(image_id == R.drawable.kavi){
                    Intent intent = new Intent(context,TedxActivity.class);
                    context.startActivity(intent);
                }else if(image_id == R.drawable.cognoscentia){
                    Intent intent = new Intent(context,TedxActivity.class);
                    context.startActivity(intent);
                }else if(image_id == R.drawable.litathon){
                    Intent intent = new Intent(context,TedxActivity.class);
                    context.startActivity(intent);
                }else if(image_id == R.drawable.brainbrawl){
                    Intent intent = new Intent(context,TedxActivity.class);
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.eventImage);
        }
    }


}

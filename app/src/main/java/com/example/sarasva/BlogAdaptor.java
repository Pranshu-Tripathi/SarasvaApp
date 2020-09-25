package com.example.sarasva;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class BlogAdaptor extends RecyclerView.Adapter<BlogAdaptor.MyViewHolder> {

    Context context;
    ArrayList<BlogItem> bList;

    public BlogAdaptor(Context c, ArrayList<BlogItem> m){
        this.context = c;
        this.bList = m;
    }


    @NonNull
    @Override
    public BlogAdaptor.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_blog_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull BlogAdaptor.MyViewHolder holder, int position) {

        Picasso.with(context).load(bList.get(position).getImgUrlBlog()).placeholder(R.drawable.brainbrawl).into(holder.imageViewBlog);
        holder.imageViewBlog.setScaleType(ImageView.ScaleType.FIT_XY);
        Picasso.with(context).load(bList.get(position).getImgUrlUser()).placeholder(R.drawable.profileimg).into(holder.circleImageView);
        holder.viewsText.setText(bList.get(position).getViews());
        holder.TitleText.setText(bList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return bList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        CircleImageView circleImageView;
        ImageView imageViewBlog,Fav;
        TextView viewsText, TitleText;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.WriterProfile);
            imageViewBlog = itemView.findViewById(R.id.blogImg);
            Fav = itemView.findViewById(R.id.like);
            viewsText = itemView.findViewById(R.id.viewsText);
            TitleText = itemView.findViewById(R.id.titleBlog);
        }
    }
}

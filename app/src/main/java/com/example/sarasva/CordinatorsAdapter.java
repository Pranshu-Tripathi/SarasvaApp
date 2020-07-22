package com.example.sarasva;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CordinatorsAdapter extends BaseExpandableListAdapter {
    Context context;
    private List<String> names;
    private List<String> imgURLF;
    private List<String> status;
    private HashMap<String,Coordinators> exp_details;
    private LayoutInflater inflater;


    public CordinatorsAdapter(Context context, List<String> names,List<String> imgURLF ,List<String> status, HashMap<String, Coordinators> exp_details) {
        this.context = context;
        this.names = names;
        this.imgURLF = imgURLF;
        this.status = status;
        this.exp_details = exp_details;
        this.inflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getGroupCount() {
        return names.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return names.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return exp_details.get(names.get(groupPosition));
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        CordiGrpViewHolder holder;
        String name = this.names.get(groupPosition);
        String status = this.status.get(groupPosition);


        if (convertView == null) {
            convertView = inflater.inflate(R.layout.cordi_list_grp, parent, false);

            holder = new CordiGrpViewHolder();
            holder.circleImageView = (CircleImageView) convertView.findViewById(R.id.profile_Pic_Header);
            holder.nameText = (TextView) convertView.findViewById(R.id.cordi_name);
            holder.statusText = (TextView) convertView.findViewById(R.id.cordi_status);
            convertView.setTag(holder);
        } else {
            holder = (CordiGrpViewHolder) convertView.getTag();
        }
        Picasso.with(context.getApplicationContext()).load(imgURLF.get(groupPosition)).placeholder(R.drawable.profileimg).into(holder.circleImageView);
        holder.nameText.setText(name);
        holder.statusText.setText(status);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final CordiItemViewHolder holder;
        Coordinators coordinators = (Coordinators) getChild(groupPosition, childPosition);


        if (convertView == null) {
            convertView = inflater.inflate(R.layout.cordi_list_item, parent, false);

            holder = new CordiItemViewHolder();
            holder.circleImageView = (CircleImageView) convertView.findViewById(R.id.profile_Pic);
            holder.enrollText = (TextView) convertView.findViewById(R.id.enrollment);
            holder.contactText = (TextView) convertView.findViewById(R.id.contactNumberCordi);

            convertView.setTag(holder);
        } else {
            holder = (CordiItemViewHolder) convertView.getTag();
        }

        Picasso.with(context.getApplicationContext())
                .load(coordinators.getImgUrl())
                .placeholder(R.drawable.profileimg)
                .into(holder.circleImageView);

        holder.enrollText.setText(coordinators.getEnroll());
        holder.contactText.setText(coordinators.getContact());

        LinearLayout linearLayoutl = (LinearLayout) convertView.findViewById(R.id.contactCordi);
        linearLayoutl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String uri = "tel:" + holder.contactText.getText().toString().trim();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(uri));
                context.startActivity(intent);
            }
        });

        LinearLayout linearLayout2 = (LinearLayout) convertView.findViewById(R.id.messageCordi);
        linearLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" + holder.enrollText.getText().toString().trim()+"@iiita.ac.in"));
                intent.putExtra(Intent.EXTRA_SUBJECT, "your_subject");
                intent.putExtra(Intent.EXTRA_TEXT, "your_text");
                context.startActivity(intent);

            }
        });

        LinearLayout linearLayout3  = (LinearLayout)convertView.findViewById(R.id.whatsappCordi);
        linearLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://api.whatsapp.com/send?phone=91"+holder.contactText.getText().toString().trim();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                context.startActivity(i);
            }
        });

        Animation animation = AnimationUtils.loadAnimation(context.getApplicationContext(),android.R.anim.fade_in);
        animation.setDuration(2000);
        convertView.startAnimation(animation);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private static class  CordiGrpViewHolder{
        CircleImageView circleImageView;
        TextView nameText;
        TextView statusText;
    }

    private static class CordiItemViewHolder{
        CircleImageView circleImageView;
        TextView enrollText;
        TextView contactText;
    }
}

package com.example.preethi.ngo_connnect;

import android.content.Context;
import android.media.Image;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import com.example.preethi.NGO_Connect.R;

/**
 * Created by Preethi on 14-03-2018.
 */

public class CustomAdapter extends BaseAdapter implements ListAdapter {

    Context context;
    ArrayList<android.media.Image> Poster;
    ArrayList<String> Events;
    ArrayList<String> Category;
    public CustomAdapter(
            Context context2,
            ArrayList<String> event_name,
            ArrayList<String> category,
            ArrayList<android.media.Image> poster
    )
    {

        this.context = context2;
        this.Events = event_name;
        this.Category = category;
    }

    public int getCount() {
        // TODO Auto-generated method stub
        return Events.size();
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }
    public View getView(int position, View child, ViewGroup parent) {

        Holder holder;

        LayoutInflater layoutInflater;

        if (child == null) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            child = layoutInflater.inflate(R.layout.eventslist, null);

            holder = new Holder();

            holder.poster = (ImageView) child.findViewById(R.id.poster);
            holder.eventname = (TextView) child.findViewById(R.id.eventname);
            holder.category = (TextView) child.findViewById(R.id.category);
            child.setTag(holder);

        } else {
            holder = (Holder) child.getTag();
        }
        //holder.textviewname.setText(poster.get(position));
        holder.eventname.setText(Events.get(position));
        holder.category.setText(Category.get(position));

        return child;
    }

    public class Holder {
        ImageView poster;
        TextView eventname;
        TextView category;
    }
}

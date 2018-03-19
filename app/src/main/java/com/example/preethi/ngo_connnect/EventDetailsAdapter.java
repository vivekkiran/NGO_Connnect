package com.example.preethi.ngo_connnect;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.preethi.NGO_Connect.R;

import java.util.ArrayList;

import static android.media.CamcorderProfile.get;

/**
 * Created by Preethi on 17-03-2018.
 */

public class EventDetailsAdapter extends BaseAdapter implements ListAdapter {

    Context context;
    ArrayList<String> EventName;
    ArrayList<String> Organisation;
    ArrayList<String> Amount;
    ArrayList<String> Venue;
    ArrayList<String> Date;
    ArrayList<String> Time;
    ArrayList<String> Descripion;

    public EventDetailsAdapter(
            Context context2,
            ArrayList<String> event_name,
            ArrayList<String> organisation ,
            ArrayList<String> amount,
            ArrayList<String> venue ,
            ArrayList<String> date ,
            ArrayList<String> time ,
            ArrayList<String> description

    )
    {


        this.context = context2;
        this.EventName = event_name;
        this.Organisation = organisation;
        this.Amount = amount;
        this.Venue = venue;
        this.Date = date;
        this.Time = time;
        this.Descripion = description;

    }
    public int getCount() {
        // TODO Auto-generated method stub
        return EventName.size();
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
            child = layoutInflater.inflate(R.layout.eventdetails, null);

            holder = new Holder();

            holder.eventname = (TextView) child.findViewById(R.id.eventname);
            holder.ngoname = (TextView) child.findViewById(R.id.ngoname);
            holder.amount = (TextView) child.findViewById(R.id.amount);
            holder.venue = (TextView) child.findViewById(R.id.venue);
            holder.eventdate = (TextView) child.findViewById(R.id.eventdate);
            holder.eventtime = (TextView) child.findViewById(R.id.eventtime);
            holder.description = (TextView) child.findViewById(R.id.description);
            child.setTag(holder);

        } else {

            holder = (Holder) child.getTag();
        }
        holder.eventname.setText(EventName.get(position));
        holder.ngoname.setText(Organisation.get(position));
        holder.amount.setText(Amount.get(position));
        holder.venue.setText(Venue.get(position));
        holder.eventdate.setText(Date.get(position));
        holder.eventtime.setText(Time.get(position));
        holder.description.setText(Descripion.get(position));

        return child;
    }

    public class Holder {
        TextView eventname;
        TextView ngoname;
        TextView amount;
        TextView venue;
        TextView eventdate;
        TextView eventtime;
        TextView description;
    }
}

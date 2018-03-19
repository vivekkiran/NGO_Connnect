package com.example.preethi.ngo_connnect;

import android.content.Context;
import android.media.FaceDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.preethi.NGO_Connect.R;

import java.util.ArrayList;


/**
 * Created by Preethi on 19-03-2018.
 */

public class ProfileAdapter extends BaseAdapter implements ListAdapter {
    Context context;
    ArrayList<String> UserName;
    ArrayList<String> Description;
    ArrayList<String> Address;
    ArrayList<String> Email;
    ArrayList<String> Contact;
    ArrayList<String> Experience;
    ArrayList<String> Website;
    ArrayList<String> Facebook;
    ArrayList<String> Linkedin;


    public ProfileAdapter(
            Context context2,
            ArrayList<String> userName,
            ArrayList<String> description ,
            ArrayList<String> address,
            ArrayList<String> email,
            ArrayList<String> contact,
            ArrayList<String> experience ,
            ArrayList<String> website ,
            ArrayList<String> facebook ,
            ArrayList<String> linkedin

    )
    {


        this.context = context2;
        this.UserName = userName;
        this.Description = description;
        this.Address = address;
        this.Email = email;
        this.Contact = contact;
        this.Experience = experience;
        this.Website = website;
        this.Facebook = facebook;
        this.Linkedin = linkedin;

    }
    public int getCount() {
        // TODO Auto-generated method stub
        return UserName.size();
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

        ProfileAdapter.Holder holder;

        LayoutInflater layoutInflater;

        if (child == null) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            child = layoutInflater.inflate(R.layout.eventdetails, null);

            holder = new Holder();

            holder.username = (TextView) child.findViewById(R.id.user_profile_name);
            holder.description = (TextView) child.findViewById(R.id.user_profile_short_bio);
            holder.address = (TextView) child.findViewById(R.id.address_profile);
            holder.email = (TextView) child.findViewById(R.id.email_profile);
            holder.contact = (TextView) child.findViewById(R.id.contact_profile);
            holder.experience = (TextView) child.findViewById(R.id.experience_profile);
            holder.website = (TextView) child.findViewById(R.id.website_profile);
            holder.facebook = (TextView) child.findViewById(R.id.facebook_profile);
            holder.linkedin = (TextView) child.findViewById(R.id.linkedin_profile);
            child.setTag(holder);

        } else {

            holder = (ProfileAdapter.Holder) child.getTag();
        }
        holder.username.setText(UserName.get(position));
        holder.description.setText(Description.get(position));
        holder.address.setText(Address.get(position));
        holder.email.setText(Email.get(position));
        holder.contact.setText(Contact.get(position));
        holder.experience.setText(Experience.get(position));
        holder.website.setText(Website.get(position));
        holder.facebook.setText(Facebook.get(position));
        holder.linkedin.setText(Linkedin.get(position));

        return child;
    }

    public class Holder {
        TextView username;
        TextView description;
        TextView address;
        TextView email;
        TextView contact;
        TextView experience;
        TextView website;
        TextView facebook;
        TextView linkedin;
    }
}

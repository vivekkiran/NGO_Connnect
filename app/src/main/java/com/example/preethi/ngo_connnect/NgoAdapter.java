package com.example.preethi.ngo_connnect;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.preethi.NGO_Connect.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Preethi on 21-03-2018.
 */

public class NgoAdapter extends BaseAdapter implements ListAdapter {

    Context context;
    ArrayList<String> NGOS;
    ArrayList<String> Contact;
    public NgoAdapter(
            Context context2,
            ArrayList<String> ngos ,
            ArrayList<String> contact
            //ArrayList<Bitmap> poster
    )
    {

        this.context = context2;
        this.NGOS = ngos;
        this.Contact = contact;
    }

    public int getCount() {
        // TODO Auto-generated method stub
        return NGOS.size();
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

        NgoAdapter.Holder holder;

        LayoutInflater layoutInflater;

        if (child == null) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            child = layoutInflater.inflate(R.layout.ngos, null);

            holder = new NgoAdapter.Holder();

            holder.ngoname = (TextView) child.findViewById(R.id.nn);
            //holder.contact = (TextView) child.findViewById(R.id.ngoc);
            child.setTag(holder);

        } else {
            holder = (NgoAdapter.Holder) child.getTag();
        }
        holder.ngoname.setText(NGOS.get(position));

        return child;
    }

    public class Holder {
        TextView ngoname;
        TextView contact;
    }
}

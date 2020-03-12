package com.example.scoutinterfacedesign.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.scoutinterfacedesign.Helpers.HelperReflection;
import com.example.scoutinterfacedesign.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

public class FlexAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;

    private List dataSource;

    private int resource;
    private Hashtable<Integer, String> map;

    public FlexAdapter(Context context, List source, int resourceId, Hashtable<Integer, String> mapping) {
        this.context = context;
        this.dataSource = source;
        this.resource = resourceId;
        this.map = mapping;
    }

    @Override
    public int getCount() {
        return this.dataSource.size();
    }

    @Override
    public Object getItem(int position) {
        return this.dataSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null)
            convertView = LayoutInflater.from(context).inflate(context.getResources().getLayout(resource), parent, false);

        Object currentItem = getItem(position);

        for (Integer i : map.keySet())
        {
            TextView target = convertView.findViewById(i.intValue());

            if(target != null)
                target.setText( HelperReflection.Invoke(map.get(i), currentItem).toString() );
        }

        return convertView;
    }
}

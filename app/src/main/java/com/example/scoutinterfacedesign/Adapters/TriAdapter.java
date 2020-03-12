package com.example.scoutinterfacedesign.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.scoutinterfacedesign.*;
import com.example.scoutinterfacedesign.Helpers.HelperResource;
import com.example.scoutinterfacedesign.Helpers.HelperReflection;

import java.util.List;

public class TriAdapter extends BaseAdapter
{
    private Context context;
    private List dataSource;

    private String headerProperty, subHeaderProperty, extraProperty;
    private HelperResource.ColorTheme theme;

    public TriAdapter(Context context, List dataSource)
    {
        this.context = context;
        this.dataSource = dataSource;
    }

    public TriAdapter(Context context, List dataSource, String headerProperty, String subHeaderProperty, String extraProperty)
    {
        this(context, dataSource);

        this.headerProperty = headerProperty;
        this.subHeaderProperty = subHeaderProperty;
        this.extraProperty = extraProperty;
    }

    public TriAdapter(Context context, List dataSource, String headerProperty, String subHeaderProperty, String extraProperty, HelperResource.ColorTheme theme)
    {
        this(context, dataSource, headerProperty, subHeaderProperty, extraProperty);

        this.theme = theme;
    }

    public void setSource(List source)
    {
        this.dataSource = source;

        this.notifyDataSetChanged();
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
        try
        {
            if(convertView == null)
                convertView = LayoutInflater.from(this.context).inflate(R.layout.list_item_group, parent, false);

            TextView header, subHeader, extra;

            header = convertView.findViewById(R.id.tv_header);
            subHeader = convertView.findViewById(R.id.tv_subHeader);
            extra = convertView.findViewById(R.id.tv_extra);

            Object current = getItem(position);

            if(headerProperty != null)
            {
                Object headerValue = HelperReflection.Invoke(headerProperty, current);
                header.setText(headerValue == null ? "" : headerValue.toString());
            }

            if(subHeaderProperty != null)
            {
                Object subHeaderValue = HelperReflection.Invoke(subHeaderProperty, current);
                subHeader.setText(subHeaderValue == null ? "" : subHeaderValue.toString());
            }

            if(headerProperty != null)
            {
                Object extraValue = HelperReflection.Invoke(extraProperty, current);
                extra.setText(extraValue == null ? "" : extraValue.toString());
            }

            if(theme != null)
            {
                header.setTextColor(context.getResources().getColor(theme.colorSecondary));
                subHeader.setTextColor(context.getResources().getColor(theme.colorMain));
                extra.setTextColor(context.getResources().getColor(theme.colorSecondary));
            }

            return convertView;
        }
        catch (Exception x)
        {
            Toast.makeText(context, x.getMessage(), Toast.LENGTH_SHORT).show();
            return null;
        }
    }
}

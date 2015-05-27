package com.ivansolutions.testalsea.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ivansolutions.testalsea.R;
import com.ivansolutions.testalsea.beans.EarthQuakeItemList;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by eliasivancarrillogonzalez on 25/05/15.
 */
public class ListaPrincipalAdapter extends BaseAdapter {

    private ArrayList<EarthQuakeItemList> listaElementos;
    private Context context;
    private LayoutInflater inflater;
    private SimpleDateFormat dateFormat;

    public ListaPrincipalAdapter(ArrayList<EarthQuakeItemList> listaElementos, Context context) {
        this.listaElementos = listaElementos;
        this.context = context;

        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        dateFormat = new SimpleDateFormat("EEEE, MMMM dd, yyyy", Locale.getDefault());
    }

    @Override
    public int getCount() {
        return listaElementos.size();
    }

    @Override
    public EarthQuakeItemList getItem(int position) {
        return listaElementos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        EarthQuakeItemList item = getItem(position);

        if(convertView == null){
            convertView = inflater.inflate(R.layout.item_list, null);
        }

        TextView tvMag = (TextView)convertView.findViewById(R.id.tv_mag);
        TextView tvPlace = (TextView)convertView.findViewById(R.id.tv_place);;
        TextView tvDate = (TextView)convertView.findViewById(R.id.tv_date);

        tvMag.setText(Float.toString(item.getMagnitud()));
        tvPlace.setText(item.getPlace());
        tvDate.setText(dateFormat.format(new Date(item.getTime())));

        return convertView;
    }
}

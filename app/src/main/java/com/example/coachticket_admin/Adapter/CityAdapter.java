package com.example.coachticket_admin.Adapter;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.coachticket_admin.Model.City;
import com.example.coachticket_admin.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CityAdapter extends BaseAdapter {
    private Context context;

    public CityAdapter(Context context, ArrayList<City> citys) {
        this.context = context;
        this.citys = citys;
    }

    private ArrayList<City> citys;

    @Override
    public int getCount() {
        return citys.size();
    }

    @Override
    public Object getItem(int i) {
        return citys.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            view = View.inflate(context, R.layout.city_cardview, null);
        }

        TextView cname = view.findViewById(R.id.name);
        TextView station = view.findViewById(R.id.station);
        TextView distance = view.findViewById(R.id.distance);

        City city = citys.get(i);

        cname.setText(city.getCname());
        station.setText("Bến xe: "+city.getStation());
        distance.setText("Khoảng cách: "+city.getDistance());

        return view;
    }
}

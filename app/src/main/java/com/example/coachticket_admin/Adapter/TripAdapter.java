package com.example.coachticket_admin.Adapter;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.coachticket_admin.Model.Trip;
import com.example.coachticket_admin.R;

import java.util.ArrayList;

public class TripAdapter extends BaseAdapter {
    private Context context;

    public TripAdapter(Context context, ArrayList<Trip> trips) {
        this.context = context;
        this.trips = trips;
    }

    private ArrayList<Trip> trips;

    @Override
    public int getCount() {
        return trips.size();
    }

    @Override
    public Object getItem(int i) {
        return trips.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            view = View.inflate(context, R.layout.trip_cardview, null);
        }

        TextView tripname = view.findViewById(R.id.tripname);
        TextView date = view.findViewById(R.id.date);
        TextView starttime = view.findViewById(R.id.starttime);
        TextView travelcar = view.findViewById(R.id.travelcar);

        Trip trip = trips.get(i);

        tripname.setText(trip.getTripName());
        date.setText("Ngày: "+trip.getDate());
        starttime.setText("Xuất phát: "+trip.getStarttime());
        travelcar.setText("Xe: "+trip.getTravelCar());

        return view;
    }
}

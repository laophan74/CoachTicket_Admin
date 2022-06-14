package com.example.coachticket_admin.Adapter;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.coachticket_admin.Model.Coach;
import com.example.coachticket_admin.R;

import java.util.ArrayList;

public class CoachAdapter extends BaseAdapter {
    private Context context;

    public CoachAdapter(Context context, ArrayList<Coach> coachs) {
        this.context = context;
        this.coachs = coachs;
    }

    private ArrayList<Coach> coachs;

    @Override
    public int getCount() {
        return coachs.size();
    }

    @Override
    public Object getItem(int i) {
        return coachs.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            view = View.inflate(context, R.layout.coach_cardview, null);
        }

        TextView plate = view.findViewById(R.id.plate);
        TextView detail = view.findViewById(R.id.detail);
        TextView price = view.findViewById(R.id.price);
        TextView speed = view.findViewById(R.id.speed);
        TextView seat1 = view.findViewById(R.id.seat1);
        TextView seat2 = view.findViewById(R.id.seat2);

        Coach coach = coachs.get(i);

        plate.setText(coach.getPlate());
        detail.setText("Chi tiết: "+coach.getDetail());
        price.setText("Giá: "+coach.getPrice());
        speed.setText("Tốc độ: "+coach.getSpeed());
        seat1.setText("Ghế tầng 1: "+coach.getNumseat1());
        seat2.setText("Ghế tầng 2: "+coach.getNumseat2());

        return view;
    }
}

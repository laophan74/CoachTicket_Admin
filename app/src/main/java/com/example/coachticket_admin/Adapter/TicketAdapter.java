package com.example.coachticket_admin.Adapter;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.coachticket_admin.Model.Ticket;
import com.example.coachticket_admin.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class TicketAdapter extends BaseAdapter {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private Context context;

    public TicketAdapter(Context context, ArrayList<Ticket> coachs) {
        this.context = context;
        this.coachs = coachs;
    }

    private ArrayList<Ticket> coachs;

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
            view = View.inflate(context, R.layout.ticket_cardview, null);
        }

        TextView name = view.findViewById(R.id.tripname);
        TextView date = view.findViewById(R.id.date);
        TextView userid = view.findViewById(R.id.userid);


        Ticket Ticket = coachs.get(i);


        name.setText(Ticket.getTripName());
        userid.setText(Ticket.getUserID());
        //date.setText(String.valueOf( Ticket.get()));

        return view;
    }
}

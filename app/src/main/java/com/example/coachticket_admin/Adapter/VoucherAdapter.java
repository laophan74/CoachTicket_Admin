package com.example.coachticket_admin.Adapter;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.coachticket_admin.Model.Voucher;
import com.example.coachticket_admin.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class VoucherAdapter extends BaseAdapter {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private Context context;

    public VoucherAdapter(Context context, ArrayList<Voucher> vouchers) {
        this.context = context;
        this.vouchers = vouchers;
    }

    private ArrayList<Voucher> vouchers;

    @Override
    public int getCount() {
        return vouchers.size();
    }

    @Override
    public Object getItem(int i) {
        return vouchers.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            view = View.inflate(context, R.layout.voucher_cardview, null);
        }

        TextView id = view.findViewById(R.id.idvoucher);
        TextView status = view.findViewById(R.id.status);
        TextView off = view.findViewById(R.id.off);

        Voucher voucher = vouchers.get(i);

        id.setText(voucher.getId());
        off.setText("Giảm giá: "+String.valueOf(voucher.getOff()) + "%");

        String st;
        if(voucher.getStatus()==true)st = "Đã kích hoạt";
        else st = "Chưa kích hoạt";
        status.setText("Trạng thái: "+st);

        return view;
    }
}

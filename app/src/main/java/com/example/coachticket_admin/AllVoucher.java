package com.example.coachticket_admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.coachticket_admin.Adapter.VoucherAdapter;
import com.example.coachticket_admin.Model.Voucher;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AllVoucher extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private RelativeLayout add;
    private ListView listView;
    private VoucherAdapter voucherAdapter;

    private ArrayList<Voucher> lVoucher = new ArrayList<>();


    public static String document;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_voucher);
        getSupportActionBar().hide();

        listView = findViewById(R.id.listview);
        add = findViewById(R.id.add);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AllVoucher.this, AddVoucher.class));
            }
        });
        GetAllVoucher();

    }

    private void GetAllVoucher(){
        db.collection("Vouchers").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot doc : task.getResult()){
                                Voucher voucher = doc.toObject(Voucher.class);
                                voucher.setDocument(doc.getId());
                                lVoucher.add(voucher);
                            }
                            voucherAdapter = new VoucherAdapter(AllVoucher.this, lVoucher);
                            listView.setAdapter(voucherAdapter);
                        }
                    }
                });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Voucher voucher = lVoucher.get(i);
                document = voucher.getDocument();
                startActivity(new Intent(AllVoucher.this, EditVoucher.class));

            }
        });
    }
}
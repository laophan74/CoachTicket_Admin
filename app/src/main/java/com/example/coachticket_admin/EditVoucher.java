package com.example.coachticket_admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.coachticket_admin.Model.Coach;
import com.example.coachticket_admin.Model.Voucher;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditVoucher extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private TextView id;
    private Button status;

    private Voucher voucher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_voucher);
        getSupportActionBar().hide();

        id = findViewById(R.id.idvoucher);
        status = findViewById(R.id.status);

        //click status button
        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(status.getText()=="Đã kích hoạt"){
                    status.setText("Chưa kích hoạt");
                    status.setBackgroundResource(R.drawable.buttonshapeyellow);
                    db.collection("Vouchers").document(AllVoucher.document)
                            .update("status",false).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                        }
                    });
                }
                else {
                    status.setText("Đã kích hoạt");
                    status.setBackgroundResource(R.drawable.buttonshape);
                    db.collection("Vouchers").document(AllVoucher.document)
                            .update("status",true).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                        }
                    });
                }
            }
        });

        Load();
    }

    private void Load(){
        db.collection("Vouchers").document(AllVoucher.document)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot doc = task.getResult();
                    voucher = doc.toObject(Voucher.class);
                    id.setText(voucher.getId());

                    String st;
                    if(voucher.getStatus()==true)st = "Đã kích hoạt";
                    else st = "Chưa kích hoạt";
                    status.setText(st);
                }
            }
        });
    }
}
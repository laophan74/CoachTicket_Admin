package com.example.coachticket_admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coachticket_admin.Model.Coach;
import com.example.coachticket_admin.Model.Voucher;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditVoucher extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private TextView id;
    private Button status;

    private Voucher voucher;
    private RelativeLayout delete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_voucher);
        getSupportActionBar().hide();

        id = findViewById(R.id.idvoucher);
        status = findViewById(R.id.status);
        delete = findViewById(R.id.delete);

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

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Delete();
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

    private void Delete(){
        AlertDialog.Builder altd = new AlertDialog.Builder(EditVoucher.this);
        altd.setMessage("Bạn có muốn xoá?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db.collection("Vouchers").document(AllVoucher.document)
                                .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(EditVoucher.this, "Xoá thành công", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(EditVoucher.this, AllVoucher.class));

                            }
                        });
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

        AlertDialog alert = altd.create();
        alert.setTitle("Cảnh báo!!!");
        alert.show();
    }
}
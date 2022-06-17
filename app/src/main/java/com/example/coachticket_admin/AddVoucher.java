package com.example.coachticket_admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddVoucher extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Button add;
    private TextView id;
    private TextView off;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_voucher);
        getSupportActionBar().hide();

        id = findViewById(R.id.idvoucher);
        off = findViewById(R.id.off);
        add = findViewById(R.id.addBtn);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!off.getText().toString().matches("[0-9]+"))
                {
                    Toast.makeText(AddVoucher.this, "Nhập sai định dạng!", Toast.LENGTH_SHORT).show();
                }
                else if (id.getText().toString() == "" ||
                        off.getText().toString() == "" ){
                    Toast.makeText(AddVoucher.this, "Không được để trống", Toast.LENGTH_SHORT).show();
                }
                else {
                    addBtn();
                }
            }
        });
    }
    private void addBtn() {
        String Id = id.getText().toString();
        String Off = off.getText().toString();

        Map<String, Object> docData = new HashMap<>();
        docData.put("id", Id);
        docData.put("off", Integer.parseInt(Off));
        docData.put("status", false);

        db.collection("Vouchers").document(Id)
                .set(docData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(AddVoucher.this, "Thành công!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AddVoucher.this, AllVoucher.class));
            }
        });

    }
}
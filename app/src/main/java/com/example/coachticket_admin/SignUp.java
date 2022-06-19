package com.example.coachticket_admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import io.grpc.TlsServerCredentials;

public class SignUp extends AppCompatActivity {
    private ImageView back;
    private EditText tvUsername;
    private EditText tvPassword;
    private EditText tvRepeatPass;
    private EditText tvEmail;


    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().hide();

        tvUsername = findViewById(R.id.tvUsername);
        tvPassword = findViewById(R.id.tvPassword);
        tvRepeatPass = findViewById(R.id.tvRepeatPass);
        tvEmail = findViewById(R.id.tvEmail);

        back = findViewById(R.id.backPress);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUp.this, LoginActivity.class));

            }
        });

    }
    private void SignUpAccount(){
        String _username = tvUsername.getText().toString().trim();
        String _pass = tvPassword.getText().toString().trim();
        String _repass = tvRepeatPass.getText().toString().trim();
        String _email = tvEmail.getText().toString().trim();
        if(_username.equals("") || _pass.equals("") || _email.equals("") || _repass.equals("")){
            Toast.makeText(SignUp.this, "Xin điền thông tin đăng ký đầy đủ", Toast.LENGTH_SHORT).show();
        }
        if(!_pass.equals(_repass)){
            Toast.makeText(SignUp.this, "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
        }
        else{

        }
    }
}
package com.example.coachticket_admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coachticket_admin.databinding.ActivityLoginBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static FirebaseAuth mAuth;
    public static FirebaseUser mClient;

    private TextView signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        mAuth = FirebaseAuth.getInstance();
        binding.btnLogin.setOnClickListener(view -> SignInAccount());
        binding.signup.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this, SignUp.class)));

        setContentView(binding.getRoot());
    }
    private void SignInAccount(){
        String _email = binding.tvEmail.getText().toString();
        String _pass = binding.tvPassword.getText().toString();
        mAuth.signInWithEmailAndPassword(_email, _pass)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        db.collection("Admin").whereEqualTo("email", _email)
                                .get().addOnCompleteListener(task1 -> {
                                    if(task1.isSuccessful()){
                                        if(task1.getResult() != null){
                                            startActivity(new Intent(this, Main.class));
                                            Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                            mClient = mAuth.getCurrentUser();
                                        }
                                        else{
                                            Toast.makeText(this, "Không có tài khoản", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    else{
                                        Toast.makeText(this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                                    }
                                });
                        Intent intent = new Intent(this, Main.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
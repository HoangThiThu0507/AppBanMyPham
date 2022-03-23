package com.example.projectcuoikhoa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    TextView tvDangKiNgay  ,tvLogin;
    EditText etSdt_email_Login,etPassword_Login;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tvDangKiNgay = findViewById(R.id.tvDangKiNgay);
        etSdt_email_Login = findViewById(R.id.etSdt_email_Login);
        etPassword_Login = findViewById(R.id.etPassword_Login);
        tvLogin = findViewById(R.id.tvLogin);
        progressDialog = new ProgressDialog(this);
        SignUp();
        Login();
    }
    private void SignUp() {
        tvDangKiNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
    private void Login(){
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnClickLogin();
            }
        });
    }

    private void OnClickLogin() {
        String email = etSdt_email_Login.getText().toString().trim();
        String password = etPassword_Login.getText().toString().trim();
        progressDialog.show();
        FirebaseAuth auth =FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finishAffinity();
                        } else {
                            Toast.makeText(LoginActivity.this, "Đăng nhập thất bại",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
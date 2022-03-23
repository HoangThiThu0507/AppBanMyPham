package com.example.projectcuoikhoa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class SignUpActivity extends AppCompatActivity {
    EditText etSdt_email_SignUp,etPassword_SignUp, etConfirmPassword_SignUp;
    TextView tvSignUp, tvChuyenDenDangNhap;
    private ProgressDialog progressDialog;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        etSdt_email_SignUp = findViewById(R.id.etSdt_email_SignUp);
        etPassword_SignUp = findViewById(R.id.etPassword_SignUp);
        etConfirmPassword_SignUp = findViewById(R.id.etConfirmPassword_SignUp);
        tvSignUp = findViewById(R.id.tvSignUp);
        tvChuyenDenDangNhap = findViewById(R.id.tvChuynDenDangNhap);
        progressDialog = new ProgressDialog(this);
        initListener();
    }

    private void initListener() {
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                OnClickSingUp();
            }
        });
        tvChuyenDenDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void OnClickSingUp() {
        String email = etSdt_email_SignUp.getText().toString().trim();
        String password = etPassword_SignUp.getText().toString().trim();
        String confirm_password = etConfirmPassword_SignUp.getText().toString().trim();
        if(TextUtils.isEmpty(email)){
            etSdt_email_SignUp.setError("Chưa nhập email");
            return;
        }
        if(TextUtils.isEmpty(password)){
            etSdt_email_SignUp.setError("Chưa nhập password");
            return;
        }
        if(TextUtils.isEmpty(confirm_password)){
            etSdt_email_SignUp.setError("Chưa nhập password");
            return;
        }
        if(password.equals(confirm_password) ==false){
            etConfirmPassword_SignUp.setError("mật khẩu xác nhận chưa chính xác");
            return;
        }
        progressDialog.show();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(SignUpActivity.this, "Đăng kí thành công",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                            startActivity(intent);
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            firebaseDatabase = FirebaseDatabase.getInstance();
                            databaseReference = firebaseDatabase.getReference();
                            databaseReference.child("GioHang").child(user.getUid()).setValue("id_giohang");
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignUpActivity.this, "Đăng kí thất bại",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
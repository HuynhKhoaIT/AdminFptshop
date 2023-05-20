package com.example.applestore.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.applestore.APIService.APIService;
import com.example.applestore.Adapter.CategoryAdapter;
import com.example.applestore.R;
import com.example.applestore.Retrofit.RetrofitClient;
import com.example.applestore.SharedPreferences.SharedPrefManager;
import com.example.applestore.model.Cart;
import com.example.applestore.model.User;
import com.google.android.gms.common.internal.ConnectionTelemetryConfiguration;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;
public class SignUpActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private EditText signupEmail, signupPassword, signupCusName, signupPhone,signupAddress;
    private Button signupButton;
    private TextView loginRedirecText;
    private Context context = this;
    APIService apiService = RetrofitClient.getRetrofit().create(APIService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        // ánh xạ
        auth = FirebaseAuth.getInstance();

        signupEmail = findViewById(R.id.signup_email);
        signupPassword = findViewById(R.id.signup_password);
        signupCusName = findViewById(R.id.signup_cusName);
        signupPhone = findViewById(R.id.signup_phone);
        signupAddress = findViewById(R.id.signup_address);

        signupButton = findViewById(R.id.signup_button);
        loginRedirecText = findViewById(R.id.loginRedirectText);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getValue
                String name = signupCusName.getText().toString();
                String email = signupEmail.getText().toString().trim();
                String pass = signupPassword.getText().toString().trim();
                String phone= signupPhone.getText().toString();
                String address = signupAddress.getText().toString();

                if(email.isEmpty()){
                    signupEmail.setError("Email cannot be empty");
                }
                if(pass.isEmpty()){
                    signupPassword.setError("Password cannot be empty");
                }else{
                    // Firebase
//                    auth.createUserWithEmailAndPassword(user, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            if(task.isSuccessful()){
//                                Toast.makeText(SignUpActivity.this,"SignUp Successful", Toast.LENGTH_SHORT).show();
//                                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
//                            }else{
//                                Toast.makeText(SignUpActivity.this, "Signup Failed"+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
                    User user = new User(name,email,phone,address,pass,1,0);
                    //Database
//                    createAccount(user);
                    createNewAccount(user);

                }
            }
        });
        loginRedirecText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
            }
        });
    }
    public void createAccount(User user){
        Call<User> call = apiService.getUserByEmail(user.getEmail());
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful())
                {
                    User user = response.body();
                    if(user.getEmail().equals("")){
                        createNewAccount(user);
                    }
                    else {
                        Toast.makeText(context,"Email mã tồn tại",Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(context,"Email mã tồn tại",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.i("TAG", t.toString());
            }
        });
    }
    private  void createNewAccount(User user){
        Call<User> call = apiService.createUser(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    Toast.makeText(SignUpActivity.this,"Đăng ký thành công",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(SignUpActivity.this,"Đăng ký thất bại",Toast.LENGTH_LONG).show();
                    Log.i("TAG","fail");
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.i("TAG", t.toString());
            }
        });
    }
    private void checkSignup(String email){

    }


}
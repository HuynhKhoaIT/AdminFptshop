package com.example.applestore.Activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.gbuttons.GoogleSignInButton;
import com.example.applestore.APIService.APIService;
import com.example.applestore.Adapter.CategoryAdapter;
import com.example.applestore.R;
import com.example.applestore.Retrofit.RetrofitClient;
import com.example.applestore.SharedPreferences.SharedPrefManager;
import com.example.applestore.model.Category;
import com.example.applestore.model.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private EditText loginEmail, loginPassword;
    private Button loginButton;

    private Context context = this;

    APIService apiService = RetrofitClient.getRetrofit().create(APIService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Đã đăng nhập
        if(SharedPrefManager.getInstance(context).isLoggedIn()){
            finish();
            startActivity(new Intent(context, MainActivity.class));
        }

        auth = FirebaseAuth.getInstance();
        loginEmail = findViewById(R.id.login_email);
        loginPassword = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = loginEmail.getText().toString();
                String pass = loginPassword.getText().toString();
                signIn(email, pass);
            }
        });

    }
    private void signIn(String email, String pass) {
        if(!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            if(!pass.isEmpty()){
                signInWithDB(email,pass);
            }else{
                loginPassword.setError("password cannot be empty");
            }
        }else if(email.isEmpty()){
            loginEmail.setError("Email cannot be empty");
        }else{
            loginEmail.setError("Please enter valid email");
        }
    }
    private void signInWithDB(String email,String pass){
        Call<User> call = apiService.loginUser(email,pass);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if(response.isSuccessful()){
                    User user = response.body();
                    if(user!=null)
                    {
                        // Lưu thông tin đăng nhập
                        SharedPrefManager.getInstance(context).userLogin(new User(
                                user.getMaKH(),
                                user.getTenKH(),
                                user.getEmail(),
                                user.getPhone(),
                                user.getDiaChi(),
                                user.getmK(),
                                user.getIsUser(),
                                user.getIsAdmin()
                        ));
                        Toast.makeText(LoginActivity.this,"Đăng nhập thành công",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        System.out.println("User null !");
                        showLoginFailedDialog();
                    }
                }else{
                    Log.i("TAG","fail");
                    showLoginFailedDialog();
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                showLoginFailedDialog();
                Log.i("TAG", t.toString());
            }
        });
    }
    private void showLoginFailedDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Lỗi đăng nhập");
        builder.setMessage("Thông tin đăng nhập không hợp lệ. Vui lòng thử lại.");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
}
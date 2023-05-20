package com.example.applestore.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.applestore.APIService.APIService;
import com.example.applestore.R;
import com.example.applestore.Retrofit.RetrofitClient;
import com.example.applestore.SharedPreferences.SharedPrefManager;
import com.example.applestore.model.User;

import java.util.ConcurrentModificationException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class UpdateAccount extends AppCompatActivity {

    APIService apiService = RetrofitClient.getRetrofit().create(APIService.class);
    EditText update_cusName, update_email, update_phone,update_address;
    Button update_button;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_account);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // anh xa
        context = this;
        update_cusName = findViewById(R.id.update_cusName);
//        update_email = findViewById(R.id.update_email);
        update_phone = findViewById(R.id.update_phone);
        update_address = findViewById(R.id.update_address);
        update_button = findViewById(R.id.update_button);

        User user = SharedPrefManager.getInstance(this).getUser();

        update_address.setText(user.getDiaChi());
        update_phone.setText(user.getPhone());
        update_cusName.setText(user.getTenKH());
//        update_email.setText(user.getEmail());


        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String address = update_address.getText()+"";
                String phone = update_phone.getText()+"";
                String name = update_cusName.getText()+"";
//                String email = update_email.getText()+"";

                if(address.equals("") || phone.equals("") || name.equals(""))
                {
                    Toast.makeText(UpdateAccount.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                else {
//                    user.setEmail(email);
                    user.setPhone(phone);
                    user.setTenKH(name);
                    user.setDiaChi(address);
                    updateUser(user);
                }
            }

        });
    }
    //    Bắt sự kiện khi bấm vào nút mũi tên quay lại
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:break;
        }

        return super.onOptionsItemSelected(item);
    }
    private  void updateUser(User user){
        Call<User> call = apiService.updateUser(user.getMaKH(),user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    System.out.println(response.body());
                    Toast.makeText(context,"Thay đổi thành công",Toast.LENGTH_LONG).show();
                    User user1 = response.body();
                    if(user1!=null)
                    {
                        //Dang xuat database
                        SharedPrefManager.getInstance(context).logout();
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
                    }
                    Intent intent = new Intent(context, UpdateAccount.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(context,"Thay đổi thất bại",Toast.LENGTH_LONG).show();
                    Log.i("TAG","fail");
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.i("TAG", t.toString());
            }
        });
    }
}
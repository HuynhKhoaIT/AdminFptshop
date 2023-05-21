package com.example.applestore.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.applestore.APIService.APIService;
import com.example.applestore.Adapter.UserAdapter;
import com.example.applestore.Interface.QuantityChangeListener;
import com.example.applestore.R;
import com.example.applestore.Retrofit.RetrofitClient;
import com.example.applestore.SharedPreferences.SharedPrefManager;
import com.example.applestore.Utils.CurrencyFormatter;
import com.example.applestore.model.CartDetail;
import com.example.applestore.model.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CusFragment extends Fragment  {
    private Context context;
    APIService apiService = RetrofitClient.getRetrofit().create(APIService.class);

    ArrayList<User> listUsers;
    RecyclerView rcItemCustomer;
    UserAdapter userAdapter;

    public CusFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cus, container, false);
        //Ánh xạ
        context = getActivity();
        rcItemCustomer = view.findViewById(R.id.rcItemCustomer);
        rcItemCustomer.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        getAllUsers();
//        cart_checkout_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context, CheckoutActivity.class);
//                context.startActivity(intent);
//            }
//        });
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        actionBar.setTitle("List Customer");
        return view;
    }
    private void getAllUsers(){
        Call<ArrayList<User>> call = apiService.getAllUser();
        call.enqueue(new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                if(response.isSuccessful()){
                    ArrayList<User> users = response.body();
                    listUsers = users;
                    System.out.println(users.size());
                    userAdapter = new UserAdapter(getContext(),listUsers);
                    rcItemCustomer.setHasFixedSize(true);
                    rcItemCustomer.setAdapter(userAdapter);
                    userAdapter.notifyDataSetChanged();
                }else{
                    System.out.println("Không lấy được users");
                }
            }
            @Override
            public void onFailure(Call<ArrayList<User>> call, Throwable t) {
                Log.i("TAG", t.toString());
                System.out.println("Lỗi kết nối API");
            }
        });
    }
}
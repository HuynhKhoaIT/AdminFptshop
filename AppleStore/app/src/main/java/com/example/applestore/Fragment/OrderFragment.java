package com.example.applestore.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.applestore.APIService.APIService;
import com.example.applestore.Adapter.OrderAdapter;
import com.example.applestore.R;
import com.example.applestore.Retrofit.RetrofitClient;
import com.example.applestore.SharedPreferences.SharedPrefManager;
import com.example.applestore.model.Order;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OrderFragment extends Fragment {
    private Context context;
    APIService apiService = RetrofitClient.getRetrofit().create(APIService.class);
    ArrayList<Order> listOrders;
    RecyclerView rcOrder;

    OrderAdapter orderAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getActivity();
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        // ánh xạ
        rcOrder = view.findViewById(R.id.rcOrder);
        rcOrder.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        int idUser = SharedPrefManager.getInstance(context).getUser().getMaKH();
        getAllListOrder();
        return view;
    }
    private void getAllListOrder() {
        Call<ArrayList<Order>> call = apiService.getAllListOrder();
        call.enqueue(new Callback<ArrayList<Order>>() {
            @Override
            public void onResponse(Call<ArrayList<Order>> call, Response<ArrayList<Order>> response) {
                if (response.isSuccessful()) {
                    listOrders = response.body();
                    orderAdapter = new OrderAdapter(getContext(), listOrders);
                    rcOrder.setHasFixedSize(true);
                    rcOrder.setAdapter(orderAdapter);
                    orderAdapter.notifyDataSetChanged();
                } else {
                    Log.i("TAG", "Không lấy được");
                }

            }

            @Override
            public void onFailure(Call<ArrayList<Order>> call, Throwable t) {
                Log.i("TAG", t.toString());
                System.out.println("Không kết nối được API");
            }
        });
    }

    private void getListOrder(int idUser){
        Call<ArrayList<Order>> call = apiService.getListOrder(idUser);
        call.enqueue(new Callback<ArrayList<Order>>() {
            @Override
            public void onResponse(Call<ArrayList<Order>> call, Response<ArrayList<Order>> response) {
                if(response.isSuccessful()){
                    listOrders = response.body();
                    orderAdapter = new OrderAdapter(getContext(),listOrders);
                    rcOrder.setHasFixedSize(true);
                    rcOrder.setAdapter(orderAdapter);
                    orderAdapter.notifyDataSetChanged();
                }
                else {
                    Log.i("TAG", "Erorr");
                }

            }

            @Override
            public void onFailure(Call<ArrayList<Order>> call, Throwable t) {
                Log.i("TAG", t.toString());
            }
    });
}
}
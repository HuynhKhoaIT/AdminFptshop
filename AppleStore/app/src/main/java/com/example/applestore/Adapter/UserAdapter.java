package com.example.applestore.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.applestore.APIService.APIService;
import com.example.applestore.Activity.DetailProductActivity;
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

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>{
    private Context context;
    private ArrayList<User> listUsers;
    private QuantityChangeListener listener;
    public void setQuantityChangeListener(QuantityChangeListener listener) {
        this.listener = listener;
    }
    APIService apiService = RetrofitClient.getRetrofit().create(APIService.class);

    public UserAdapter(Context applicationContext, ArrayList<User> listUsers) {
        this.context = applicationContext;
        this.listUsers = listUsers;
    }
    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user, parent, false);
        return new UserAdapter.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull UserAdapter.ViewHolder holder, int position) {
        User user = listUsers.get(position);
        holder.customer_name.setText(user.getTenKH());
        holder.customer_phone.setText(user.getPhone().toString());
        Glide.with(context).load("https://th.bing.com/th/id/R.bbbb54b8e1e92b768a356015194475a5?rik=2zuehhG3OX3Q6g&pid=ImgRaw&r=0").into(holder.user_image);
    }
    @Override
    public int getItemCount() {
        return listUsers.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView user_image;
        public TextView customer_name;
        public TextView customer_phone;

        public ViewHolder(View itemView) {
            super(itemView);
            user_image = itemView.findViewById(R.id.user_image);
            customer_name = itemView.findViewById(R.id.customer_name);
            customer_phone = itemView.findViewById(R.id.customer_phone);
        }
    }
}

package com.example.applestore.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.applestore.APIService.APIService;
import com.example.applestore.Activity.CheckoutActivity;
import com.example.applestore.Adapter.CartAdapter;
import com.example.applestore.Adapter.CategoryAdapter;
import com.example.applestore.Adapter.CheckoutAdapter;
import com.example.applestore.Interface.QuantityChangeListener;
import com.example.applestore.R;
import com.example.applestore.Retrofit.RetrofitClient;
import com.example.applestore.SharedPreferences.SharedPrefManager;
import com.example.applestore.Utils.CurrencyFormatter;
import com.example.applestore.model.Cart;
import com.example.applestore.model.CartDetail;
import com.example.applestore.model.Category;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartFragment extends Fragment implements QuantityChangeListener {
    private Context context;
    APIService apiService = RetrofitClient.getRetrofit().create(APIService.class);

    ArrayList<CartDetail> listCartDetail;

    TextView cart_subtotal_text;
    TextView cart_subtotal_value;
    Button cart_checkout_button;
    RecyclerView rcItemCart;
    CartAdapter cartAdapter;

    public CartFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        //Ánh xạ
        context = getActivity();
        cart_subtotal_text = view.findViewById(R.id.cart_subtotal_text);
        cart_subtotal_value = view.findViewById(R.id.cart_subtotal_value);
        cart_checkout_button = view.findViewById(R.id.cart_checkout_button);
        rcItemCart = view.findViewById(R.id.rcItemCart);
        rcItemCart.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        int idUser = SharedPrefManager.getInstance(context).getUser().getMaKH();
        System.out.println(idUser);

        getCartDetail(idUser);
        cart_checkout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CheckoutActivity.class);
                context.startActivity(intent);
            }
        });

        return view;
    }
    private void getCartDetail(int idUser){
        Call<Cart> call = apiService.getGioHangBymaKH(idUser);
        call.enqueue(new Callback<Cart>() {
            @Override
            public void onResponse(Call<Cart> call, Response<Cart> response) {
                if(response.isSuccessful()){
                    Cart cart = response.body();
                    listCartDetail = cart.getChiTietGioHangs();
                    cartAdapter = new CartAdapter(getContext(),listCartDetail);
                    //Thay đổi tiền

                    rcItemCart.setHasFixedSize(true);
                    rcItemCart.setAdapter(cartAdapter);
                    cart_subtotal_value.setText( CurrencyFormatter.formatCurrency(tongTienGioHang(listCartDetail)));
                    cartAdapter.notifyDataSetChanged();
                    cartAdapter.setQuantityChangeListener(CartFragment.this);
                }else{
                    Log.i("TAG","fail");
                }
            }
            @Override
            public void onFailure(Call<Cart> call, Throwable t) {
                Log.i("TAG", t.toString());
            }
        });
    }
    public int tongTienGioHang(ArrayList<CartDetail> listCartDetail) {
        int total = 0;

        for (CartDetail cartDetail : listCartDetail) {
            int quantity = cartDetail.getSoLuong();
            int price = cartDetail.getSanPham3().getGiaBanThuong();
            total += quantity * price;
        }
        return total;
    }
    @Override
    public void onQuantityChanged() {
        cart_subtotal_value.setText( CurrencyFormatter.formatCurrency(tongTienGioHang(cartAdapter.getListCartDetail())));
    }
}
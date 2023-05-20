package com.example.applestore.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.applestore.APIService.APIService;
import com.example.applestore.Adapter.CartAdapter;
import com.example.applestore.Adapter.CheckoutAdapter;
import com.example.applestore.Fragment.OrderFragment;
import com.example.applestore.R;
import com.example.applestore.Retrofit.RetrofitClient;
import com.example.applestore.SharedPreferences.SharedPrefManager;
import com.example.applestore.Utils.CurrencyFormatter;
import com.example.applestore.model.Cart;
import com.example.applestore.model.CartDetail;
import com.example.applestore.model.Order;
import com.example.applestore.model.OrderStatus;
import com.example.applestore.model.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.Date;
public class CheckoutActivity extends AppCompatActivity {

    RecyclerView rv_product_list;
    TextView tv_total_price;
    EditText et_customer_name;
    EditText et_customer_phone;
    EditText et_customer_address;
    Button btn_checkout;

    ArrayList<CartDetail> listCartDetail;

    CartAdapter cartAdapter;
    CheckoutAdapter checkoutAdapter;

    int totalPrice=0;
    private Context context;

    APIService apiService = RetrofitClient.getRetrofit().create(APIService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_out);

        context = this;
        // anh xa
        tv_total_price = findViewById(R.id.tv_total_price);
        et_customer_name = findViewById(R.id.et_customer_name);
        et_customer_phone = findViewById(R.id.et_customer_phone);
        et_customer_address = findViewById(R.id.et_customer_address);
        btn_checkout = findViewById(R.id.btn_checkout);
        rv_product_list =  findViewById(R.id.rv_product_list);
        rv_product_list.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        //hiện thông tin
        User user = SharedPrefManager.getInstance(this).getUser();
        et_customer_name.setText(user.getTenKH());
        et_customer_phone.setText(user.getPhone());
        et_customer_address.setText(user.getDiaChi());
        int idUser = SharedPrefManager.getInstance(this).getUser().getMaKH();
        getCartDetail(idUser);

        // tạo đơn hàng
        btn_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Order order = new Order();
                order.setKhachHang(user);
                order.setDiaChi(et_customer_address.getText().toString());
                /*order.setNgayDatHang(new Date());*/
                order.setTrangThai(new OrderStatus(1));
                order.setTongTien(totalPrice);
                createOrder(order);

            }
        });
    }
    private void createOrder(Order order){
        Call<Order> call = apiService.createOrder(order);
        call.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                if(response.isSuccessful()){
                    Toast.makeText(context, "Thêm đơn hàng thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CheckoutActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(context, "Thêm đơn hàng thất bại", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                Toast.makeText(context, "Thêm đơn hàng thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getCartDetail(int idUser){
        Call<Cart> call = apiService.getGioHangBymaKH(idUser);
        call.enqueue(new Callback<Cart>() {
            @Override
            public void onResponse(Call<Cart> call, Response<Cart> response) {
                if(response.isSuccessful()){
                    Cart cart = response.body();
                    listCartDetail = cart.getChiTietGioHangs();
                    System.out.println(listCartDetail.size());
                    checkoutAdapter = new CheckoutAdapter(context,listCartDetail);
                    rv_product_list.setHasFixedSize(true);
                    rv_product_list.setAdapter(checkoutAdapter);
                    totalPrice = tongTienGioHang(listCartDetail);
                    tv_total_price.setText( CurrencyFormatter.formatCurrency(totalPrice));
                    checkoutAdapter.notifyDataSetChanged();
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
}

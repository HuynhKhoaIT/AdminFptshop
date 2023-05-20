package com.example.applestore.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.applestore.APIService.APIService;
import com.example.applestore.Adapter.CategoryAdapter;
import com.example.applestore.Adapter.DetailOrderAdapter;
import com.example.applestore.Adapter.OrderAdapter;
import com.example.applestore.Adapter.ProductAdapter;
import com.example.applestore.R;
import com.example.applestore.Retrofit.RetrofitClient;
import com.example.applestore.Utils.CurrencyFormatter;
import com.example.applestore.model.Order;
import com.example.applestore.model.OrderDetail;
import com.example.applestore.model.Product;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailOrderActivity extends AppCompatActivity {

    public static final String KEY_ORDER_TO_PRODUCT = "KEY_ORDER_TO_PRODUCT";
    private Context context;
    DetailOrderAdapter detailOrderAdapter;

    APIService apiService = RetrofitClient.getRetrofit().create(APIService.class);

    RecyclerView recOrder ;

    TextView sum_price;

    TextView order_date;

    TextView order_address;

    TextView order_status;

    ArrayList<OrderDetail> listOrderDetail;

    Button btn_order_review;

    int idOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_order);
        context = this;
        //anh xa
        recOrder = findViewById(R.id.recOrder);
        sum_price = findViewById(R.id.sum_price);
        order_date = findViewById(R.id.order_date);
        order_address = findViewById(R.id.order_address);
        order_status = findViewById(R.id.order_status);
        recOrder.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        btn_order_review = findViewById(R.id.btn_order_review);
        // Lay thong tin
        getData();
        getOrder(idOrder);
        btn_order_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ReviewActivity.class);
                intent.putExtra(KEY_ORDER_TO_PRODUCT, idOrder);
                context.startActivity(intent);
            }
        });
    }
    private void getData() {
        Intent intent = getIntent();
        idOrder = getIntent().getIntExtra(OrderAdapter.KEY_ORDER_TO_PRODUCT, 0);
        System.out.println("Mã đơn hàng "+idOrder);
    }
    private void getOrder(int id) {
        Call <Order> call = apiService.getOrderbyID(id);
        call.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                if(response.isSuccessful()){
                    System.out.println(response.body());
                    Order order = response.body();
                    System.out.println(order.getListChiTietDonHang().size());
                    listOrderDetail = order.getListChiTietDonHang();
                    detailOrderAdapter = new DetailOrderAdapter(context,listOrderDetail);
                    recOrder.setHasFixedSize(true);
                    recOrder.setAdapter(detailOrderAdapter);
                    detailOrderAdapter.notifyDataSetChanged();
                    sum_price.setText(CurrencyFormatter.formatCurrency(order.getTongTien()));
                    order_date.setText(order.getNgayDatHang()+"");
                    order_address.setText(order.getDiaChi()+"");
                    order_status.setText(order.getTrangThai().getTenTrangThai());
                }
            }
            @Override
            public void onFailure(Call<Order> call, Throwable t) {

            }
        });
    }

}
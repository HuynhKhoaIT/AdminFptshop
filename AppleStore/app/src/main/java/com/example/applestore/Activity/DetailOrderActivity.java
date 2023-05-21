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
import android.widget.Toast;

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
import com.example.applestore.model.OrderStatus;
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

    Button btn_order_confirm,btn_order_cancel;
    Order order;

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
        btn_order_confirm = findViewById(R.id.btn_order_confirm);
        btn_order_cancel = findViewById(R.id.btn_order_cancel);
        // Lay thong tin
        getData();
        getOrder(idOrder);
        btn_order_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // lấy mã trạng thái hiện tại
                int currentIDStatus = order.getTrangThai().getMaTrangThai();
                // update order
                order.setTrangThai(new OrderStatus(currentIDStatus+1));
                // date bị lỗi nên sẽ set nó null
                order.setNgayDatHang(null);
                System.out.println(idOrder);
                System.out.println(order.getMaDH());
                updateOrder(idOrder,order);
                // dùng reload để sử lý tạm
                recreate();
            }
        });
        btn_order_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //goi api cancel
                order.setTrangThai(new OrderStatus(5));
                // date bị lỗi nên sẽ set nó null
                order.setNgayDatHang(null);
                System.out.println(idOrder);
                System.out.println(order.getMaDH());
                updateOrder(idOrder,order);
                // dùng reload để xử lý tạm
                recreate();
            }
        });

    }
    private void updateOrder(int idOrder,Order order){
        Call<Order> call = apiService.updateOrder(idOrder,order);
        call.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                if(response.isSuccessful()){
                    Toast.makeText(DetailOrderActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(DetailOrderActivity.this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                System.out.println("Lỗi kết nối API");
            }
        });
    }
    private void getData() {
        Intent intent = getIntent();
        idOrder = intent.getIntExtra(OrderAdapter.KEY_ORDER_TO_PRODUCT, 0);
        System.out.println("Mã đơn hàng "+idOrder);
    }
    private void getOrder(int id) {
        Call <Order> call = apiService.getOrderbyID(id);
        call.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                if(response.isSuccessful()){
                    System.out.println(response.body());
                    order = response.body();
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
                    // thay đổi nút
                    int maTrangThai = order.getTrangThai().getMaTrangThai();
                    if(maTrangThai == 1){
                        btn_order_confirm.setText("Xác nhận đơn hàng");
                    } else if (maTrangThai == 2) {
                        btn_order_confirm.setText("Tiến hành giao hàng");
                    } else if (maTrangThai == 3) {
                        btn_order_confirm.setText("Xác nhận giao thành công");
                    } else if (maTrangThai == 4) {
                        btn_order_confirm.setText("Đã giao thành công");
                        btn_order_confirm.setEnabled(false);
                        btn_order_cancel.setEnabled(false);
                    }
                    // đơn hàng bị hủy
                    else if (maTrangThai == 5) {
                        btn_order_confirm.setText("Đơn hàng đã hủy");
                        btn_order_confirm.setEnabled(false);
                        btn_order_cancel.setEnabled(false);
                    }
                }
            }
            @Override
            public void onFailure(Call<Order> call, Throwable t) {

            }
        });
    }

}
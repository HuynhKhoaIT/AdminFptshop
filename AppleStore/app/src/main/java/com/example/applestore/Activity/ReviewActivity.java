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
import android.widget.Toast;

import com.example.applestore.APIService.APIService;
import com.example.applestore.Adapter.DetailOrderAdapter;
import com.example.applestore.Adapter.OrderAdapter;
import com.example.applestore.Adapter.ReviewAdapter;
import com.example.applestore.R;
import com.example.applestore.Retrofit.RetrofitClient;
import com.example.applestore.SharedPreferences.SharedPrefManager;
import com.example.applestore.Utils.CurrencyFormatter;
import com.example.applestore.model.Order;
import com.example.applestore.model.OrderDetail;
import com.example.applestore.model.Product;
import com.example.applestore.model.Review;
import com.example.applestore.model.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewActivity extends AppCompatActivity {
    RecyclerView rcItemReview;
    Button btnRating;
    Context context = this;
    int idOrder;
    APIService apiService = RetrofitClient.getRetrofit().create(APIService.class);

    ArrayList<OrderDetail> listOrderDetail;

    ReviewAdapter reviewAdapter;
    ArrayList<Review> listReview;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        // anh xa
        rcItemReview = findViewById(R.id.rcItemReview);
        btnRating = findViewById(R.id.btnRating);
        getData();
        getOrder(idOrder);

        btnRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(Review r: listReview){
                    System.out.println(r.getVote() +"-"+r.getNoiDung());
                }
                createReview(listReview);
            }
        });

    }
    private void getData() {
        Intent intent = getIntent();
        idOrder = getIntent().getIntExtra(OrderAdapter.KEY_ORDER_TO_PRODUCT, 0);
        System.out.println("Mã đơn hàng " + idOrder);

        user = SharedPrefManager.getInstance(context).getUser();
    }

    private void createReview(ArrayList<Review> listReview){
        Call<ArrayList<Review>> call = apiService.createListReview(listReview);
        call.enqueue(new Callback<ArrayList<Review>>() {
            @Override
            public void onResponse(Call<ArrayList<Review>> call, Response<ArrayList<Review>> response) {
                if(response.isSuccessful())
                {
                    Toast.makeText(ReviewActivity.this, "Đánh giá thành công", Toast.LENGTH_SHORT).show();

                }
                else {
                    Toast.makeText(ReviewActivity.this, "Đánh giá không thành công", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Review>> call, Throwable t) {
                Log.i("TAG",t.toString());
                System.out.println("Lỗi kết nói API");
            }
        });
    }
    private void getOrder(int id) {
        Call<Order> call = apiService.getOrderbyID(id);
        call.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                if (response.isSuccessful()) {
                    System.out.println(response.body());
                    Order order = response.body();
                    System.out.println("Chi tiết đơn hàng: "+order.getListChiTietDonHang().size());
                    listOrderDetail = order.getListChiTietDonHang();
                    System.out.println(listOrderDetail.size());
                    listReview = new ArrayList<>();
                    // tạo ra listReview từ listOrder
                    for(OrderDetail o: listOrderDetail){
                        listReview.add(new Review(o.getSanPham2(),user,"",5));
                    }
                    rcItemReview.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                    reviewAdapter = new ReviewAdapter(context,listReview);
                    rcItemReview.setHasFixedSize(true);
                    rcItemReview.setAdapter(reviewAdapter);
                    reviewAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {

            }
        });
    }
}
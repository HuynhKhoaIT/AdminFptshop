package com.example.applestore.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.applestore.APIService.APIService;
import com.example.applestore.Adapter.ProductReviewAdapter;
import com.example.applestore.Fragment.CartFragment;
import com.example.applestore.R;
import com.example.applestore.Retrofit.RetrofitClient;
import com.example.applestore.SharedPreferences.SharedPrefManager;
import com.example.applestore.model.CartDetail;
import com.example.applestore.model.Category;
import com.example.applestore.model.Product;
import com.example.applestore.model.Review;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailProductActivity extends AppCompatActivity {
    TextView detailName,detailPrice, detailDes,amount;
    ImageSlider imageSlider;
    Button btnAddProduct,btn_minus,btn_plus;

    RecyclerView rcReview;
    APIService apiService = RetrofitClient.getRetrofit().create(APIService.class);
    int amountP,idSP;
    ArrayList<Review> reviews;
    private Context context = this;
    ProductReviewAdapter productReviewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        // anh xa
        detailName = findViewById(R.id.name_product);
        detailPrice = findViewById(R.id.price_product);
        detailDes = findViewById(R.id.des_product);
        imageSlider = findViewById(R.id.imageSlideProduct);
        amount = findViewById(R.id.amount);
        btnAddProduct = findViewById(R.id.add_product);
        btn_plus = findViewById(R.id.btn_plus);
        btn_minus = findViewById(R.id.btn_minus);
        rcReview = findViewById(R.id.recReview);
        rcReview.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            // lấy thông tin truyền bằng bundel
            detailName.setText(bundle.getString("Title"));
            detailPrice.setText(bundle.getString("Price"));
            detailDes.setText(bundle.getString("Desc"));
            amountP = bundle.getInt("soLuong");
            idSP = bundle.getInt("maSP");

            // Slide
            ArrayList<String> arrayListImage = bundle.getStringArrayList("slideImage");
            // add image
            arrayListImage.add(0,bundle.getString("Image"));
            ArrayList<SlideModel> slideModels = new ArrayList<>();
            for(String a:arrayListImage){
                slideModels.add(new SlideModel(a, ScaleTypes.FIT));
            }
            imageSlider.startSliding(3000);
            imageSlider.setImageList(slideModels, ScaleTypes.FIT);

        }
        getReviews(idSP);
        //Event
        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // lấy số lượng
                int amountProduct;
                try {
                    String amountString = amount.getText().toString();
                    amountProduct = Integer.parseInt(amountString);
                } catch (NumberFormatException e) {
                    amountProduct = 0;
                }
                // lấy id sản phấm
                int idSP = bundle.getInt("maSP");
                // lấy id khác hàng
                int idKH = SharedPrefManager.getInstance(context).getUser().getMaKH();

                addProductToCart(amountProduct,idSP,idKH);
            }
        });
        btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int amountProduct;
                try {
                    String amountString = amount.getText().toString();
                    amountProduct = Integer.parseInt(amountString);
                } catch (NumberFormatException e) {
                    amountProduct = 0;
                }
                if(amountP > amountProduct){
                    amount.setText((amountProduct+1)+"");
                }
                else {
                    Toast.makeText(context,"Vượt quá số lượng giới hạn",Toast.LENGTH_SHORT).show();
                }

            }
        });
        btn_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int amountProduct;
                try {
                    String amountString = amount.getText().toString();
                    amountProduct = Integer.parseInt(amountString);
                } catch (NumberFormatException e) {
                    amountProduct = 0;
                }
                //check valid
                if(amountProduct>1){
                    amount.setText((amountProduct-1)+"");
                }

            }
        });
    }
    private void getReviews(int idSP){
        Call<ArrayList<Review>> call = apiService.getListReview(idSP);
        call.enqueue(new Callback<ArrayList<Review>>() {
            @Override
            public void onResponse(Call<ArrayList<Review>> call, Response<ArrayList<Review>> response) {
                if(response.isSuccessful()){
                    System.out.println("Lay review thanh cong");
                    reviews = response.body();
                    productReviewAdapter = new ProductReviewAdapter(context,reviews);
                    rcReview.setHasFixedSize(true);
                    rcReview.setAdapter(productReviewAdapter);
                    productReviewAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Review>> call, Throwable t) {

            }
        });
    }
    private void addProductToCart(int amountProduct,int idSP,int idKH){
        //tạo ra 1 CartDetail
        CartDetail cartDetail = new CartDetail(idSP,amountProduct);
        Call<CartDetail> call = apiService.addProductToCart(cartDetail,idKH);
        call.enqueue(new Callback<CartDetail>() {
            @Override
            public void onResponse(Call<CartDetail> call, Response<CartDetail> response) {
                if(response.isSuccessful())
                {
                    System.out.println(response.body());
                    Toast.makeText(context,"Thêm thành công",Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(context,"Thêm sản phẩm thất bại",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<CartDetail> call, Throwable t) {
                Toast.makeText(context,"error",Toast.LENGTH_LONG).show();
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
}
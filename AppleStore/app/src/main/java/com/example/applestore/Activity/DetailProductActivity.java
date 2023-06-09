package com.example.applestore.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.view.MenuItem;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.applestore.APIService.APIService;
import com.example.applestore.R;
import com.example.applestore.Retrofit.RetrofitClient;
import com.example.applestore.model.Product;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailProductActivity extends AppCompatActivity {
    TextView detailName, detailPrice, detailDes, amount;
    ImageSlider imageSlider;
    APIService apiService = RetrofitClient.getRetrofit().create(APIService.class);
    int amountP, idSP;
    private Context context = this;
    Button btn_repair,btn_delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        // anh xa
        detailName = findViewById(R.id.name_product);
        detailPrice = findViewById(R.id.price_product);
        detailDes = findViewById(R.id.des_product);
        imageSlider = findViewById(R.id.imageSlideProduct);
        btn_repair = findViewById(R.id.btn_repair);
        btn_delete = findViewById(R.id.btn_delete);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Detail Product");
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            // lấy thông tin truyền bằng bundel
            detailName.setText(bundle.getString("Title"));
            detailPrice.setText(bundle.getString("Price"));
            detailDes.setText(bundle.getString("Desc"));
            System.out.println(((bundle.getInt("isDeleted")==0)?"Xóa":"Khôi phục"));
            btn_delete.setText(((bundle.getInt("isDeleted")==0)?"Xóa":"Khôi phục"));
            amountP = bundle.getInt("soLuong");
            idSP = bundle.getInt("maSP");

            // Slide
            ArrayList<String> arrayListImage = bundle.getStringArrayList("slideImage");
            // add image
            arrayListImage.add(0, bundle.getString("Image"));
            ArrayList<SlideModel> slideModels = new ArrayList<>();
            for (String a : arrayListImage) {
                slideModels.add(new SlideModel(a, ScaleTypes.FIT));
            }
            imageSlider.startSliding(3000);
            imageSlider.setImageList(slideModels, ScaleTypes.FIT);

        }
        //Delete
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<Product> call = apiService.deleteProduct(idSP);
                call.enqueue(new Callback<Product>() {
                    @Override
                    public void onResponse(Call<Product> call, Response<Product> response) {
                        if(response.isSuccessful()){
                            Product product = response.body();
                            if(product.getIsDeteted() ==0){
                                btn_delete.setText("Xóa");

                            }
                            else {
                                btn_delete.setText("Khôi phục");
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Product> call, Throwable t) {

                    }
                });
            }
        });
        //Repair
        btn_repair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateProductActivity.class);
                intent.putExtra("maSP", idSP);
                startActivity(intent);
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
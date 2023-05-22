package com.example.applestore.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.applestore.APIService.APIService;
import com.example.applestore.Fragment.BlogFragment;
import com.example.applestore.R;
import com.example.applestore.Retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailBlogActivity extends AppCompatActivity {
    TextView detailDesc, detailTitle;
    ImageView detailImage;
    Button btn_delete,btn_repair;
    APIService apiService = RetrofitClient.getRetrofit().create(APIService.class);
    int idBlog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blog_detail);

        detailImage = findViewById(R.id.detailImage);
        detailDesc = findViewById(R.id.detailDesc);
        detailTitle = findViewById(R.id.detailTitle);
        btn_delete = findViewById(R.id.btn_delete);
        btn_repair = findViewById(R.id.btn_repair);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            //detailImage.setImageResource(bundle.getInt("Image"));
            Glide.with(this).load(bundle.getString("Image")).into(detailImage);
            detailTitle.setText(bundle.getString("Title"));
            detailDesc.setText(bundle.getString("Desc"));
            idBlog = bundle.getInt("idBlog");
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Detail Blog");
        btn_repair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UpdateBlogActivity.class);
                intent.putExtra("maBlog", idBlog);
                startActivity(intent);
            }
        });
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<String> call = apiService.deteleProduct(idBlog);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if(response.isSuccessful())
                        {
                            Toast.makeText(getApplicationContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(DetailBlogActivity.this, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });
            }
        });

    }
    //    Bắt sự kiện khi bấm vào nút mũi tên quay lại
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}

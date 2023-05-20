package com.example.applestore.Activity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.applestore.APIService.APIService;
import com.example.applestore.Adapter.CategoryAdapter;
import com.example.applestore.Adapter.ProductAdapter;
import com.example.applestore.R;
import com.example.applestore.Retrofit.RetrofitClient;
import com.example.applestore.model.Product;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductActivity extends AppCompatActivity {

    Context context = this;

    private SearchView searchView;
    ArrayList<Product> productList;

    APIService apiService = RetrofitClient.getRetrofit().create(APIService.class);
    public static final String KEY_PRODUCT_ID_TO_PRODUCT_DETAIL = "KEY_PRODUCT_ID_TO_PRODUCT_DETAIL";
    //view
    int id;
    RecyclerView recProduct;
    ProductAdapter productAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        getData();
        recProduct = findViewById(R.id.product_list);
        recProduct.setLayoutManager(new GridLayoutManager(context, 2));
        getProducts();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    private void getData() {
        Intent intent = getIntent();
        id = getIntent().getIntExtra(CategoryAdapter.KEY_CATEGORYID_TO_PRODUCT, 0);
    }
    private void getProducts() {
        Call<ArrayList<Product>> call = apiService.getSanPhamByDanhMuc(id);
        call.enqueue(new Callback<ArrayList<Product>>() {
            @Override
            public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {

                if (response.isSuccessful()) {
                    productList = response.body();
                    productAdapter = new ProductAdapter(context, productList);
                    recProduct.setHasFixedSize(true);
                    recProduct.setAdapter(productAdapter);
                    productAdapter.notifyDataSetChanged();
                } else {
                    Log.i("TAG", "fail");
                    System.out.println("Zoo - Errors");
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Product>> call, Throwable t) {
                Log.i("fail ", t.getMessage());
            }
        });
    }


//    bắt sự kiện trên toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Search...");
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.search:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}


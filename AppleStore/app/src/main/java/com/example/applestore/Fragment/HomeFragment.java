package com.example.applestore.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.applestore.APIService.APIService;
import com.example.applestore.Activity.AddCategoryActivity;
import com.example.applestore.Activity.AddProductActivity;
import com.example.applestore.Activity.LoginActivity;
import com.example.applestore.Activity.MainActivity;
import com.example.applestore.Adapter.CategoryAdapter;
import com.example.applestore.Adapter.ProductAdapter;
import com.example.applestore.R;
import com.example.applestore.Retrofit.RetrofitClient;
import com.example.applestore.model.Category;
import com.example.applestore.model.Product;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private RecyclerView mRecyclerView,categoryRec;
    APIService apiService = RetrofitClient.getRetrofit().create(APIService.class);
    ProductAdapter productAdapter;
    ArrayList<Product> productList;
    ArrayList<Category> categories;
    CategoryAdapter categoryAdapter;

    Button addcategory,addproduct;
    private Context context;
    public HomeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        //Ánh xạ

        categoryRec = view.findViewById(R.id.categoryList);
        mRecyclerView = view.findViewById(R.id.product_list);
        context = getActivity();
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        categoryRec.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        addcategory = view.findViewById(R.id.btn_add_category);
        addproduct = view.findViewById(R.id.btn_add_product);
        // set Data
        getCategory();
        getProduct();

        //List Slide
        ArrayList<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.bn1, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.bnfstudio, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.bn2, ScaleTypes.FIT));
        addcategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddCategoryActivity.class);
                startActivity(intent);
            }
        });
        addproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddProductActivity.class);
                startActivity(intent);
            }
        });

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        actionBar.setTitle("Home");
        return view;
    }
    private void getCategory(){
        Call<ArrayList<Category>> call = apiService.getAllCategory();
        call.enqueue(new Callback<ArrayList<Category>>() {
            @Override
            public void onResponse(Call<ArrayList<Category>> call, Response<ArrayList<Category>> response) {

                if(response.isSuccessful()){
                    categories = response.body();
                    categoryAdapter = new CategoryAdapter(getContext(),categories);
                    categoryRec.setHasFixedSize(true);
                    categoryRec.setAdapter(categoryAdapter);
                    categoryAdapter.notifyDataSetChanged();
                }else{
                    Log.i("TAG","fail");
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Category>> call, Throwable t) {
                Log.i("TAG", t.toString());
            }
        });
    }

    private void getProduct(){
        Call<ArrayList<Product>> call = apiService.getAllProduct();
        call.enqueue(new Callback<ArrayList<Product>>() {
            @Override
            public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {

                if(response.isSuccessful()){
                    productList = response.body();
                    productAdapter = new ProductAdapter(getContext(),productList);
                    mRecyclerView.setHasFixedSize(true);
                    mRecyclerView.setAdapter(productAdapter);
                    productAdapter.notifyDataSetChanged();
                }else{
                    System.out.println("Lấy sản phẩm không thành công");
                    Log.i("TAG","fail");
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Product>> call, Throwable t) {
                Log.i("TAG", t.toString());
                System.out.println("Lỗi kết nối đến API");
            }
        });
    }



}

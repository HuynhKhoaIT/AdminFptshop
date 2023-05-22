package com.example.applestore.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.applestore.APIService.APIService;
import com.example.applestore.Activity.AddBlogActivity;
import com.example.applestore.Retrofit.RetrofitClient;
import com.example.applestore.model.Blog;
import com.example.applestore.Adapter.BlogAdapter;
import com.example.applestore.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BlogFragment extends Fragment {
    private Context context;
    private RecyclerView mRecyclerView;
    ArrayList<Blog> blogs;
    APIService apiService = RetrofitClient.getRetrofit().create(APIService.class);
    BlogAdapter adapter;
    Button btn_add_blog;
    public BlogFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blog, container, false);
        mRecyclerView = view.findViewById(R.id.blog_list);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        btn_add_blog=view.findViewById(R.id.btn_add_blog);
        context = getContext();
        getAllBlog();

        btn_add_blog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddBlogActivity.class);
                startActivity(intent);
            }
        });

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        actionBar.setTitle("Blog");

        return view;
    }
    private  void getAllBlog()
    {
        Call<ArrayList<Blog>> call= apiService.getAllBlog();
        call.enqueue(new Callback<ArrayList<Blog>>(){
            @Override
            public void onResponse(Call<ArrayList<Blog>> call, Response<ArrayList<Blog>> response) {
                if(response.isSuccessful())
                {
                    blogs = response.body();
                    System.out.println(blogs.size());
                    adapter =new BlogAdapter(getContext(),blogs);
                    mRecyclerView.setHasFixedSize(true);
                    mRecyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Blog>> call, Throwable t) {

            }
        });
    }
}

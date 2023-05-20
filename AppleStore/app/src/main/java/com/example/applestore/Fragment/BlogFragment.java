package com.example.applestore.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.applestore.APIService.APIService;
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
    public BlogFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blog, container, false);
        mRecyclerView = view.findViewById(R.id.blog_list);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));

        getAllBlog();
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
                    blogs =response.body();
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

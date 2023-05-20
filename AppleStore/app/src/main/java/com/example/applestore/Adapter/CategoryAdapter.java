package com.example.applestore.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.applestore.Activity.ProductActivity;
import com.example.applestore.R;
import com.example.applestore.model.Category;
import com.example.applestore.model.Product;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewholder> {
    private static Context context;
    ArrayList<Category> listCategory;
    public static final String KEY_CATEGORYID_TO_PRODUCT = "KEY_CATEGORYID_TO_PRODUCT";
    public CategoryAdapter(Context applicationContext, ArrayList<Category> array) {
        this.context = applicationContext;
        this.listCategory = array;
    }
    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category, parent, false);
        return new MyViewholder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.MyViewholder holder, int position) {
        Category category = listCategory.get(position);
        Glide.with(context).load(category.getAnhdm()).into(holder.category_image);
        holder.category_name.setText(category.getTenDM());

        holder.category_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductActivity.class);
                intent.putExtra(KEY_CATEGORYID_TO_PRODUCT, listCategory.get(holder.getAdapterPosition()).getMaDM());
                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return listCategory.size();
    }
    public static class MyViewholder extends RecyclerView.ViewHolder {
        public ImageView category_image;
        public TextView category_name;
        public CardView category_card;
        public MyViewholder(@NonNull View itemView) {
            super(itemView);
            category_image = (ImageView) itemView.findViewById(R.id.category_image);
            category_name = (TextView) itemView.findViewById(R.id.category_name);
            category_card = (CardView) itemView.findViewById(R.id.category_card);
        }
    }

}

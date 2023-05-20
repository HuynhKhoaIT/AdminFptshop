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
import com.example.applestore.Activity.DetailBlogActivity;
import com.example.applestore.Activity.DetailProductActivity;
import com.example.applestore.R;
import com.example.applestore.Utils.CurrencyFormatter;
import com.example.applestore.model.Category;
import com.example.applestore.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Product> mProductList;

    public ProductAdapter(Context applicationContext, ArrayList<Product> productList) {
        this.context = applicationContext;
        mProductList = productList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = mProductList.get(position);
        holder.productName.setText(product.getTenSP());
        holder.productPrice.setText(CurrencyFormatter.formatCurrency(product.getGiaBanThuong()));
        Glide.with(context).load(product.getAnh()).into(holder.productImage);
        holder.productCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Chuyển thông tin sang file product
                Intent intent = new Intent(context, DetailProductActivity.class);
                intent.putExtra("Image", mProductList.get(holder.getAdapterPosition()).getAnh());
                intent.putExtra("Title", mProductList.get(holder.getAdapterPosition()).getTenSP());
                intent.putExtra("Price", CurrencyFormatter.formatCurrency(mProductList.get(holder.getAdapterPosition()).getGiaBanThuong()));
                intent.putExtra("Desc", mProductList.get(holder.getAdapterPosition()).getMoTa());
                intent.putExtra("maSP",mProductList.get(holder.getAdapterPosition()).getMaSP());
                intent.putExtra("soLuong",mProductList.get(holder.getAdapterPosition()).getSoLuong());
                intent.putStringArrayListExtra("slideImage",mProductList.get(holder.getAdapterPosition()).getURLImageSlide());
                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return mProductList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView productImage;
        public TextView productName;
        public TextView productPrice;
        public CardView productCard;

        public ViewHolder(View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_image);
            productName = itemView.findViewById(R.id.product_name);
            productPrice = itemView.findViewById(R.id.product_price);
            productCard = itemView.findViewById(R.id.product_card);
        }
    }
}

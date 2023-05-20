package com.example.applestore.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.applestore.Activity.DetailProductActivity;
import com.example.applestore.R;
import com.example.applestore.Utils.CurrencyFormatter;
import com.example.applestore.model.CartDetail;
import com.example.applestore.model.OrderDetail;

import java.util.ArrayList;

public class DetailOrderAdapter  extends RecyclerView.Adapter<CheckoutAdapter.ViewHolder>{
    private Context context;
    private ArrayList<OrderDetail> listCartDetail;

    public DetailOrderAdapter(Context context, ArrayList<OrderDetail> listCartDetail) {
        this.context = context;
        this.listCartDetail = listCartDetail;
    }
    @NonNull
    @Override
    public CheckoutAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rec_view_item_order, parent, false);
        return new CheckoutAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckoutAdapter.ViewHolder holder, int position) {
        OrderDetail cartDetail = listCartDetail.get(position);
        holder.productName.setText(cartDetail.getSanPham2().getTenSP());
        holder.productPrice.setText(CurrencyFormatter.formatCurrency(cartDetail.getSanPham2().getGiaBanThuong()));
        Glide.with(context).load(cartDetail.getSanPham2().getAnh()).into(holder.productImage);
        holder.productAmount.setText(cartDetail.getSoLuong() + "");
        holder.productImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailProductActivity.class);
                intent.putExtra("Image", listCartDetail.get(holder.getAdapterPosition()).getSanPham2().getAnh());
                intent.putExtra("Title", listCartDetail.get(holder.getAdapterPosition()).getSanPham2().getTenSP());
                intent.putExtra("Price", CurrencyFormatter.formatCurrency(listCartDetail.get(holder.getAdapterPosition()).getSanPham2().getGiaBanThuong()));
                intent.putExtra("Desc", listCartDetail.get(holder.getAdapterPosition()).getSanPham2().getMoTa());
                intent.putExtra("maSP",listCartDetail.get(holder.getAdapterPosition()).getSanPham2().getMaSP());
                intent.putExtra("soLuong",listCartDetail.get(holder.getAdapterPosition()).getSanPham2().getSoLuong());
                intent.putStringArrayListExtra("slideImage",listCartDetail.get(holder.getAdapterPosition()).getSanPham2().getURLImageSlide());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listCartDetail.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView productImage;
        public TextView productName;
        public TextView productPrice;
        public TextView productAmount;


        public ViewHolder(View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_image);
            productName = itemView.findViewById(R.id.product_name);
            productPrice = itemView.findViewById(R.id.product_price);
            productAmount = itemView.findViewById(R.id.product_amount);
        }
    }
}

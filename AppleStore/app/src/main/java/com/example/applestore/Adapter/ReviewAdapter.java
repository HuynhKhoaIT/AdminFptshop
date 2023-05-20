package com.example.applestore.Adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.applestore.R;
import com.example.applestore.Utils.CurrencyFormatter;
import com.example.applestore.model.OrderDetail;
import com.example.applestore.model.Review;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {
    Context context;
    ArrayList<Review> listReview;

    public ReviewAdapter(Context context, ArrayList<Review> listReview) {
        this.context = context;
        this.listReview = listReview;
    }

    public ArrayList<Review> getListReview() {
        return listReview;
    }

    @NonNull
    @Override
    public ReviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_review_order, parent, false);
        return new ReviewAdapter.ViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull ReviewAdapter.ViewHolder holder, int position) {
        // set date
        Glide.with(context).load(listReview.get(position).getSanPham().getAnh()).into(holder.productImage);
        holder.productName.setText(listReview.get(position).getSanPham().getTenSP());
        holder.productPrice.setText(CurrencyFormatter.formatCurrency(listReview.get(position).getSanPham().getGiaBanThuong()));
        // Lưu vị trí hiện tại của ViewHolder
        holder.setCurrentPosition(position);
        // Loại bỏ TextWatcher cũ (nếu tồn tại)
        if (holder.reviewEditText.getTag() instanceof TextWatcher) {
            holder.reviewEditText.removeTextChangedListener((TextWatcher) holder.reviewEditText.getTag());
        }
        // Gắn TextWatcher mới
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void afterTextChanged(Editable editable) {
                String reviewMessage = holder.reviewEditText.getText().toString();
                listReview.get(holder.currentPosition).setNoiDung(reviewMessage);
            }
            // Implement các phương thức của TextWatcher
        };
        holder.reviewEditText.addTextChangedListener(textWatcher);
        holder.reviewEditText.setTag(textWatcher);
        holder.productRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                int numStars = (int) holder.productRatingBar.getRating();
                listReview.get(holder.currentPosition).setVote(numStars);
//                notifyDataSetChanged();
                System.out.println("Sao trong listReview" +listReview.get(holder.currentPosition).getVote());
            }
        });
    }
    @Override
    public int getItemCount() {
        return listReview.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName,productPrice;
        RatingBar productRatingBar;
        EditText reviewEditText;

        private int currentPosition;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
            productRatingBar = itemView.findViewById(R.id.productRatingBar);
            reviewEditText = itemView.findViewById(R.id.reviewEditText);
        }
        public void setCurrentPosition(int position) {
            currentPosition = position;
        }
    }
}

package com.example.applestore.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.applestore.R;
import com.example.applestore.model.Review;

import java.util.ArrayList;

public class ProductReviewAdapter extends RecyclerView.Adapter<ProductReviewAdapter.ReviewViewHolder>{
    private Context context;
    private ArrayList<Review> reviewList;

    public ProductReviewAdapter(Context applicationContext, ArrayList<Review> reviewList) {
        this.context = applicationContext;
        this.reviewList = reviewList;
    }

    @NonNull
    @Override
    public ProductReviewAdapter.ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_review_product, parent, false);
        return new ProductReviewAdapter.ReviewViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductReviewAdapter.ReviewViewHolder holder, int position) {
        Review review = reviewList.get(position);
        holder.user_name.setText(review.getKhachHang().getTenKH());
        holder.productVote.setNumStars(review.getVote());
        holder.tvReview.setText(review.getNoiDung());
    }

    @Override
    public int getItemCount() {
        return reviewList.size();

    }
    public class ReviewViewHolder extends RecyclerView.ViewHolder {

        RatingBar productVote;
        TextView tvReview,user_name;
        public ReviewViewHolder(View itemView) {
            super(itemView);
            productVote = itemView.findViewById(R.id.productVote);
            tvReview = itemView.findViewById(R.id.tvReview);
            user_name = itemView.findViewById(R.id.user_name);
        }
    }
}

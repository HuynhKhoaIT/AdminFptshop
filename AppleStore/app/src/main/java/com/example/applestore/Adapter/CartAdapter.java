package com.example.applestore.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.applestore.APIService.APIService;
import com.example.applestore.Activity.DetailProductActivity;
import com.example.applestore.Interface.QuantityChangeListener;
import com.example.applestore.R;
import com.example.applestore.Retrofit.RetrofitClient;
import com.example.applestore.SharedPreferences.SharedPrefManager;
import com.example.applestore.Utils.CurrencyFormatter;
import com.example.applestore.model.CartDetail;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder>{
    private Context context;
    private ArrayList<CartDetail> listCartDetail;
    private QuantityChangeListener listener;
    public void setQuantityChangeListener(QuantityChangeListener listener) {
        this.listener = listener;
    }
    APIService apiService = RetrofitClient.getRetrofit().create(APIService.class);

    public CartAdapter(Context applicationContext, ArrayList<CartDetail> listCartDetail) {
        this.context = applicationContext;
        this.listCartDetail = listCartDetail;
    }
    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cart, parent, false);
        return new CartAdapter.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
        CartDetail cartDetail = listCartDetail.get(position);
        holder.productName.setText(cartDetail.getSanPham3().getTenSP());
        holder.productPrice.setText(CurrencyFormatter.formatCurrency(cartDetail.getSanPham3().getGiaBanThuong()));
        Glide.with(context).load(cartDetail.getSanPham3().getAnh()).into(holder.productImage);
        holder.productAmount.setText(cartDetail.getSoLuong()+"");
        holder.productImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailProductActivity.class);
                intent.putExtra("Image", listCartDetail.get(holder.getAdapterPosition()).getSanPham3().getAnh());
                intent.putExtra("Title", listCartDetail.get(holder.getAdapterPosition()).getSanPham3().getTenSP());
                intent.putExtra("Price", CurrencyFormatter.formatCurrency(listCartDetail.get(holder.getAdapterPosition()).getSanPham3().getGiaBanThuong()));
                intent.putExtra("Desc", listCartDetail.get(holder.getAdapterPosition()).getSanPham3().getMoTa());
                intent.putExtra("maSP",listCartDetail.get(holder.getAdapterPosition()).getSanPham3().getMaSP());
                context.startActivity(intent);
            }
        });
        holder.remove_product_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int idSP = cartDetail.getSanPham3().getMaSP();
                int idKH = SharedPrefManager.getInstance(context).getUser().getMaKH();
                System.out.println(idSP+"/"+idKH);
                removeItemInCart(idSP,idKH,holder.getAdapterPosition());
            }
        });
        // giảm sản phẩm
        holder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // kiểm tra số lượng sản phẩm hiện tại
                if(cartDetail.getSanPham3().getSoLuong()>1){
                    System.out.println(cartDetail.getSoLuong()-1);
                    holder.productAmount.setText((cartDetail.getSoLuong()-1)+"");
                    //cập nhật cartDetail
                    updateCartDetail(holder.getAdapterPosition(),-1);
                }
                else{
                    int idSP = cartDetail.getSanPham3().getMaSP();
                    int idKH = SharedPrefManager.getInstance(context).getUser().getMaKH();
                    removeItemInCart(idSP,idKH,holder.getAdapterPosition());
                }
            }
        });
        // tăng số lượng
        holder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // kiểm tra số lượng sản phẩm hiện tại
                if(cartDetail.getSanPham3().getSoLuong()>cartDetail.getSoLuong()){
                    holder.productAmount.setText((cartDetail.getSoLuong()+1)+"");

                    //cập nhật cartDetail
                    updateCartDetail(holder.getAdapterPosition(),1);
                    notifyDataSetChanged();
                }
                else {
                    Toast.makeText(context,"Đã đặt số lượng tối đa",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    //cập nhật số lượng
    public void updateCartDetail(int position,int soluongtang){
        //tạo cartDetail
        CartDetail cartDetail = new CartDetail(listCartDetail.get(position).getSanPham3().getMaSP(),listCartDetail.get(position).getSoLuong()+soluongtang);
        // mã khách hàng
        int maKH = SharedPrefManager.getInstance(context).getUser().getMaKH();

        Call<CartDetail> call = apiService.updateAmountCartItem(cartDetail,maKH);
        call.enqueue(new Callback<CartDetail>() {
            @Override
            public void onResponse(Call<CartDetail> call, Response<CartDetail> response) {
                if(response.isSuccessful()){
                    listCartDetail.get(position).setSoLuong(listCartDetail.get(position).getSoLuong()+soluongtang);
                    notifyItemChanged(position);
                    if (listener != null) {
                        listener.onQuantityChanged();
                    }
                }
            }
            @Override
            public void onFailure(Call<CartDetail> call, Throwable t) {
            }
        });
    }

    // xóa sản phẩm ra khỏi giỏ hàng
    public void removeItemInCart(int idSP, int idKH,int position){
        System.out.println("Zoo hàm xóa");
        Call<CartDetail> call = apiService.deleteCartItem(idKH,idSP);
        call.enqueue(new Callback<CartDetail>() {
            @Override
            public void onResponse(Call<CartDetail> call, Response<CartDetail> response) {
                if(response.isSuccessful())
                {
                    System.out.println("Xóa thành công");
                    Toast.makeText(context,"Xóa thành công",Toast.LENGTH_LONG).show();
                    System.out.println("Trước khi xóa"+listCartDetail.size());
                    listCartDetail.remove(position);
                    System.out.println("Sau khi xóa"+listCartDetail.size());
                    if (listener != null) {
                        listener.onQuantityChanged();
                    }
                    notifyDataSetChanged();
                }
                else {
                    Toast.makeText(context,"Xóa thất bại",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<CartDetail> call, Throwable t) {

            }
        });

    }
    @Override
    public int getItemCount() {
        return listCartDetail.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageButton remove_product_button;
        public ImageView productImage;
        public TextView productName;
        public TextView productPrice;
        public TextView btnMinus;
        public TextView productAmount;
        public TextView btnPlus;
//        public CardView item_Cart;

        public ViewHolder(View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_image);
            productName = itemView.findViewById(R.id.product_name);
            productPrice = itemView.findViewById(R.id.product_price);
            btnMinus = itemView.findViewById(R.id.quantity_minus_btn);
            productAmount = itemView.findViewById(R.id.quantity_text_view);
            btnPlus = itemView.findViewById(R.id.quantity_plus_button);
            remove_product_button = itemView.findViewById(R.id.remove_product_button);
        }
    }

    public ArrayList<CartDetail> getListCartDetail() {
        return listCartDetail;
    }
}

package com.example.applestore.APIService;

import com.example.applestore.model.Cart;
import com.example.applestore.model.Blog;
import com.example.applestore.model.CartDetail;
import com.example.applestore.model.Category;
import com.example.applestore.model.Order;
import com.example.applestore.model.Product;
import com.example.applestore.model.Review;
import com.example.applestore.model.User;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface APIService {
    //ServiceAPI
    APIService serviceapi = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://10.0.2.2:8080/myapp/")
            .build()
            .create(APIService.class);
    // San Pham
    @GET("sanpham")
    Call<ArrayList<Product>> getAllProduct();
    @GET("sanpham-by-dm")
    Call<ArrayList<Product>> getSanPhamByDanhMuc(@Query("maDM") int maDM);
    @GET("top8")
    Call<ArrayList<Product>> getTop8Product();

    // Danh Muc
    @GET("danhmuc")
    Call<ArrayList<Category>> getAllCategory();
    @POST("danhmuc")
    Call<Category> createCategory(@Body Category category) ;

    // User
    @POST("khachhang")
    Call<User> createUser(@Body User user);
    @FormUrlEncoded
    @POST("khachhang-login")
    Call<User> loginUser(@Field("email") String username, @Field("password") String password);
    // Cart
    @POST("giohang")
    Call<Cart> createCart(@Body Cart cart);
    @GET("giohangbymakh")
    Call<Cart> getGioHangBymaKH(@Query("id") int maKH);
    //Blog
    @GET("baiviet")
    Call<ArrayList<Blog>> getAllBlog();

    //DetailCart
    @POST("ctgh")
    Call<CartDetail> addProductToCart(@Body CartDetail cartDetail, @Query("id") int id);

    @DELETE("delete-item-cart")
    Call<CartDetail> deleteCartItem(@Query("idKH") int idKH,@Query("idSP") int idSP);

    @PUT("updatesl")
    Call<CartDetail> updateAmountCartItem(@Body CartDetail cartDetail, @Query("id") int id);

    //Order
    @POST("donhang")
    Call<Order> createOrder(@Body Order order);

    @GET("donhangbymakh")
    Call<ArrayList<Order>> getListOrder(@Query("id") int id);

    @GET("checkemail")
    Call <User> getUserByEmail(@Query("email") String email);

    @PUT("khachhang")
    Call <User> updateUser(@Query("id") int id, @Body User user);

    @GET("donhangbymadh")
    Call<Order> getOrderbyID(@Query("id") int id);

    @POST("listdanhgia")
    Call<ArrayList<Review>> createListReview(@Body ArrayList<Review> review);

    @GET("danhgiabymasp")
    Call<ArrayList<Review>> getListReview(@Query("masp") int masp);
}

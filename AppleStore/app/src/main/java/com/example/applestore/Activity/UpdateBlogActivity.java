package com.example.applestore.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.applestore.APIService.APIService;
import com.example.applestore.R;
import com.example.applestore.Retrofit.RetrofitClient;
import com.example.applestore.model.Blog;
import com.example.applestore.model.Category;
import com.example.applestore.model.Product;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateBlogActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    APIService apiService = RetrofitClient.getRetrofit().create(APIService.class);
    private Button btnChooseImage;
    private Button btnUpload;
    private ImageView imageView;
    private EditText etBlogName,etBlogContent;
    private Uri selectedImageUri;
    int idBlog;
    Blog blog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_blog);

        btnChooseImage = findViewById(R.id.addBlogImage);
        btnUpload = findViewById(R.id.btnAddBlog);
        imageView = findViewById(R.id.imBlogImage);
        etBlogName = findViewById(R.id.etBlogName);
        etBlogContent = findViewById(R.id.etBlogContent);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            idBlog = bundle.getInt("maBlog");
            Call<Blog> call = apiService.getBlogByid(idBlog);
            call.enqueue(new Callback<Blog>() {
                @Override
                public void onResponse(Call<Blog> call, Response<Blog> response) {
                    if (response.isSuccessful()) {
                        blog = response.body();

                        // Hiển thị ảnh cũ (nếu có)
                        if (blog.getAnh() != null && !blog.getAnh().isEmpty()) {
                            Glide.with(UpdateBlogActivity.this)
                                    .load(blog.getAnh())
                                    .into(imageView);
                        }

                        // Hiển thị thông tin sản phẩm cũ
                        etBlogName.setText(blog.getTieuDeBV());
                        etBlogContent.setText(blog.getNoiDung());

                    } else {
                        Toast.makeText(UpdateBlogActivity.this, "Lỗi lấy thông sản phẩm", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Blog> call, Throwable t) {
                    Toast.makeText(UpdateBlogActivity.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                }
            });
        }
        getSupportActionBar().setTitle("Update Blog");

        btnChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedImageUri != null) {
                    uploadImage();
                } else {
                    // Gửi yêu cầu API mà không đính kèm ảnh
                    updateBlogWithoutImage();
                }
            }
        });
    }
    private void updateBlogWithoutImage() {
        // Lấy thông tin từ các EditText
        String title = etBlogName.getText().toString();
        String des = etBlogContent.getText().toString();

        // Tạo đối tượng Product mới với ảnh cũ
        Blog updatedBlog = new Blog(title,des,blog.getAnh());

        Call<Blog> updateCall = apiService.updateBlog(idBlog, updatedBlog);
        updateCall.enqueue(new Callback<Blog>() {
            @Override
            public void onResponse(Call<Blog> call, Response<Blog> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(UpdateBlogActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(UpdateBlogActivity.this, "Cập nhật không thành công", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Blog> call, Throwable t) {
                Log.e("UpdateBlogActivity", "Lỗi: " + t.getMessage());
                Toast.makeText(getApplicationContext(), "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Chọn ảnh"), PICK_IMAGE_REQUEST);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void uploadImage() {
        try {
            InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);
            File file = new File(getCacheDir(), "image.jpg");
            writeFile(inputStream, file);

            RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part imagePart = MultipartBody.Part.createFormData("image", file.getName(), requestFile);

            Call<ResponseBody> call = apiService.uploadImage(imagePart);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        // Xử lý phản hồi thành công từ server
                        String img = null;
                        try {
                            img = response.body().string();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        String title = etBlogName.getText().toString();
                        String des = etBlogContent.getText().toString();
                        Blog updatedBlog = new Blog(title,des, img);
                        System.out.println("Anh trong blog mới"+updatedBlog.getAnh());
                        Call<Blog> updateCall = apiService.updateBlog(idBlog, updatedBlog);
                        updateCall.enqueue(new Callback<Blog>() {
                            @Override
                            public void onResponse(Call<Blog> call, Response<Blog> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(UpdateBlogActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(UpdateBlogActivity.this, "Cập nhật không thành công", Toast.LENGTH_SHORT).show();
                                }
                            }
                            @Override
                            public void onFailure(Call<Blog> call, Throwable t) {
                                Log.e("UpdateBlogActivity", "Lỗi: " + t.getMessage());
                                Toast.makeText(getApplicationContext(), "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Toast.makeText(getApplicationContext(), "Upload thất bại!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.e("UpdateBlogActivity", "Lỗi: " + t.getMessage());
                    Toast.makeText(getApplicationContext(), "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void writeFile(InputStream inputStream, File file) {
        try {
            OutputStream outputStream = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package com.example.applestore.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.FileUtils;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.applestore.APIService.APIService;
import com.example.applestore.R;
import com.example.applestore.Retrofit.RetrofitClient;
import com.example.applestore.model.Category;

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

public class AddCategoryActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    APIService apiService = RetrofitClient.getRetrofit().create(APIService.class);
    private Button btnChooseImage;
    private Button btnUpload;
    private ImageView imageView;
    private EditText etCategoryName;
    private Uri selectedImageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        btnChooseImage = findViewById(R.id.addCategoryImage);
        btnUpload = findViewById(R.id.btn_add_category);
        imageView = findViewById(R.id.imCategoryImage);
        etCategoryName = findViewById(R.id.etCategoryName);

        getSupportActionBar().setTitle("ADD Category");

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
                    Toast.makeText(getApplicationContext(), "Vui lòng chọn một ảnh", Toast.LENGTH_SHORT).show();
                }
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
                        Category category = new Category(img,etCategoryName.getText().toString());

                        Call<Category> call1 = apiService.createCategory(category);
                        call1.enqueue(new Callback<Category>() {
                            @Override
                            public void onResponse(Call<Category> call, Response<Category> response) {
                                if(response.isSuccessful())
                                {
                                    Toast.makeText(AddCategoryActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(AddCategoryActivity.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Category> call, Throwable t) {
                                System.out.println("Lỗi kết nối API");
                            }
                        });

//                        Toast.makeText(getApplicationContext(), "Upload thành công!", Toast.LENGTH_SHORT).show();
                    } else {
                        // Xử lý lỗi từ server
                        Toast.makeText(getApplicationContext(), "Upload thất bại!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    // Xử lý lỗi kết nối hoặc lỗi chung
                    Log.e("UploadImageActivity", "Lỗi: " + t.getMessage());
                    Toast.makeText(getApplicationContext(), "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void writeFile(InputStream inputStream, File file) {
        try {
            OutputStream outputStream = new FileOutputStream(file);
            byte[] buffer = new byte[4 * 1024]; // 4KB buffer
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

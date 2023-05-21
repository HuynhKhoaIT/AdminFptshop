package com.example.applestore.Activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.applestore.R;
import java.util.List;
public class DetailBlogActivity extends AppCompatActivity {
    TextView detailDesc, detailTitle;
    ImageView detailImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blog_detail);

        detailImage = findViewById(R.id.detailImage);
        detailDesc = findViewById(R.id.detailDesc);
        detailTitle = findViewById(R.id.detailTitle);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            //detailImage.setImageResource(bundle.getInt("Image"));
            Glide.with(this).load(bundle.getString("Image")).into(detailImage);
            detailTitle.setText(bundle.getString("Title"));
            detailDesc.setText(bundle.getString("Desc"));
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Detail Blog");
    }
    //    Bắt sự kiện khi bấm vào nút mũi tên quay lại
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}

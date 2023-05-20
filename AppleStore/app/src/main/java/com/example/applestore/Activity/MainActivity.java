package com.example.applestore.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import java.util.List;
import androidx.appcompat.widget.Toolbar;

import com.example.applestore.Fragment.AccountFragment;
import com.example.applestore.Fragment.BlogFragment;
import com.example.applestore.Fragment.CartFragment;
import com.example.applestore.Fragment.HomeFragment;
import com.example.applestore.Fragment.OrderFragment;
import com.example.applestore.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    CoordinatorLayout coordinatorLayout;
    BottomNavigationView bottomNavigationView;
    Toolbar toolbar;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        toolbar = findViewById(R.id.toolbar); //Ignore red line errors
//        setSupportActionBar(toolbar);
//      Ánh xạ
        coordinatorLayout = findViewById(R.id.coordinatorLayout);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new HomeFragment()).commit();
        }
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new HomeFragment()).commit();
                    break;
                case R.id.cart:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new CartFragment()).commit();
                    break;
                case R.id.blog:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new BlogFragment()).commit();
                    break;
                case R.id.order:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new OrderFragment()).commit();
                    break;
                case R.id.account:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new AccountFragment()).commit();
                    break;
            }
            return true;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Search...");
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
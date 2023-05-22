package com.example.applestore.Activity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.applestore.Fragment.AccountFragment;
import com.example.applestore.Fragment.BlogFragment;
import com.example.applestore.Fragment.CusFragment;
import com.example.applestore.Fragment.DoanhThuFragment;
import com.example.applestore.Fragment.HomeFragment;
import com.example.applestore.Fragment.OrderFragment;
import com.example.applestore.R;
import com.example.applestore.SharedPreferences.SharedPrefManager;
import com.example.applestore.model.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    CoordinatorLayout coordinatorLayout;
    BottomNavigationView bottomNavigationView;
    Toolbar toolbar;
    private SearchView searchView;
    private DrawerLayout drawerLayout;
    NavigationView navigationView;
    View headerView;
    TextView tvGmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//      Ánh xạ
        coordinatorLayout = findViewById(R.id.coordinatorLayout);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar); //Ignore red line errors
        headerView = navigationView.getHeaderView(0);
        tvGmail = headerView.findViewById(R.id.tvGmail);

        User user = SharedPrefManager.getInstance(this).getUser();
        tvGmail.setText(user.getEmail());

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav,
                R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }

        navigationView.setNavigationItemSelectedListener(this);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new HomeFragment()).commit();
                    break;
                case R.id.cart:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new CusFragment()).commit();
                    break;
                case R.id.blog:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new BlogFragment()).commit();
                    break;
                case R.id.order:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new OrderFragment()).commit();
                    break;
                case R.id.sales:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new DoanhThuFragment()).commit();
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new HomeFragment()).commit();
                break;
            case R.id.nav_customer:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new CusFragment()).commit();
                break;
            case R.id.nav_blog:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new BlogFragment()).commit();
                break;
            case R.id.nav_order:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new OrderFragment()).commit();
                break;
            case R.id.nav_sales:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new DoanhThuFragment()).commit();
                break;
            case R.id.logout:
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Xóa các Activity trước đó khỏi ngăn xếp
                SharedPrefManager.getInstance(MainActivity.this).logout();
                startActivity(intent);
                finish(); // Kết thúc MainActivity
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



}

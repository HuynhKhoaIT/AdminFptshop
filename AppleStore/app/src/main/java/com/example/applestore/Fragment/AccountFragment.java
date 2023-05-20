package com.example.applestore.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.applestore.Activity.DetailProductActivity;
import com.example.applestore.Activity.LoginActivity;
import com.example.applestore.Activity.UpdateAccount;
import com.example.applestore.R;
import com.example.applestore.SharedPreferences.SharedPrefManager;
import com.example.applestore.model.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class AccountFragment extends Fragment {
    TextView userName;
    Button logout;
    GoogleSignInClient gClient;
    GoogleSignInOptions gOptions;

    ImageView imvAvt;
    TextView tvName,tvPhone,tvEmail,tvAddress;

    Button btnUpdate;
    Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        logout = view.findViewById(R.id.logout);

        //anh xa
        context = getContext();
        btnUpdate = (Button)view.findViewById(R.id.btnUpdate);
        tvName = (TextView)view.findViewById(R.id.tvFname);
        tvPhone = (TextView)view.findViewById(R.id.tvPhone);
        tvEmail = (TextView)view.findViewById(R.id.tvEmail);
        tvAddress = (TextView)view.findViewById(R.id.tvAddress);

        gOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gClient = GoogleSignIn.getClient(getContext(), gOptions);
        GoogleSignInAccount gAccount = GoogleSignIn.getLastSignedInAccount(getContext());
        if (gAccount != null){
            String gName = gAccount.getDisplayName();
            userName.setText(gName);
        }
        //getUser from SharedPrefManager
        User user = SharedPrefManager.getInstance(getContext()).getUser();

        // show user info
        tvName.setText(user.getTenKH());
        tvAddress.setText(user.getDiaChi());
        tvPhone.setText(user.getPhone());
        tvEmail.setText(user.getEmail());

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateAccount.class);
                context.startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut(); //Đăng xuất khỏi Firebase Authentication
                gClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        getActivity().finish(); //Kết thúc fragment hiện tại
                        startActivity(new Intent(getActivity(), LoginActivity.class)); //Chuyển đến màn hình đăng nhập
                    }
                });
                //Dang xuat database
                SharedPrefManager.getInstance(getContext()).logout();
            }
        });
        return view;
    }
}

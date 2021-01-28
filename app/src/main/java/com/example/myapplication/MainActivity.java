package com.example.myapplication;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import com.example.myapplication.AsyncTaskLoader.ListNewsLoader;
import com.example.myapplication.Bottom.CalendarFragment;
import com.example.myapplication.Bottom.HomeFragment;
import com.example.myapplication.Bottom.TrendFragment;
import com.example.myapplication.Bottom.UserFragment;
import com.example.myapplication.Bottom.VideoFragment;
import com.example.myapplication.Login.LoginActivity;
import com.example.myapplication.data.model.News;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<LinkedList<News>>, Loader.OnLoadCanceledListener<LinkedList<News>> {
    private BottomNavigationView bottomNavigationView;
    private static final int LOGIN_REQUEST = 1;
    public static String USER_NAME = "USERNAME";
    DrawerLayout drawerLayout;
    Fragment selectedFragment = null;
    LoaderManager loaderManager;
    private final int NEW_ID = 1111;
    private TextView txtUserName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        txtUserName = findViewById(R.id.txt_DangNhap);
        loaderManager = LoaderManager.getInstance(this);
        Loader<LinkedList<News>> loader =  loaderManager.getLoader(NEW_ID);
        if (loader == null) {
            loader = loaderManager.initLoader(NEW_ID, null, this);
        } else {
            loader = loaderManager.restartLoader(NEW_ID, null, this);
        }
        loader.registerOnLoadCanceledListener(this);
        getSupportFragmentManager().beginTransaction().replace( R.id.fragment_container,
                new HomeFragment()).commit();
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.action_home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.action_video:
                            selectedFragment = new VideoFragment();
                            break;
                        case R.id.action_calendar:
                            selectedFragment = new CalendarFragment();
                            break;
                        case R.id.action_trend:
                            selectedFragment = new TrendFragment();
                            break;
                        case R.id.action_user:
                            selectedFragment = new UserFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace( R.id.fragment_container,
                            selectedFragment).commit();
                    return true;
                }
            };

    public void UserLogin(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivityForResult(intent,LOGIN_REQUEST);
    }
    public void UserLogout(View view){
        ((UserFragment) selectedFragment).UserLogout(view);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOGIN_REQUEST) {
            if (resultCode == RESULT_OK) {
                Bundle bundle = data.getExtras();
                String userName = bundle.getString(LoginActivity.MAIN_REPLY);
                if(userName != null) {
                    Bundle bundle_Login = new Bundle();
                    bundle_Login.putString(USER_NAME, userName);
                    selectedFragment = new UserFragment();
                    selectedFragment.setArguments(bundle_Login);
                    getSupportFragmentManager().beginTransaction().replace( R.id.fragment_container,
                            selectedFragment).commit();
                }
            }
        }
    }

    @NonNull
    @Override
    public Loader<LinkedList<News>> onCreateLoader(int id, @Nullable Bundle args) {
        return new ListNewsLoader(this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<LinkedList<News>> loader, LinkedList<News> data) {
        if(data != null && data.size() > 0) {
            String name = data.get(0).getTitle();
        }
        else {

        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<LinkedList<News>> loader) {

    }

    @Override
    public void onLoadCanceled(@NonNull Loader<LinkedList<News>> loader) {

    }
}
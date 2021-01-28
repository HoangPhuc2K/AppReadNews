package com.example.myapplication.Login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.AsyncTaskLoader.UserLoader;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.data.model.User;

import java.util.LinkedList;

public class LoginActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<LinkedList<User>>, Loader.OnLoadCanceledListener<LinkedList<User>> {

    public static final String MAIN_REPLY = "com.example.myapplication.extra.REPLY";
    private EditText editUserName;
    private EditText editPassword;
    private String UserName;
    private String PassWord;
    LoaderManager loaderManager;
    private final int LOGIN_ID = 111;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editUserName = findViewById(R.id.login_Username);
        editPassword = findViewById(R.id.login_Password);
        loaderManager = LoaderManager.getInstance(this);
    }

    public void logIn(View v){
        UserName = editUserName.getText().toString();
        PassWord = editPassword.getText().toString();
        if(UserName.length() != 0 && PassWord.length() != 0 ) {
            if (UserName.length() > 7 && PassWord.length() > 7) {
                Loader<LinkedList<User>> loader = loaderManager.getLoader(LOGIN_ID);
                if (loader == null) {
                    loader = loaderManager.initLoader(LOGIN_ID, null, this);
                } else {
                    loader = loaderManager.restartLoader(LOGIN_ID, null, this);
                }
                loader.registerOnLoadCanceledListener(this);
            } else {
                Toast.makeText(this, "Tài khoản và mật khẩu ít nhất 8 kí tự", Toast.LENGTH_SHORT).show();
                editUserName.requestFocus();
            }
        }else {
            if(UserName.length() == 0 || PassWord.length() == 0 ) {
                Toast.makeText(this, "Tài khoản hoặc mật khẩu không được để trống", Toast.LENGTH_SHORT).show();
                editUserName.requestFocus();
            }
        }

    }
    public void TaoTaiKhoan(View view)
    {
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }

    @NonNull
    @Override
    public Loader<LinkedList<User>> onCreateLoader(int id, @Nullable Bundle args) {
        return new UserLoader(this,UserName,PassWord);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<LinkedList<User>> loader, LinkedList<User> data) {
        if(data != null && data.size() > 0){
            String name = data.get(0).getUserName();
            Log.d("TEST_LOG",name);
            Intent intent =  new Intent();
            intent.putExtra(MAIN_REPLY,name);
            setResult(RESULT_OK,intent);
            finish();
        }
        else {
            Toast.makeText(this,"Sai tài khoản hoặc mật khẩu",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<LinkedList<User>> loader) {

    }

    @Override
    public void onLoadCanceled(@NonNull Loader<LinkedList<User>> loader) {

    }

}
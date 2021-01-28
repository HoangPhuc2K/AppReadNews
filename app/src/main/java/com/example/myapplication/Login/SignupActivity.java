package com.example.myapplication.Login;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import com.example.myapplication.AsyncTaskLoader.SingUpTaskLoader;
import com.example.myapplication.R;

public class SignupActivity extends AppCompatActivity  implements LoaderManager.LoaderCallbacks<String>, Loader.OnLoadCanceledListener<String> {

    private EditText editUserName;
    private EditText editPassword;
    private EditText editPassCom;
    LoaderManager loaderManager;
    private final  int SINGUP = 222;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        editUserName = findViewById(R.id.Signup_Username);
        editPassword = findViewById(R.id.Signup_Password);
        editPassCom = findViewById(R.id.Signup_VaPassword);
        loaderManager = LoaderManager.getInstance(this);
    }


    public void singUp(View view) {
        if(editPassword.getText().toString().length() > 7) {
            if (editPassword.getText().toString().equals(editPassCom.getText().toString())) {
                if (editUserName.getText().toString().length() > 7) {
                    Bundle bundle = new Bundle();
                    bundle.putString("UseMember", editUserName.getText().toString());
                    bundle.putString("PassWord", editPassword.getText().toString());
                    Loader<String> loader = loaderManager.getLoader(SINGUP);
                    if (loader == null) {
                        loader = loaderManager.initLoader(SINGUP, bundle, this);
                    } else {
                        loader = loaderManager.restartLoader(SINGUP, bundle, this);
                    }
                    loader.registerOnLoadCanceledListener(this);
                } else {
                    Toast.makeText(this, "Tài khoản ít nhất 8 kí tự", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                editPassCom.setText("");
                editPassCom.requestFocus();
            }
        }else {
            Toast.makeText(this, "Mật khẩu ít nhất 8 kí tự", Toast.LENGTH_SHORT).show();
            editPassword.setText("");
            editPassword.requestFocus();
        }
    }


    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        return new SingUpTaskLoader(this, args.getString("UseMember"), args.getString("PassWord"));
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        if(data != null && data.length() > 0) {
            if (data.equals("success")) {
                Toast.makeText(this, "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Đăng kí thất bại", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this, "Tài khoản đã có người sử dụng", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }

    @Override
    public void onLoadCanceled(@NonNull Loader<String> loader) {

    }
}
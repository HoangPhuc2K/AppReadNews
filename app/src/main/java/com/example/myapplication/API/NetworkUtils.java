package com.example.myapplication.API;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class NetworkUtils {
    public static String login(String username,String password) {
        Uri builtURI = Uri.parse("http://10.0.2.2:8000/api/Member?").buildUpon()
                .appendQueryParameter("Username",username)
                .appendQueryParameter("Password",password)
                .build();
        Log.d("LOG_LOGIN",builtURI.toString());
        try {
            URL requestURL = new URL(builtURI.toString());
            return callAPI(requestURL, "POST");
        } catch (MalformedURLException e) {
            return null;
        }
    }
    public static String singUp(String usermember, String password) {
        Uri builtURI = Uri.parse("http://10.0.2.2:8000/api/Member/SingUp?").buildUpon()
                .appendQueryParameter("Usemember",usermember)
                .appendQueryParameter("Password",password)
                .appendQueryParameter("Email","adsd")
                .appendQueryParameter("Fullname",usermember)
                .build();
        Log.d("LOG_SIGUP",builtURI.toString());
        try {
            URL requestURL = new URL(builtURI.toString());
            Log.d("LOG_SIGUP",builtURI.toString());
            return callAPI(requestURL, "POST");
        } catch (MalformedURLException e) {
            return null;
        }
    }
    public static String loadListNews() {
        Uri builtURI = Uri.parse("http://10.0.2.2:8000/api/News").buildUpon()
                .build();
        try {
            URL requestURL = new URL(builtURI.toString());
            return callAPI(requestURL, "GET");
        } catch (MalformedURLException e) {
            return null;
        }
    }
    public static String saveNews(String iduser,int idnews) {
        Uri builtURI = Uri.parse("http://10.0.2.2:8000/api/save?").buildUpon()
                .appendQueryParameter("iduser", String.valueOf(iduser))
                .appendQueryParameter("idnews", String.valueOf(idnews))
                .build();
        Log.d("save",builtURI.toString());
        try {
            URL requestURL = new URL(builtURI.toString());
            return callAPI(requestURL, "POST");
        } catch (MalformedURLException e) {
            return null;
        }
    }
    public static String loadSaveNews(String id) {
        Uri builtURI = Uri.parse("http://10.0.2.2:8000/api/savenews/"+id).buildUpon()
                .build();
        try {
            URL requestURL = new URL(builtURI.toString());
            return callAPI(requestURL, "GET");
        } catch (MalformedURLException e) {
            return null;
        }
    }
    public static String loadListNewsSport() {
        Uri builtURI = Uri.parse("http://10.0.2.2:8000/api/News/ListSportNews").buildUpon()
                .build();
        try {
            URL requestURL = new URL(builtURI.toString());
            return callAPI(requestURL, "GET");
        } catch (MalformedURLException e) {
            return null;
        }
    }
    public static String loadListNewsNew() {
        Uri builtURI = Uri.parse("http://10.0.2.2:8000/api/News/ListHotNews").buildUpon()
                .build();
        try {
            URL requestURL = new URL(builtURI.toString());
            return callAPI(requestURL, "GET");
        } catch (MalformedURLException e) {
            return null;
        }
    }

    public static String callAPI(URL requestURL, String method) {
        HttpURLConnection urlConnection = null;
        String result = "";
        try {
            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod(method);
            urlConnection.connect();
            // Get the InputStream.
            InputStream inputStream = urlConnection.getInputStream();
            result = convertISToString(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return result;
    }

    public static String convertISToString(InputStream inputStream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder builder = new StringBuilder();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                builder.append(line + "\n");
                if (builder.length() == 0) {
                    return null;
                }
            }
        } catch (IOException e) {
            return null;
        }
        return builder.toString();
    }
    //    CHILINH
    public static String loadListComment() {
        Uri builtURI = Uri.parse("http://10.0.2.2:8000/api/Comment").buildUpon()
                .build();
        try {
            URL requestURL = new URL(builtURI.toString());
            return callAPI(requestURL, "GET");
        } catch (MalformedURLException e) {
            return null;
        }
    }
    //InsertComment
    public static String InsertComment(String IdNews_FK, String Context, String UserMember_FK) {
        Uri builtURI = Uri.parse("http://10.0.2.2:8000/api/InsertComment?").buildUpon()
                .appendQueryParameter("IdNews_FK", String.valueOf(IdNews_FK))
                .appendQueryParameter("Title", "Đây là tiêu đề của bình luận")
                .appendQueryParameter("Context", String.valueOf(Context))
                .appendQueryParameter("Usemember_FK", String.valueOf(UserMember_FK))
                .appendQueryParameter("Status", "Yes")
                .build();
        Log.d("InsertComment",builtURI.toString());
        try {
            URL requestURL = new URL(builtURI.toString());
            return callAPI(requestURL, "POST");
        } catch (MalformedURLException e) {
            return null;
        }
    }
//    end
}

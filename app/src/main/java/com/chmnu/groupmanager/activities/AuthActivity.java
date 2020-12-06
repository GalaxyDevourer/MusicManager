package com.chmnu.groupmanager.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.chmnu.groupmanager.MainActivity;
import com.chmnu.groupmanager.R;
import com.chmnu.groupmanager.models.entities.http.UserData;
import com.chmnu.groupmanager.models.utils.http.HttpDataPoster;

import org.json.JSONException;
import org.json.JSONObject;

public class AuthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
    }

    public void onLogIn (View view) {
        JSONObject data = new JSONObject();
        try {
            data.put("password", ((EditText) findViewById(R.id.auth_password_input)).getText());
            data.put("username", ((EditText) findViewById(R.id.auth_login_input)).getText());
        }
        catch (JSONException ex) {
            ex.printStackTrace();
        }

        String strToken = new HttpDataPoster(
                "http://192.168.1.127/api/?action=login", data).getData();

        JSONObject tokenData = null;
        String token = null;
        try {
            System.out.println("strToken: " + strToken);
            tokenData = new JSONObject(strToken);
            System.out.println("TokenData: " + tokenData);
            token = tokenData.getString("token");
            System.out.println("Token: " + token);
        }
        catch (JSONException ex) {
            ex.printStackTrace();
        }

        try {
            if (token.toLowerCase() == "null") {
                Toast.makeText(this, "Password is incorrect!", Toast.LENGTH_LONG).show();
            }
            else {
                UserData.token = token;
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }
        catch (NullPointerException ex) {
            Toast.makeText(this, "Oops There was some exception, please, check stacktrace...", Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }
}
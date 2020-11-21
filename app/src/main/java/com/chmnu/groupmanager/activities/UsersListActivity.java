package com.chmnu.groupmanager.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.chmnu.groupmanager.R;
import com.chmnu.groupmanager.models.entities.http.User;
import com.chmnu.groupmanager.models.utils.http.HttpDataGetter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class UsersListActivity extends AppCompatActivity {

    public static String USER = "user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);

        ListView listView = findViewById(R.id.users_list_users_list);
        ArrayAdapter<User> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1,
                getUsers()
        );
        listView.setAdapter(adapter);

        AdapterView.OnItemClickListener listener = (parent, view, position, id) -> {
            User user = (User) parent.getItemAtPosition(position);

            Intent intent = new Intent(UsersListActivity.this, TasksListActivity.class);
            intent.putExtra(UsersListActivity.USER, user);
            startActivity(intent);
        };
        listView.setOnItemClickListener(listener);
    }

    public ArrayList<User> getUsers () {
        ArrayList<User> usersList = new ArrayList<>();
        String response = new HttpDataGetter("https://jsonplaceholder.typicode.com/users").getData();

        try {
            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                User post = new User(obj.getInt("id"), obj.getString("name"), obj.getString("username"));
                usersList.add(post);
            }
        }
        catch (JSONException jex) {
            jex.printStackTrace();
        }

        return  usersList;
    }
}


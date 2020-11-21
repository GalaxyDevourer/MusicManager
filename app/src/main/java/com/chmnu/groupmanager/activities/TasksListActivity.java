package com.chmnu.groupmanager.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.chmnu.groupmanager.R;
import com.chmnu.groupmanager.models.entities.http.Task;
import com.chmnu.groupmanager.models.entities.http.User;
import com.chmnu.groupmanager.models.utils.http.HttpDataGetter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class TasksListActivity extends AppCompatActivity {

    public static String TASK = "task";

    private User user = new User();
    private ArrayList<Task> taskList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks_list);

        Intent intent = getIntent();
        user = intent.getParcelableExtra(UsersListActivity.USER);
        assert user != null;

        TextView tasks_label = findViewById(R.id.tasks_list_task_label);
        tasks_label.setText(user.toString());

        List<String> filters = Arrays.asList("All", "Completed", "In Progress");
        Spinner filter = findViewById(R.id.tasks_list_filter_list);
        ListAdapter filterAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, filters);
        filter.setAdapter((SpinnerAdapter) filterAdapter);

        taskList = getAllTasks(user.getId(), "All");
        ArrayAdapter<Task> tasksAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, taskList);

        ListView listView = findViewById(R.id.tasks_list_tasks_list);
        listView.setAdapter(tasksAdapter);

        AdapterView.OnItemClickListener listener = (parent, view, position, id) -> {
            Task task = (Task) parent.getItemAtPosition(position);

            Intent intent_ = new Intent(TasksListActivity.this, TaskActivity.class);
            intent_.putExtra(TasksListActivity.TASK, task);
            startActivity(intent_);
        };
        listView.setOnItemClickListener(listener);
    }

    public ArrayList<Task> getAllTasks (Integer userId, String filter) {
        ArrayList<Task> tasksList = new ArrayList<>();
        String response = new HttpDataGetter("https://jsonplaceholder.typicode.com/users/" + userId + "/todos").getData();

        try {
            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);

                boolean status = obj.getBoolean("completed");
                if (filter.equals("Completed") && status) {
                    Task task = new Task(obj.getInt("id"), statusTranslate(status), obj.getString("title"));
                    tasksList.add(task);
                }
                else if (filter.equals("In Progress") && !status) {
                    Task task = new Task(obj.getInt("id"), statusTranslate(status), obj.getString("title"));
                    tasksList.add(task);
                }
                else if (filter.equals("All")) {
                    Task task = new Task(obj.getInt("id"), statusTranslate(status), obj.getString("title"));
                    tasksList.add(task);
                }
            }
        }
        catch (JSONException jex) {
            jex.printStackTrace();
        }

        return  tasksList;
    }

    public void onFilterButton (View view) {
        Spinner filter_type = findViewById(R.id.tasks_list_filter_list);

        ArrayAdapter<Task> tasksAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                getAllTasks(user.getId(), filter_type.getSelectedItem().toString()));

        ListView listView = findViewById(R.id.tasks_list_tasks_list);
        listView.setAdapter(tasksAdapter);
    }

    public String statusTranslate (Boolean flag) {
        if (flag) return "Completed";
        else return "In Progress";
    }

}


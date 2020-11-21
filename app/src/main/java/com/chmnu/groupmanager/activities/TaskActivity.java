package com.chmnu.groupmanager.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.chmnu.groupmanager.R;
import com.chmnu.groupmanager.models.entities.http.Task;

import androidx.appcompat.app.AppCompatActivity;

public class TaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        Intent intent = getIntent();
        Task task = intent.getParcelableExtra(TasksListActivity.TASK);

        TextView task_view = findViewById(R.id.task_label);
        String task_label = "Task №" + task.getId();
        task_view.setText(task_label);

        TextView status = findViewById(R.id.task_status_data);
        status.setText(task.getStatus());

        TextView title = findViewById(R.id.task_title_label);
        String title_data = "Title: " + task.getTitle();
        title.setText(title_data);

    }
}

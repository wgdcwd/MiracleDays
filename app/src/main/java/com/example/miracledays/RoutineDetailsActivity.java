package com.example.miracledays;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.miracledays.models.Routine;
import com.example.miracledays.models.Task;
import com.example.miracledays.utils.DataManager;

import java.util.ArrayList;
import java.util.List;

public class RoutineDetailsActivity extends AppCompatActivity {

    private TextView routineNameText;
    private LinearLayout taskContainer;

    private DataManager dataManager;
    private Routine currentRoutine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine_details);

        dataManager = new DataManager(this);

        String routineId = getIntent().getStringExtra("routine_id");
        currentRoutine = getRoutineById(routineId);

        setupViews();
        loadTasks();
    }

    private void setupViews() {
        routineNameText = findViewById(R.id.text_routine_name);
        taskContainer = findViewById(R.id.linear_task_container);

        if (currentRoutine != null) {
            routineNameText.setText(currentRoutine.getName());
        }
    }

    private Routine getRoutineById(String routineId) {
        List<Routine> routines = dataManager.getRoutines();
        for (Routine routine : routines) {
            if (routine.getId().equals(routineId)) {
                return routine;
            }
        }
        return null;
    }

    private void loadTasks() {
        List<Task> allTasks = dataManager.getTasks();
        List<Task> relatedTasks = new ArrayList<>();

        for (Task task : allTasks) {
            if (task.getRoutineId().equals(currentRoutine.getId())) {
                relatedTasks.add(task);
            }
        }

        for (Task task : relatedTasks) {
            addTaskView(task);
        }
    }

    private void addTaskView(Task task) {
        LinearLayout taskView = (LinearLayout) getLayoutInflater()
                .inflate(R.layout.item_task, taskContainer, false);

        TextView taskPeriod = taskView.findViewById(R.id.text_task_period);
        TextView taskStatus = taskView.findViewById(R.id.text_task_status);

        taskPeriod.setText("기간: " + task.getDueDate());
        taskStatus.setText(task.isCompleted() ? "성공" : "대기중");

        taskContainer.addView(taskView);
    }
}

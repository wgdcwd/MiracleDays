package com.example.miracledays.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.miracledays.R;
import com.example.miracledays.models.Routine;
import com.example.miracledays.models.RoutineHistory;
import com.example.miracledays.utils.DataManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TaskFragment extends Fragment {

    private LinearLayout taskContainer;
    private DataManager dataManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task, container, false);

        taskContainer = view.findViewById(R.id.task_container);
        dataManager = new DataManager(requireContext());

        loadTasks();
        return view;
    }

    // 할 일 불러오기
    private void loadTasks() {
        taskContainer.removeAllViews(); // 기존 항목 제거
        List<Routine> routines = dataManager.getRoutines();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String today = sdf.format(new Date());

        for (Routine routine : routines) {
            // 현재 주기 내의 할 일만 표시
            RoutineHistory currentTask = getCurrentTask(routine, today);
            if (currentTask != null && !currentTask.getStatus().equals("성공")) {
                addTaskView(routine, currentTask);
            }
        }
    }

    // 현재 주기 가져오기
    private RoutineHistory getCurrentTask(Routine routine, String today) {
        for (RoutineHistory history : routine.getHistory()) {
            if (history.getStartDate().compareTo(today) <= 0 && history.getEndDate().compareTo(today) >= 0) {
                return history;
            }
        }
        return null;
    }

    // 할 일 항목 추가
    private void addTaskView(Routine routine, RoutineHistory task) {
        View taskView = getLayoutInflater().inflate(R.layout.item_task, null);

        TextView taskName = taskView.findViewById(R.id.text_task_name);
        CheckBox checkBox = taskView.findViewById(R.id.checkbox_task);

        taskName.setText(routine.getName());
        checkBox.setChecked(task.getStatus().equals("성공"));

        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                task.setStatus("성공");
                task.setCompletedAt(new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(new Date()));
                Toast.makeText(getContext(), routine.getName() + " 완료!", Toast.LENGTH_SHORT).show();
            } else {
                task.setStatus("대기중");
                task.setCompletedAt("");
                Toast.makeText(getContext(), routine.getName() + " 취소됨", Toast.LENGTH_SHORT).show();
            }
            dataManager.saveRoutines(dataManager.getRoutines());
            loadTasks();
        });

        taskContainer.addView(taskView);
    }
}

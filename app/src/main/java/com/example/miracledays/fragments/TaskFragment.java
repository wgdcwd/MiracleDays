package com.example.miracledays.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miracledays.R;
import com.example.miracledays.models.Task;
import com.example.miracledays.utils.DataManager;
import com.example.miracledays.utils.TaskAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskFragment extends Fragment {

    private RecyclerView taskRecyclerView;
    private TaskAdapter taskAdapter;
    private DataManager dataManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task, container, false);

        taskRecyclerView = view.findViewById(R.id.task_recycler_view);
        taskRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        dataManager = new DataManager(requireContext());
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadTasks(); // Fragment 활성화 시 항상 Task 로드
    }

    private void loadTasks() {
        List<Task> allTasks = dataManager.getTasks();
        List<Task> currentTasks = new ArrayList<>();

        String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        for (Task task : allTasks) {
            String[] period = task.getDueDate().split(" ~ ");
            if (today.compareTo(period[0]) >= 0 && today.compareTo(period[1]) <= 0) {
                currentTasks.add(task);
            }
        }

        if (currentTasks.isEmpty()) {
            // 디버깅용 로그 추가
            System.out.println("현재 Task 없음. allTasks 크기: " + allTasks.size());
            for (Task task : allTasks) {
                System.out.println("Task ID: " + task.getId() + ", 기간: " + task.getDueDate());
            }
        }

        // enableCompleteButton 값을 true로 설정
        boolean enableCompleteButton = true;

        // TaskAdapter 생성 시 세 개의 파라미터 전달
        taskAdapter = new TaskAdapter(currentTasks, dataManager, enableCompleteButton);
        taskRecyclerView.setAdapter(taskAdapter);
    }


}

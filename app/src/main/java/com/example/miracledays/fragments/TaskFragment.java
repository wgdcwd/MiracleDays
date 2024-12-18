package com.example.miracledays.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miracledays.R;
import com.example.miracledays.models.Routine;
import com.example.miracledays.models.Task;
import com.example.miracledays.utils.DataManager;

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
        List<Routine> routines = dataManager.getRoutines(); // 저장된 루틴 가져오기
        dataManager.updateTasksForAllRoutines(routines);    // 루틴 기반으로 Task 갱신
        loadTasks();                                        // Task 목록 로드
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

        taskAdapter = new TaskAdapter(currentTasks);
        taskRecyclerView.setAdapter(taskAdapter);
    }

    // Adapter class
    private class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
        private final List<Task> tasks;

        public TaskAdapter(List<Task> tasks) {
            this.tasks = tasks;
        }

        @NonNull
        @Override
        public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
            return new TaskViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
            Task task = tasks.get(position);

            holder.taskName.setText(task.getName());
            holder.taskDueDate.setText("기간: " + task.getDueDate());

            if (task.isCompleted()) {
                holder.taskStatus.setText("완료됨");
                holder.taskStatus.setEnabled(false);
            } else {
                holder.taskStatus.setText("대기 중");
                holder.taskStatus.setEnabled(true);

                // 완료 처리 버튼 클릭 리스너 추가
                holder.taskStatus.setOnClickListener(v -> {
                    // Task 완료 처리
                    task.setCompleted(true);
                    new DataManager(holder.itemView.getContext()).completeTask(task);

                    // 리스트에서 안전하게 제거
                    int adapterPosition = holder.getAdapterPosition();
                    if (adapterPosition != RecyclerView.NO_POSITION) {
                        tasks.remove(adapterPosition);
                        notifyDataSetChanged(); // 전체 리스트 새로고침
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return tasks.size();
        }

        class TaskViewHolder extends RecyclerView.ViewHolder {
            TextView taskName, taskDueDate;
            Button taskStatus;

            public TaskViewHolder(@NonNull View itemView) {
                super(itemView);
                taskName = itemView.findViewById(R.id.text_task_name);
                taskDueDate = itemView.findViewById(R.id.text_task_due_date);
                taskStatus = itemView.findViewById(R.id.button_task_complete);
            }
        }
    }
}

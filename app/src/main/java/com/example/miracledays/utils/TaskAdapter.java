package com.example.miracledays.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miracledays.R;
import com.example.miracledays.models.Task;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private final List<Task> tasks;
    private final DataManager dataManager;
    private final boolean enableCompleteButton; // 버튼 활성화 여부

    public TaskAdapter(List<Task> tasks, DataManager dataManager, boolean enableCompleteButton) {
        this.tasks = tasks;
        this.dataManager = dataManager;
        this.enableCompleteButton = enableCompleteButton; // 활성화 여부 설정
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
            holder.taskStatus.setEnabled(enableCompleteButton);

            if (enableCompleteButton) {
                holder.taskStatus.setOnClickListener(v -> {
                    // Task 완료 처리
                    task.setCompleted(true);
                    dataManager.completeTask(task);

                    // RecyclerView 업데이트
                    tasks.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, tasks.size());
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView taskName, taskDueDate, taskStatus;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            taskName = itemView.findViewById(R.id.text_task_name);
            taskDueDate = itemView.findViewById(R.id.text_task_due_date);
            taskStatus = itemView.findViewById(R.id.button_task_complete);
        }
    }
}

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
    private final boolean enableCompleteButton;

    public TaskAdapter(List<Task> tasks, boolean enableCompleteButton) {
        this.tasks = tasks;
        this.enableCompleteButton = enableCompleteButton;
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

    // ViewHolder 클래스
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

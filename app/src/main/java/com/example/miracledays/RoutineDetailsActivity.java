package com.example.miracledays;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.miracledays.models.Routine;
import com.example.miracledays.models.RoutineHistory;
import com.example.miracledays.utils.DataManager;

import java.util.List;

public class RoutineDetailsActivity extends AppCompatActivity {

    private TextView routineNameText;
    private LinearLayout historyContainer;

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
        loadHistory();
    }

    private void setupViews() {
        routineNameText = findViewById(R.id.text_routine_name);
        historyContainer = findViewById(R.id.linear_history_container);

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

    private void loadHistory() {
        List<RoutineHistory> history = currentRoutine.getHistory();

        if (history == null || history.isEmpty()) {
            historyContainer.removeAllViews();
            TextView noHistoryView = new TextView(this);
            noHistoryView.setText("기록 없음");
            noHistoryView.setTextSize(16);
            noHistoryView.setTextColor(getResources().getColor(android.R.color.darker_gray));
            noHistoryView.setGravity(Gravity.CENTER);
            historyContainer.addView(noHistoryView);
        } else {
            historyContainer.removeAllViews();
            for (RoutineHistory historyRecord : history) {
                addHistoryView(historyRecord);
            }
        }
    }

    private void addHistoryView(RoutineHistory history) {
        View historyView = getLayoutInflater().inflate(R.layout.item_routine_history, historyContainer, false);

        TextView periodView = historyView.findViewById(R.id.text_history_period);
        TextView statusView = historyView.findViewById(R.id.text_history_status);
        TextView completedAtView = historyView.findViewById(R.id.text_history_completed_at);

        periodView.setText("기간: " + history.getStartDate() + " ~ " + history.getEndDate());
        statusView.setText("상태: " + history.getStatus());
        completedAtView.setText("완료 시각: " + history.getCompletedAt());

        historyContainer.addView(historyView);
    }
}

package com.example.miracledays.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.miracledays.R;
import com.example.miracledays.models.Routine;
import com.example.miracledays.utils.DataManager;

import java.util.List;
import java.util.UUID;

public class RoutineFragment extends Fragment {

    private DataManager dataManager;
    private LinearLayout routineContainer; // 루틴 목록을 표시하는 레이아웃

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_routine, container, false);

        dataManager = new DataManager(requireContext());
        routineContainer = view.findViewById(R.id.routine_container);

        Button addButton = view.findViewById(R.id.button_add_routine);
        addButton.setOnClickListener(v -> showAddRoutineDialog());

        loadRoutines();
        return view;
    }

    // 루틴 데이터 불러오기 및 표시
    private void loadRoutines() {
        routineContainer.removeAllViews();
        List<Routine> routines = dataManager.getRoutines();

        for (Routine routine : routines) {
            View routineView = getLayoutInflater().inflate(R.layout.item_routine, null);

            TextView nameText = routineView.findViewById(R.id.text_routine_name);
            Button deleteButton = routineView.findViewById(R.id.button_delete);
            Button editButton = routineView.findViewById(R.id.button_edit);

            nameText.setText(routine.getName());

            deleteButton.setOnClickListener(v -> {
                routines.remove(routine);
                dataManager.saveRoutines(routines);
                loadRoutines();
                Toast.makeText(getContext(), "루틴 삭제됨", Toast.LENGTH_SHORT).show();
            });

            editButton.setOnClickListener(v -> showEditRoutineDialog(routine));

            routineContainer.addView(routineView);
        }
    }

    // 루틴 추가 다이얼로그
    private void showAddRoutineDialog() {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_routine, null);

        EditText nameInput = dialogView.findViewById(R.id.input_routine_name);
        EditText periodInput = dialogView.findViewById(R.id.input_routine_period);

        new androidx.appcompat.app.AlertDialog.Builder(requireContext())
                .setTitle("새 루틴 추가")
                .setView(dialogView)
                .setPositiveButton("추가", (dialog, which) -> {
                    String name = nameInput.getText().toString();
                    int period = Integer.parseInt(periodInput.getText().toString());

                    Routine newRoutine = new Routine(UUID.randomUUID().toString(), name, "n일마다", period, 1);
                    List<Routine> routines = dataManager.getRoutines();
                    routines.add(newRoutine);
                    dataManager.saveRoutines(routines);
                    loadRoutines();
                })
                .setNegativeButton("취소", null)
                .show();
    }

    // 루틴 수정 다이얼로그
    private void showEditRoutineDialog(Routine routine) {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_routine, null);

        EditText nameInput = dialogView.findViewById(R.id.input_routine_name);
        EditText periodInput = dialogView.findViewById(R.id.input_routine_period);

        nameInput.setText(routine.getName());
        periodInput.setText(String.valueOf(routine.getPeriodValue()));

        new androidx.appcompat.app.AlertDialog.Builder(requireContext())
                .setTitle("루틴 수정")
                .setView(dialogView)
                .setPositiveButton("수정", (dialog, which) -> {
                    routine.setName(nameInput.getText().toString());
                    routine.setPeriodValue(Integer.parseInt(periodInput.getText().toString()));

                    List<Routine> routines = dataManager.getRoutines();
                    dataManager.saveRoutines(routines);
                    loadRoutines();
                })
                .setNegativeButton("취소", null)
                .show();
    }
}

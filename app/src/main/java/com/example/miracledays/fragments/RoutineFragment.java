package com.example.miracledays.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.miracledays.R;
import com.example.miracledays.RoutineAddActivity;
import com.example.miracledays.RoutineDetailsActivity;
import com.example.miracledays.models.Routine;
import com.example.miracledays.utils.DataManager;

import java.util.List;

public class RoutineFragment extends Fragment {

    private LinearLayout routineContainer;
    private DataManager dataManager;
    private View currentButtonTab; // 현재 표시된 버튼 탭

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_routine, container, false);

        routineContainer = view.findViewById(R.id.routine_container);
        dataManager = new DataManager(requireContext());

        // 루틴 추가 버튼
        view.findViewById(R.id.button_add_routine).setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), RoutineAddActivity.class);
            startActivity(intent);
        });

        loadRoutines();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadRoutines(); // 화면 갱신
    }

    private void loadRoutines() {
        routineContainer.removeAllViews();
        List<Routine> routines = dataManager.getRoutines();

        for (Routine routine : routines) {
            addRoutineView(routine);
        }
    }

    private void addRoutineView(Routine routine) {
        // 루틴 항목 레이아웃
        View routineView = getLayoutInflater().inflate(R.layout.item_routine, routineContainer, false);

        TextView routineName = routineView.findViewById(R.id.text_routine_name);
        TextView routineDetails = routineView.findViewById(R.id.text_routine_details);

        routineName.setText(routine.getName());
        routineDetails.setText(routine.getPeriodType() + " - 반복 " + routine.getRepeatCount() + "회");

        // 클릭 시 버튼 탭 추가
        routineView.setOnClickListener(v -> toggleButtonTab(routineView, routine));

        routineContainer.addView(routineView);
    }

    private void toggleButtonTab(View routineView, Routine routine) {
        // 기존 버튼 탭 제거
        if (currentButtonTab != null) {
            routineContainer.removeView(currentButtonTab);
            currentButtonTab = null;
        }

        // 버튼 탭 동적 추가
        View buttonTab = getLayoutInflater().inflate(R.layout.button_tab_routine, routineContainer, false);

        TextView buttonDetails = buttonTab.findViewById(R.id.button_details);
        TextView buttonDelete = buttonTab.findViewById(R.id.button_delete);

        // 버튼 이벤트 설정
        buttonDetails.setOnClickListener(v -> showDetails(routine));
        buttonDelete.setOnClickListener(v -> deleteRoutine(routine));

        // 버튼 탭을 루틴 항목 아래에 추가
        int index = routineContainer.indexOfChild(routineView);
        routineContainer.addView(buttonTab, index + 1);
        currentButtonTab = buttonTab;
    }

    private void showDetails(Routine routine) {
        // 상세보기 기능 구현
        Intent intent = new Intent(requireContext(), RoutineDetailsActivity.class);
        intent.putExtra("routine_id", routine.getId());
        startActivity(intent);
    }

    private void deleteRoutine(Routine routine) {
        // 루틴 삭제 처리
        List<Routine> routines = dataManager.getRoutines();
        routines.removeIf(r -> r.getId().equals(routine.getId()));
        dataManager.saveRoutines(routines);

        // 루틴에 연관된 Task 삭제
        dataManager.removeTasksByRoutineId(routine.getId());

        // UI 갱신
        loadRoutines();
    }

}

package com.example.miracledays.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.miracledays.R;
import com.example.miracledays.models.Routine;
import com.example.miracledays.utils.DataManager;

import java.util.List;

public class TaskFragment extends Fragment {

    private DataManager dataManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task, container, false);

        dataManager = new DataManager(requireContext());

        // 루틴 목록 불러오기
        List<Routine> routines = dataManager.getRoutines();

        // 루틴 데이터 표시 예시
        TextView textView = view.findViewById(R.id.text_routines);
        StringBuilder routineText = new StringBuilder();

        for (Routine routine : routines) {
            routineText.append("이름: ").append(routine.getName())
                    .append(" | 주기: ").append(routine.getPeriodValue())
                    .append("일마다\n");
        }

        textView.setText(routineText.toString());
        return view;
    }
}

package com.example.miracledays.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.miracledays.R;
import com.example.miracledays.utils.DataManager;

public class ManageFragment extends Fragment {

    private DataManager dataManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manage, container, false);

        dataManager = new DataManager(requireContext());

        Button resetButton = view.findViewById(R.id.button_reset);
        resetButton.setOnClickListener(v -> showResetConfirmationDialog());

        return view;
    }

    // 초기화 확인 팝업 표시
    private void showResetConfirmationDialog() {
        new androidx.appcompat.app.AlertDialog.Builder(requireContext())
                .setTitle("데이터 초기화")
                .setMessage("정말 모든 데이터를 초기화하시겠습니까? 이 작업은 되돌릴 수 없습니다.")
                .setPositiveButton("확인", (dialog, which) -> resetData())
                .setNegativeButton("취소", null)
                .show();
    }

    // 데이터 초기화 기능
    private void resetData() {
//        dataManager.saveRoutines(null); // 모든 데이터 삭제
        Toast.makeText(getContext(), "모든 데이터가 초기화되었습니다.", Toast.LENGTH_SHORT).show();
    }
}

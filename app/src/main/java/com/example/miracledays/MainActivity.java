package com.example.miracledays;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.miracledays.fragments.TaskFragment;
import com.example.miracledays.fragments.RoutineFragment;
import com.example.miracledays.fragments.ManageFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 초기 화면 설정
        if (savedInstanceState == null) {
            loadFragment(new TaskFragment());
        }

        // 버튼 설정
        Button btnTask = findViewById(R.id.btn_task);
        Button btnRoutine = findViewById(R.id.btn_routine);
        Button btnManage = findViewById(R.id.btn_manage);

        // 버튼 클릭 이벤트 처리
        btnTask.setOnClickListener(v -> loadFragment(new TaskFragment()));
        btnRoutine.setOnClickListener(v -> loadFragment(new RoutineFragment()));
        btnManage.setOnClickListener(v -> loadFragment(new ManageFragment()));
    }

    // Fragment 교체 메서드
    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}

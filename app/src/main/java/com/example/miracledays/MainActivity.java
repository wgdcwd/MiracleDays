package com.example.miracledays;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 초기 화면 설정
        if (savedInstanceState == null) {
            loadFragment(new TaskFragment());
        }

        // BottomNavigationView 설정
        BottomNavigationView navigationView = findViewById(R.id.navigation);
        navigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_task:
                    loadFragment(new TaskFragment());
                    return true;
                case R.id.navigation_routine:
                    loadFragment(new RoutineFragment());
                    return true;
                case R.id.navigation_manage:
                    loadFragment(new ManageFragment());
                    return true;
            }
            return false;
        });
    }

    // Fragment 전환 함수
    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}

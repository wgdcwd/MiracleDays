package com.example.miracledays;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.miracledays.models.Routine;
import com.example.miracledays.models.Task;
import com.example.miracledays.utils.DataManager;
import com.example.miracledays.utils.TaskGenerator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class RoutineAddActivity extends AppCompatActivity {

    private EditText routineNameInput;
    private RadioGroup periodRadioGroup;
    private LinearLayout daysOfWeekLayout, nDaysLayout;
    private CheckBox[] dayCheckboxes = new CheckBox[7];
    private Button nDaysMinus, nDaysPlus, repeatMinus, repeatPlus, confirmButton;
    private TextView nDaysText, repeatCountText;
    private int nDays = 1, repeatCount = 1;

    private DataManager dataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine_add);

        dataManager = new DataManager(this);
        setupViews();
        setupListeners();
    }

    private void setupViews() {
        routineNameInput = findViewById(R.id.edit_routine_name);
        periodRadioGroup = findViewById(R.id.radio_group_period);

        daysOfWeekLayout = findViewById(R.id.linear_days_of_week);
        nDaysLayout = findViewById(R.id.linear_n_days);

        dayCheckboxes[0] = findViewById(R.id.checkbox_monday);
        dayCheckboxes[1] = findViewById(R.id.checkbox_tuesday);
        dayCheckboxes[2] = findViewById(R.id.checkbox_wednesday);
        dayCheckboxes[3] = findViewById(R.id.checkbox_thursday);
        dayCheckboxes[4] = findViewById(R.id.checkbox_friday);
        dayCheckboxes[5] = findViewById(R.id.checkbox_saturday);
        dayCheckboxes[6] = findViewById(R.id.checkbox_sunday);

        nDaysMinus = findViewById(R.id.button_n_days_minus);
        nDaysPlus = findViewById(R.id.button_n_days_plus);
        nDaysText = findViewById(R.id.text_n_days);

        repeatMinus = findViewById(R.id.button_repeat_minus);
        repeatPlus = findViewById(R.id.button_repeat_plus);
        repeatCountText = findViewById(R.id.text_repeat_count);

        confirmButton = findViewById(R.id.button_confirm);
    }

    private void setupListeners() {
        // 주기 선택 리스너
        periodRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            daysOfWeekLayout.setVisibility(View.GONE);
            nDaysLayout.setVisibility(View.GONE);

            if (checkedId == R.id.radio_period_daily) {
                daysOfWeekLayout.setVisibility(View.VISIBLE);
            } else if (checkedId == R.id.radio_period_n_days) {
                nDaysLayout.setVisibility(View.VISIBLE);
            }
        });

        // n일마다 버튼 리스너
        nDaysMinus.setOnClickListener(v -> {
            if (nDays > 1) nDays--;
            nDaysText.setText(String.valueOf(nDays));
        });

        nDaysPlus.setOnClickListener(v -> {
            nDays++;
            nDaysText.setText(String.valueOf(nDays));
        });

        // 반복 횟수 버튼 리스너
        repeatMinus.setOnClickListener(v -> {
            if (repeatCount > 1) repeatCount--;
            repeatCountText.setText(String.valueOf(repeatCount));
        });

        repeatPlus.setOnClickListener(v -> {
            repeatCount++;
            repeatCountText.setText(String.valueOf(repeatCount));
        });

        // 확인 버튼 리스너
        confirmButton.setOnClickListener(v -> saveRoutine());
    }

    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date());
    }

    private void saveRoutine() {
        // 루틴 이름 검사
        String name = routineNameInput.getText().toString().trim();
        if (name.isEmpty()) {
            Toast.makeText(this, "루틴 이름을 입력하세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        // 주기 설정
        String periodType;
        int periodValue = nDays;

        int selectedPeriodId = periodRadioGroup.getCheckedRadioButtonId();
        if (selectedPeriodId == R.id.radio_period_daily) {
            periodType = "요일마다";
            periodValue = 1;
        } else if (selectedPeriodId == R.id.radio_period_n_days) {
            periodType = nDays + "일마다";
        } else if (selectedPeriodId == R.id.radio_period_weekly) {
            periodType = "주간";
            periodValue = 7;
        } else if (selectedPeriodId == R.id.radio_period_monthly) {
            periodType = "월간";
            periodValue = 30;
        } else {
            periodType = "연간";
            periodValue = 365;
        }

        // 루틴 생성
        Routine newRoutine = new Routine(
                UUID.randomUUID().toString(),
                name,
                periodType,
                periodValue,
                repeatCount
        );

        // Task 생성 및 저장
        List<Task> existingTasks = dataManager.getTasks();       // 현재 저장된 Task
        List<Task> completedTasks = dataManager.getCompletedTasks(); // 완료된 Task
        List<Task> newTasks = TaskGenerator.generateTasksFromRoutine(newRoutine, getCurrentDate(), existingTasks, completedTasks);

        // 기존 루틴과 연관된 Task 제거
        dataManager.removeTasksByRoutineId(newRoutine.getId()); // Task 삭제
        dataManager.saveTasks(newTasks); // 새로운 Task 저장

        // 루틴 저장
        List<Routine> routines = dataManager.getRoutines();
        routines.add(newRoutine);
        dataManager.saveRoutines(routines);

        Toast.makeText(this, "루틴이 추가되었습니다.", Toast.LENGTH_SHORT).show();
        finish();
    }

}

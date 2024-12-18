package com.example.miracledays.utils;

import com.example.miracledays.models.Routine;
import com.example.miracledays.models.Task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TaskGenerator {

    public static List<Task> generateTasksFromRoutine(Routine routine, String currentDate) {
        List<Task> tasks = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();

        try {
            Date startDate = sdf.parse(currentDate); // 현재 날짜 기준
            calendar.setTime(startDate);

            int periodValue = routine.getPeriodValue();
            int repeatCount = routine.getRepeatCount();

            // 주기를 무한 반복하면서 각각의 반복횟수에 맞게 Task를 생성
            for (int cycle = 0; cycle < 2; cycle++) { // 현재 주기 + 다음 주기
                // 주기의 시작 날짜 설정
                String startPeriod = sdf.format(calendar.getTime());

                // 주기의 종료 날짜 설정 (월간 or 연간일 경우 특별 처리)
                if (routine.getPeriodType().equals("월간")) {
                    calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                } else if (routine.getPeriodType().equals("연간")) {
                    calendar.set(Calendar.MONTH, Calendar.DECEMBER);
                    calendar.set(Calendar.DAY_OF_MONTH, 31);
                } else {
                    calendar.add(Calendar.DAY_OF_MONTH, periodValue - 1);
                }

                String endPeriod = sdf.format(calendar.getTime());
                String dueDate = startPeriod + " ~ " + endPeriod;

                // 반복횟수만큼 같은 주기의 Task를 생성
                for (int i = 0; i < repeatCount; i++) {
                    tasks.add(new Task(
                            routine.getId() + "_" + startPeriod + "_" + i,
                            routine.getId(),
                            routine.getName(),
                            dueDate
                    ));
                }

                // 다음 주기로 이동
                calendar.add(Calendar.DAY_OF_MONTH, 1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return tasks;
    }



}

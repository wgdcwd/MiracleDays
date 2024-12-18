package com.example.miracledays.utils;

import com.example.miracledays.models.Routine;
import com.example.miracledays.models.Task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TaskGenerator {

    public static List<Task> generateTasksFromRoutine(
            Routine routine, String currentDate, List<Task> existingTasks, List<Task> completedTasks) {

        List<Task> tasks = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();

        try {
            Date startDate = sdf.parse(currentDate);
            calendar.setTime(startDate);

            int periodValue = routine.getPeriodValue();
            int repeatCount = routine.getRepeatCount();

            for (int cycle = 0; cycle < 2; cycle++) { // 현재 주기 + 다음 주기
                String startPeriod = sdf.format(calendar.getTime());

                // 주기의 종료 날짜 설정
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

                // 반복 횟수만큼 Task 생성
                for (int i = 0; i < repeatCount; i++) {
                    String taskId = routine.getId() + "_" + startPeriod + "_" + i;

                    boolean isDuplicate = existingTasks.stream().anyMatch(t -> t.getId().equals(taskId)) ||
                            completedTasks.stream().anyMatch(t -> t.getId().equals(taskId));

                    if (!isDuplicate) {
                        tasks.add(new Task(taskId, routine.getId(), routine.getName(), dueDate));
                    }
                }

                calendar.add(Calendar.DAY_OF_MONTH, 1); // 다음 주기로 이동
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return tasks;
    }


}

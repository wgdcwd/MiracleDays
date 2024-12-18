package com.example.miracledays.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.miracledays.models.Routine;
import com.example.miracledays.models.Task;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataManager {

    private static final String PREFS_NAME = "miracle_days_prefs";
    private static final String ROUTINES_KEY = "routines_key";
    private static final String TASKS_KEY = "tasks_key";
    private static final String COMPLETED_TASKS_KEY = "completed_tasks_key";

    private final SharedPreferences sharedPreferences;
    private final Gson gson;

    public DataManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    // 루틴 가져오기
    public List<Routine> getRoutines() {
        String routinesJson = sharedPreferences.getString(ROUTINES_KEY, "");
        return routinesJson.isEmpty() ? new ArrayList<>() :
                gson.fromJson(routinesJson, new TypeToken<List<Routine>>() {}.getType());
    }

    // 루틴 저장
    public void saveRoutines(List<Routine> routines) {
        sharedPreferences.edit().putString(ROUTINES_KEY, gson.toJson(routines)).apply();
    }

    // Task 가져오기
    public List<Task> getTasks() {
        String tasksJson = sharedPreferences.getString(TASKS_KEY, "");
        return tasksJson.isEmpty() ? new ArrayList<>() :
                gson.fromJson(tasksJson, new TypeToken<List<Task>>() {}.getType());
    }

    // Task 저장
    public void saveTasks(List<Task> tasks) {
        sharedPreferences.edit().putString(TASKS_KEY, gson.toJson(tasks)).apply();
    }

    // 완료된 Task 가져오기
    public List<Task> getCompletedTasks() {
        String completedTasksJson = sharedPreferences.getString(COMPLETED_TASKS_KEY, "");
        return completedTasksJson.isEmpty() ? new ArrayList<>() :
                gson.fromJson(completedTasksJson, new TypeToken<List<Task>>() {}.getType());
    }

    // 완료된 Task 저장
    public void saveCompletedTasks(List<Task> completedTasks) {
        sharedPreferences.edit().putString(COMPLETED_TASKS_KEY, gson.toJson(completedTasks)).apply();
    }

    // Task 완료 처리
    public void completeTask(Task task) {
        List<Task> tasks = getTasks();
        tasks.removeIf(t -> t.getId().equals(task.getId()));
        saveTasks(tasks);

        List<Task> completedTasks = getCompletedTasks();
        completedTasks.add(task);
        saveCompletedTasks(completedTasks);
    }

    // 특정 루틴 ID의 Task 제거
    public void removeTasksByRoutineId(String routineId) {
        List<Task> tasks = getTasks();
        tasks.removeIf(task -> task.getRoutineId().equals(routineId));
        saveTasks(tasks);

        List<Task> completedTasks = getCompletedTasks();
        completedTasks.removeIf(task -> task.getRoutineId().equals(routineId));
        saveCompletedTasks(completedTasks);
    }

//    // 특정 루틴의 Task 갱신
//    public void updateTasksForRoutine(Routine routine) {
//        List<Task> tasks = getTasks();
//        tasks.removeIf(task -> task.getRoutineId().equals(routine.getId()));
//        tasks.addAll(TaskGenerator.generateTasksFromRoutine(routine, getCurrentDate()));
//        saveTasks(tasks);
//    }

    // 모든 루틴의 Task 갱신
    public void updateTasksForAllRoutines(List<Routine> routines) {
        List<Task> allTasks = new ArrayList<>();
        List<Task> existingTasks = getTasks();
        List<Task> completedTasks = getCompletedTasks();

        for (Routine routine : routines) {
            allTasks.addAll(TaskGenerator.generateTasksFromRoutine(routine, getCurrentDate(), existingTasks, completedTasks));
        }

        saveTasks(allTasks);
    }


    // 오래된 Task 제거
    public void cleanupOldTasks(String cutoffDate) {
        List<Task> tasks = getTasks();
        tasks.removeIf(task -> task.getDueDate().compareTo(cutoffDate) < 0);
        saveTasks(tasks);
    }

    // 유틸: 현재 날짜 반환
    private String getCurrentDate() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }
}

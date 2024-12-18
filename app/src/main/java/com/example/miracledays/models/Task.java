package com.example.miracledays.models;

import java.io.Serializable;

public class Task implements Serializable {
    private String id;         // 할 일 ID
    private String routineId;  // 연결된 루틴 ID
    private String name;       // 할 일 이름
    private String dueDate;    // 수행 기한 (yyyy-MM-dd)
    private boolean isCompleted; // 완료 여부

    public Task(String id, String routineId, String name, String dueDate) {
        this.id = id;
        this.routineId = routineId;
        this.name = name;
        this.dueDate = dueDate;
        this.isCompleted = false;
    }

    // Getters and Setters
    public String getId() { return id; }
    public String getRoutineId() { return routineId; }
    public String getName() { return name; }
    public String getDueDate() { return dueDate; }
    public boolean isCompleted() { return isCompleted; }

    public void setCompleted(boolean completed) { isCompleted = completed; }
}

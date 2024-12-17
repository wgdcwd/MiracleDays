package com.example.miracledays.models;

import java.util.ArrayList;
import java.util.List;

public class Routine {
    private String id;
    private String name;          // 루틴 이름
    private String periodType;    // 주기 타입 (요일마다, n일, 주간 등)
    private int periodValue;      // 주기 값 (n일마다의 n값)
    private int repeatCount;      // 반복 횟수
    private List<RoutineHistory> history;  // 수행 내역

    public Routine(String id, String name, String periodType, int periodValue, int repeatCount) {
        this.id = id;
        this.name = name;
        this.periodType = periodType;
        this.periodValue = periodValue;
        this.repeatCount = repeatCount;
        this.history = new ArrayList<>();
    }

    // Getters and Setters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getPeriodType() { return periodType; }
    public int getPeriodValue() { return periodValue; }
    public int getRepeatCount() { return repeatCount; }
    public List<RoutineHistory> getHistory() { return history; }

    public void addHistory(RoutineHistory record) {
        history.add(record);
    }
}

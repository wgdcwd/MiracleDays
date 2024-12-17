package com.example.miracledays.models;

import java.util.ArrayList;
import java.util.List;

public class Routine {
    private String id;                  // 고유 ID
    private String name;                // 루틴 이름
    private String periodType;          // 주기 타입 (요일마다, n일마다 등)
    private int periodValue;            // 주기 값 (예: 5일마다)
    private int repeatCount;            // 반복 횟수
    private List<RoutineHistory> history;  // 수행 내역

    public Routine(String id, String name, String periodType, int periodValue, int repeatCount) {
        this.id = id;
        this.name = name;
        this.periodType = periodType;
        this.periodValue = periodValue;
        this.repeatCount = repeatCount;
        this.history = new ArrayList<>();
    }

    // Getter 메서드
    public String getId() { return id; }
    public String getName() { return name; }
    public String getPeriodType() { return periodType; }
    public int getPeriodValue() { return periodValue; }
    public int getRepeatCount() { return repeatCount; }
    public List<RoutineHistory> getHistory() { return history; }

    // Setter 메서드
    public void setName(String name) { this.name = name; }
    public void setPeriodValue(int periodValue) { this.periodValue = periodValue; }
    public void setRepeatCount(int repeatCount) { this.repeatCount = repeatCount; }

    // History 관리 메서드
    public void addHistory(RoutineHistory record) {
        history.add(record);
    }

    public void clearHistory() {
        history.clear();
    }

    public void setHistory(List<RoutineHistory> history) {
        this.history = history;
    }
}

package com.example.miracledays.models;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Routine implements Serializable {
    private String id;                     // 루틴 ID
    private String name;                   // 루틴 이름
    private String periodType;             // 주기 타입 (요일마다, n일마다, 주간, 월간, 연간)
    private int periodValue;               // 주기 값 (n일마다의 n값)
    private int repeatCount;               // 반복 횟수
    private String startDate;              // 시작 날짜 (yyyy-MM-dd)
    private int totalAttempts;             // 총 시도 횟수
    private int successfulAttempts;        // 성공 횟수
    private List<RoutineHistory> history;  // 히스토리 목록

    // 생성자
    public Routine(String id, String name, String periodType, int periodValue, int repeatCount) {
        this.id = id;
        this.name = name;
        this.periodType = periodType;
        this.periodValue = periodValue;
        this.repeatCount = repeatCount;
        this.startDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        this.totalAttempts = 0;
        this.successfulAttempts = 0;
        this.history = new ArrayList<>();
    }

    // 히스토리 추가 메서드
    public void addHistory(RoutineHistory historyRecord) {
        if (history == null) history = new ArrayList<>();
        history.add(historyRecord);
    }

    // 성공 및 실패 기록
    public void addSuccess() {
        this.totalAttempts++;
        this.successfulAttempts++;
    }

    public void addFailure() {
        this.totalAttempts++;
    }

    // 수행률 계산
    public int getCompletionRate() {
        if (totalAttempts == 0) return 0;
        return (successfulAttempts * 100) / totalAttempts;
    }

    // Getter 메서드
    public String getId() { return id; }
    public String getName() { return name; }
    public String getPeriodType() { return periodType; }
    public int getPeriodValue() { return periodValue; }
    public int getRepeatCount() { return repeatCount; }
    public List<RoutineHistory> getHistory() { return history; }
    public String getStartDate() { return startDate; }

    // Setter 메서드 (수정 기능에 필요)
    public void setName(String name) { this.name = name; }
    public void setPeriodType(String periodType) { this.periodType = periodType; }
    public void setPeriodValue(int periodValue) { this.periodValue = periodValue; }
    public void setRepeatCount(int repeatCount) { this.repeatCount = repeatCount; }
}

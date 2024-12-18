package com.example.miracledays.models;

public class RoutineHistory {
    private String startDate;    // 시작 날짜
    private String endDate;      // 종료 날짜
    private String status;       // 상태 (성공, 실패, 대기 등)
    private String completedAt;  // 완료 시간

    public RoutineHistory(String startDate, String endDate, String status, String completedAt) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.completedAt = completedAt;
    }

    // Getters
    public String getStartDate() { return startDate; }
    public String getEndDate() { return endDate; }
    public String getStatus() { return status; }
    public String getCompletedAt() { return completedAt; }
}

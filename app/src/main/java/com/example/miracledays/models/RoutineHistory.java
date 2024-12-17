package com.example.miracledays.models;

public class RoutineHistory {
    private String startDate;    // 시작 날짜
    private String endDate;      // 종료 날짜
    private String status;       // 상태 (성공, 실패, 대기중)
    private String completedAt;  // 완료 시간

    public RoutineHistory(String startDate, String endDate, String status, String completedAt) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.completedAt = completedAt;
    }

    // Getter 메서드
    public String getStartDate() { return startDate; }
    public String getEndDate() { return endDate; }
    public String getStatus() { return status; }
    public String getCompletedAt() { return completedAt; }

    // Setter 메서드
    public void setStartDate(String startDate) { this.startDate = startDate; }
    public void setEndDate(String endDate) { this.endDate = endDate; }
    public void setStatus(String status) { this.status = status; }
    public void setCompletedAt(String completedAt) { this.completedAt = completedAt; }
}

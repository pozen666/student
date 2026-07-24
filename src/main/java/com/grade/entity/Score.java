package com.grade.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Score {
    private Integer id;
    private Integer studentId;
    private Integer courseId;
    private Double score;
    private LocalDate examDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private String studentName;
    private String studentNo;
    private String courseName;
    private String courseNo;

    public Score() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getStudentId() { return studentId; }
    public void setStudentId(Integer studentId) { this.studentId = studentId; }
    public Integer getCourseId() { return courseId; }
    public void setCourseId(Integer courseId) { this.courseId = courseId; }
    public Double getScore() { return score; }
    public void setScore(Double score) { this.score = score; }
    public LocalDate getExamDate() { return examDate; }
    public void setExamDate(LocalDate examDate) { this.examDate = examDate; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }
    public String getStudentNo() { return studentNo; }
    public void setStudentNo(String studentNo) { this.studentNo = studentNo; }
    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }
    public String getCourseNo() { return courseNo; }
    public void setCourseNo(String courseNo) { this.courseNo = courseNo; }

    @Override
    public String toString() {
        String scoreStr = (score == null) ? "未录入" : String.format("%.2f", score);
        return String.format("| %-10s | %-10s | %-20s | %-8s | %-12s |",
                studentNo, studentName, courseName, scoreStr, examDate == null ? "" : examDate.toString());
    }
}
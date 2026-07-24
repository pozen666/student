package com.grade.entity;

import java.time.LocalDateTime;

public class Course {
    private Integer id;
    private String courseNo;
    private String name;
    private String teacher;
    private Double credit;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Course() {}

    public Course(String courseNo, String name, String teacher, Double credit) {
        this.courseNo = courseNo;
        this.name = name;
        this.teacher = teacher;
        this.credit = credit;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getCourseNo() { return courseNo; }
    public void setCourseNo(String courseNo) { this.courseNo = courseNo; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getTeacher() { return teacher; }
    public void setTeacher(String teacher) { this.teacher = teacher; }
    public Double getCredit() { return credit; }
    public void setCredit(Double credit) { this.credit = credit; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public String toString() {
        return String.format("| %-8s | %-20s | %-10s | %-5s |",
                courseNo, name, teacher, credit);
    }
}
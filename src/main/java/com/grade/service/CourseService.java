package com.grade.service;

import com.grade.dao.CourseDAO;
import com.grade.entity.Course;

import java.util.List;

public class CourseService {
    private final CourseDAO courseDAO = new CourseDAO();

    public boolean addCourse(Course course) {
        if (courseDAO.findByCourseNo(course.getCourseNo()) != null) {
            System.out.println("课程编号已存在！");
            return false;
        }
        return courseDAO.insert(course) > 0;
    }

    public boolean updateCourse(Course course) {
        return courseDAO.update(course) > 0;
    }

    public boolean deleteCourse(String courseNo) {
        return courseDAO.delete(courseNo) > 0;
    }

    public Course findCourse(String courseNo) {
        return courseDAO.findByCourseNo(courseNo);
    }

    public List<Course> listAllCourses() {
        return courseDAO.findAll();
    }
}
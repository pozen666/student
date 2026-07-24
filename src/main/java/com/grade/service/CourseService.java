package com.grade.service;

import com.grade.dao.CourseDAO;
import com.grade.entity.Course;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    private final CourseDAO courseDAO;

    public CourseService(CourseDAO courseDAO) {
        this.courseDAO = courseDAO;
    }

    public boolean addCourse(Course course) {
        if (courseDAO.findByCourseNo(course.getCourseNo()) != null) {
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
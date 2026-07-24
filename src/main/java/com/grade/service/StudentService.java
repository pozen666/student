package com.grade.service;

import com.grade.dao.StudentDAO;
import com.grade.entity.Student;

import java.util.List;

public class StudentService {
    private final StudentDAO studentDAO = new StudentDAO();

    public boolean addStudent(Student student) {
        if (studentDAO.findByStudentNo(student.getStudentNo()) != null) {
            System.out.println("学号已存在！");
            return false;
        }
        return studentDAO.insert(student) > 0;
    }

    public boolean updateStudent(Student student) {
        return studentDAO.update(student) > 0;
    }

    public boolean deleteStudent(String studentNo) {
        return studentDAO.delete(studentNo) > 0;
    }

    public Student findStudent(String studentNo) {
        return studentDAO.findByStudentNo(studentNo);
    }

    public List<Student> listAllStudents() {
        return studentDAO.findAll();
    }

    public List<Student> searchStudents(String keyword) {
        return studentDAO.search(keyword);
    }
}
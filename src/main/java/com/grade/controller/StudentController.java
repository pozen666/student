package com.grade.controller;

import com.grade.entity.Student;
import com.grade.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public String list(@RequestParam(required = false) String keyword, Model model) {
        if (keyword != null && !keyword.isEmpty()) {
            model.addAttribute("students", studentService.searchStudents(keyword));
            model.addAttribute("keyword", keyword);
        } else {
            model.addAttribute("students", studentService.listAllStudents());
        }
        return "student/list";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("student", new Student());
        return "student/form";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute Student student, Model model) {
        if (!studentService.addStudent(student)) {
            model.addAttribute("error", "学号已存在！");
            model.addAttribute("student", student);
            return "student/form";
        }
        return "redirect:/students";
    }

    @GetMapping("/edit/{studentNo}")
    public String editForm(@PathVariable String studentNo, Model model) {
        Student student = studentService.findStudent(studentNo);
        if (student == null) return "redirect:/students";
        model.addAttribute("student", student);
        return "student/form";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute Student student) {
        studentService.updateStudent(student);
        return "redirect:/students";
    }

    @GetMapping("/delete/{studentNo}")
    public String delete(@PathVariable String studentNo) {
        studentService.deleteStudent(studentNo);
        return "redirect:/students";
    }
}
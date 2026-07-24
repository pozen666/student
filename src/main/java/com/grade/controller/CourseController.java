package com.grade.controller;

import com.grade.entity.Course;
import com.grade.service.CourseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/courses")
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("courses", courseService.listAllCourses());
        return "course/list";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("course", new Course());
        return "course/form";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute Course course, Model model) {
        if (!courseService.addCourse(course)) {
            model.addAttribute("error", "课程编号已存在！");
            model.addAttribute("course", course);
            return "course/form";
        }
        return "redirect:/courses";
    }

    @GetMapping("/edit/{courseNo}")
    public String editForm(@PathVariable String courseNo, Model model) {
        Course course = courseService.findCourse(courseNo);
        if (course == null) return "redirect:/courses";
        model.addAttribute("course", course);
        return "course/form";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute Course course) {
        courseService.updateCourse(course);
        return "redirect:/courses";
    }

    @GetMapping("/delete/{courseNo}")
    public String delete(@PathVariable String courseNo) {
        courseService.deleteCourse(courseNo);
        return "redirect:/courses";
    }
}
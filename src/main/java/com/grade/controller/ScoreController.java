package com.grade.controller;

import com.grade.entity.Course;
import com.grade.entity.Score;
import com.grade.entity.Student;
import com.grade.service.CourseService;
import com.grade.service.ScoreService;
import com.grade.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/scores")
public class ScoreController {
    private final ScoreService scoreService;
    private final StudentService studentService;
    private final CourseService courseService;

    public ScoreController(ScoreService scoreService, StudentService studentService, CourseService courseService) {
        this.scoreService = scoreService;
        this.studentService = studentService;
        this.courseService = courseService;
    }

    @GetMapping
    public String list(@RequestParam(required = false) String studentNo,
                       @RequestParam(required = false) String courseNo,
                       Model model) {
        List<Score> scores;
        if (studentNo != null && !studentNo.isEmpty()) {
            scores = scoreService.findScoreByStudent(studentNo);
            model.addAttribute("filterStudent", studentNo);
        } else if (courseNo != null && !courseNo.isEmpty()) {
            scores = scoreService.findScoreByCourse(courseNo);
            model.addAttribute("filterCourse", courseNo);
        } else {
            scores = scoreService.listAllScores();
        }
        model.addAttribute("scores", scores);
        model.addAttribute("students", studentService.listAllStudents());
        model.addAttribute("courses", courseService.listAllCourses());
        return "score/list";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("score", new Score());
        model.addAttribute("students", studentService.listAllStudents());
        model.addAttribute("courses", courseService.listAllCourses());
        return "score/form";
    }

    @PostMapping("/add")
    public String add(@RequestParam String studentNo,
                      @RequestParam String courseNo,
                      @RequestParam(required = false) Double scoreVal,
                      @RequestParam(required = false) String examDate) {
        Student student = studentService.findStudent(studentNo);
        Course course = courseService.findCourse(courseNo);
        if (student == null || course == null) return "redirect:/scores";

        Score score = new Score();
        score.setStudentId(student.getId());
        score.setCourseId(course.getId());
        score.setScore(scoreVal);
        if (examDate != null && !examDate.isEmpty()) score.setExamDate(LocalDate.parse(examDate));
        scoreService.addScore(score);
        return "redirect:/scores";
    }

    @GetMapping("/edit")
    public String editForm(@RequestParam String studentNo, @RequestParam String courseNo, Model model) {
        List<Score> list = scoreService.findScoreByStudent(studentNo);
        Score score = list.stream()
                .filter(s -> s.getCourseNo().equals(courseNo))
                .findFirst().orElse(null);
        if (score == null) return "redirect:/scores";
        model.addAttribute("score", score);
        model.addAttribute("students", studentService.listAllStudents());
        model.addAttribute("courses", courseService.listAllCourses());
        return "score/form";
    }

    @PostMapping("/edit")
    public String edit(@RequestParam String studentNo,
                       @RequestParam String courseNo,
                       @RequestParam(required = false) Double scoreVal,
                       @RequestParam(required = false) String examDate) {
        Student student = studentService.findStudent(studentNo);
        Course course = courseService.findCourse(courseNo);
        if (student == null || course == null) return "redirect:/scores";

        Score score = new Score();
        score.setStudentId(student.getId());
        score.setCourseId(course.getId());
        score.setScore(scoreVal);
        if (examDate != null && !examDate.isEmpty()) score.setExamDate(LocalDate.parse(examDate));
        scoreService.updateScore(score);
        return "redirect:/scores";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam String studentNo, @RequestParam String courseNo) {
        Student student = studentService.findStudent(studentNo);
        Course course = courseService.findCourse(courseNo);
        if (student != null && course != null) {
            scoreService.deleteScore(student.getId(), course.getId());
        }
        return "redirect:/scores";
    }
}
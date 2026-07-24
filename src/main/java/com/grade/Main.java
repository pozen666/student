package com.grade;

import com.grade.entity.Course;
import com.grade.entity.Score;
import com.grade.entity.Student;
import com.grade.service.CourseService;
import com.grade.service.ScoreService;
import com.grade.service.StudentService;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final StudentService studentService = new StudentService();
    private static final CourseService courseService = new CourseService();
    private static final ScoreService scoreService = new ScoreService();

    public static void main(String[] args) {
        while (true) {
            showMainMenu();
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> studentMenu();
                case "2" -> courseMenu();
                case "3" -> scoreMenu();
                case "4" -> {
                    System.out.println("感谢使用，再见！");
                    System.exit(0);
                }
                default -> System.out.println("输入有误，请重新选择！");
            }
        }
    }

    private static void showMainMenu() {
        System.out.println("\n==================================");
        System.out.println("       学生成绩管理系统");
        System.out.println("==================================");
        System.out.println(" 1. 学生管理");
        System.out.println(" 2. 课程管理");
        System.out.println(" 3. 成绩管理");
        System.out.println(" 4. 退出系统");
        System.out.println("==================================");
        System.out.print("请选择功能：");
    }

    // ===================== 学生管理 =====================
    private static void studentMenu() {
        while (true) {
            System.out.println("\n--- 学生管理 ---");
            System.out.println(" 1. 添加学生");
            System.out.println(" 2. 修改学生");
            System.out.println(" 3. 删除学生");
            System.out.println(" 4. 查看单个学生");
            System.out.println(" 5. 查看全部学生");
            System.out.println(" 6. 搜索学生");
            System.out.println(" 7. 返回主菜单");
            System.out.print("请选择：");
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> addStudent();
                case "2" -> updateStudent();
                case "3" -> deleteStudent();
                case "4" -> viewStudent();
                case "5" -> listStudents();
                case "6" -> searchStudents();
                case "7" -> { return; }
                default -> System.out.println("输入有误！");
            }
        }
    }

    private static void addStudent() {
        System.out.print("学号："); String no = scanner.nextLine().trim();
        System.out.print("姓名："); String name = scanner.nextLine().trim();
        System.out.print("性别(男/女)："); String gender = scanner.nextLine().trim();
        System.out.print("班级："); String cls = scanner.nextLine().trim();
        System.out.print("电话："); String phone = scanner.nextLine().trim();
        Student s = new Student(no, name, gender, cls, phone);
        if (studentService.addStudent(s)) {
            System.out.println("添加成功！");
        }
    }

    private static void updateStudent() {
        System.out.print("请输入要修改的学号：");
        String no = scanner.nextLine().trim();
        Student s = studentService.findStudent(no);
        if (s == null) {
            System.out.println("学生不存在！");
            return;
        }
        System.out.print("姓名(" + s.getName() + ")：");
        String name = scanner.nextLine().trim();
        if (!name.isEmpty()) s.setName(name);
        System.out.print("性别(" + s.getGender() + ")：");
        String gender = scanner.nextLine().trim();
        if (!gender.isEmpty()) s.setGender(gender);
        System.out.print("班级(" + s.getClassName() + ")：");
        String cls = scanner.nextLine().trim();
        if (!cls.isEmpty()) s.setClassName(cls);
        System.out.print("电话(" + s.getPhone() + ")：");
        String phone = scanner.nextLine().trim();
        if (!phone.isEmpty()) s.setPhone(phone);
        if (studentService.updateStudent(s)) {
            System.out.println("修改成功！");
        }
    }

    private static void deleteStudent() {
        System.out.print("请输入要删除的学号：");
        String no = scanner.nextLine().trim();
        if (studentService.deleteStudent(no)) {
            System.out.println("删除成功！");
        } else {
            System.out.println("删除失败，学号不存在！");
        }
    }

    private static void viewStudent() {
        System.out.print("请输入学号：");
        String no = scanner.nextLine().trim();
        Student s = studentService.findStudent(no);
        if (s == null) {
            System.out.println("学生不存在！");
            return;
        }
        printStudentHeader();
        System.out.println(s);
    }

    private static void listStudents() {
        List<Student> list = studentService.listAllStudents();
        if (list.isEmpty()) {
            System.out.println("暂无学生数据！");
            return;
        }
        printStudentHeader();
        list.forEach(System.out::println);
    }

    private static void searchStudents() {
        System.out.print("请输入搜索关键字(学号/姓名/班级)：");
        String keyword = scanner.nextLine().trim();
        List<Student> list = studentService.searchStudents(keyword);
        if (list.isEmpty()) {
            System.out.println("未找到匹配的学生！");
            return;
        }
        printStudentHeader();
        list.forEach(System.out::println);
    }

    private static void printStudentHeader() {
        System.out.println("+----------+------------+------+------------------+-----------------+");
        System.out.println("| 学号     | 姓名       | 性别 | 班级             | 电话            |");
        System.out.println("+----------+------------+------+------------------+-----------------+");
    }

    // ===================== 课程管理 =====================
    private static void courseMenu() {
        while (true) {
            System.out.println("\n--- 课程管理 ---");
            System.out.println(" 1. 添加课程");
            System.out.println(" 2. 修改课程");
            System.out.println(" 3. 删除课程");
            System.out.println(" 4. 查看全部课程");
            System.out.println(" 5. 返回主菜单");
            System.out.print("请选择：");
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> addCourse();
                case "2" -> updateCourse();
                case "3" -> deleteCourse();
                case "4" -> listCourses();
                case "5" -> { return; }
                default -> System.out.println("输入有误！");
            }
        }
    }

    private static void addCourse() {
        System.out.print("课程编号："); String no = scanner.nextLine().trim();
        System.out.print("课程名称："); String name = scanner.nextLine().trim();
        System.out.print("授课教师："); String teacher = scanner.nextLine().trim();
        System.out.print("学分："); double credit = Double.parseDouble(scanner.nextLine().trim());
        Course c = new Course(no, name, teacher, credit);
        if (courseService.addCourse(c)) {
            System.out.println("添加成功！");
        }
    }

    private static void updateCourse() {
        System.out.print("请输入要修改的课程编号：");
        String no = scanner.nextLine().trim();
        Course c = courseService.findCourse(no);
        if (c == null) {
            System.out.println("课程不存在！");
            return;
        }
        System.out.print("课程名称(" + c.getName() + ")：");
        String name = scanner.nextLine().trim();
        if (!name.isEmpty()) c.setName(name);
        System.out.print("授课教师(" + c.getTeacher() + ")：");
        String teacher = scanner.nextLine().trim();
        if (!teacher.isEmpty()) c.setTeacher(teacher);
        System.out.print("学分(" + c.getCredit() + ")：");
        String credit = scanner.nextLine().trim();
        if (!credit.isEmpty()) c.setCredit(Double.parseDouble(credit));
        if (courseService.updateCourse(c)) {
            System.out.println("修改成功！");
        }
    }

    private static void deleteCourse() {
        System.out.print("请输入要删除的课程编号：");
        String no = scanner.nextLine().trim();
        if (courseService.deleteCourse(no)) {
            System.out.println("删除成功！");
        } else {
            System.out.println("删除失败，课程编号不存在！");
        }
    }

    private static void listCourses() {
        List<Course> list = courseService.listAllCourses();
        if (list.isEmpty()) {
            System.out.println("暂无课程数据！");
            return;
        }
        System.out.println("+----------+----------------------+------------+-------+");
        System.out.println("| 课程编号 | 课程名称             | 授课教师   | 学分  |");
        System.out.println("+----------+----------------------+------------+-------+");
        list.forEach(System.out::println);
    }

    // ===================== 成绩管理 =====================
    private static void scoreMenu() {
        while (true) {
            System.out.println("\n--- 成绩管理 ---");
            System.out.println(" 1. 录入成绩");
            System.out.println(" 2. 修改成绩");
            System.out.println(" 3. 删除成绩");
            System.out.println(" 4. 查看某学生全部成绩");
            System.out.println(" 5. 查看某课程全部成绩");
            System.out.println(" 6. 查看全部成绩");
            System.out.println(" 7. 返回主菜单");
            System.out.print("请选择：");
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> addScore();
                case "2" -> updateScore();
                case "3" -> deleteScore();
                case "4" -> queryScoreByStudent();
                case "5" -> queryScoreByCourse();
                case "6" -> listAllScores();
                case "7" -> { return; }
                default -> System.out.println("输入有误！");
            }
        }
    }

    private static void addScore() {
        System.out.print("学号："); String studentNo = scanner.nextLine().trim();
        Student s = studentService.findStudent(studentNo);
        if (s == null) { System.out.println("学生不存在！"); return; }
        System.out.print("课程编号："); String courseNo = scanner.nextLine().trim();
        Course c = courseService.findCourse(courseNo);
        if (c == null) { System.out.println("课程不存在！"); return; }
        System.out.print("成绩："); double scoreVal = Double.parseDouble(scanner.nextLine().trim());
        System.out.print("考试日期(yyyy-MM-dd)："); String dateStr = scanner.nextLine().trim();

        Score sc = new Score();
        sc.setStudentId(s.getId());
        sc.setCourseId(c.getId());
        sc.setScore(scoreVal);
        if (!dateStr.isEmpty()) sc.setExamDate(LocalDate.parse(dateStr));
        if (scoreService.addScore(sc)) {
            System.out.println("成绩录入成功！");
        } else {
            System.out.println("录入失败（可能该学生该课程已有成绩，请使用修改功能）");
        }
    }

    private static void updateScore() {
        System.out.print("学号："); String studentNo = scanner.nextLine().trim();
        Student s = studentService.findStudent(studentNo);
        if (s == null) { System.out.println("学生不存在！"); return; }
        System.out.print("课程编号："); String courseNo = scanner.nextLine().trim();
        Course c = courseService.findCourse(courseNo);
        if (c == null) { System.out.println("课程不存在！"); return; }
        System.out.print("新成绩："); double scoreVal = Double.parseDouble(scanner.nextLine().trim());
        System.out.print("考试日期(yyyy-MM-dd)："); String dateStr = scanner.nextLine().trim();

        Score sc = new Score();
        sc.setStudentId(s.getId());
        sc.setCourseId(c.getId());
        sc.setScore(scoreVal);
        if (!dateStr.isEmpty()) sc.setExamDate(LocalDate.parse(dateStr));
        if (scoreService.updateScore(sc)) {
            System.out.println("修改成功！");
        } else {
            System.out.println("修改失败，成绩记录不存在！");
        }
    }

    private static void deleteScore() {
        System.out.print("学号："); String studentNo = scanner.nextLine().trim();
        Student s = studentService.findStudent(studentNo);
        if (s == null) { System.out.println("学生不存在！"); return; }
        System.out.print("课程编号："); String courseNo = scanner.nextLine().trim();
        Course c = courseService.findCourse(courseNo);
        if (c == null) { System.out.println("课程不存在！"); return; }
        if (scoreService.deleteScore(s.getId(), c.getId())) {
            System.out.println("删除成功！");
        } else {
            System.out.println("删除失败！");
        }
    }

    private static void queryScoreByStudent() {
        System.out.print("请输入学号：");
        String no = scanner.nextLine().trim();
        List<Score> list = scoreService.findScoreByStudent(no);
        if (list.isEmpty()) {
            System.out.println("该学生暂无成绩记录！");
            return;
        }
        printScoreHeader();
        list.forEach(System.out::println);
    }

    private static void queryScoreByCourse() {
        System.out.print("请输入课程编号：");
        String no = scanner.nextLine().trim();
        List<Score> list = scoreService.findScoreByCourse(no);
        if (list.isEmpty()) {
            System.out.println("该课程暂无成绩记录！");
            return;
        }
        printScoreHeader();
        list.forEach(System.out::println);
    }

    private static void listAllScores() {
        List<Score> list = scoreService.listAllScores();
        if (list.isEmpty()) {
            System.out.println("暂无成绩数据！");
            return;
        }
        printScoreHeader();
        list.forEach(System.out::println);
    }

    private static void printScoreHeader() {
        System.out.println("+------------+------------+----------------------+----------+--------------+");
        System.out.println("| 学号       | 姓名       | 课程名称             | 成绩     | 考试日期    |");
        System.out.println("+------------+------------+----------------------+----------+--------------+");
    }
}
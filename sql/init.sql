CREATE DATABASE IF NOT EXISTS student_grade_manager DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE student_grade_manager;

CREATE TABLE IF NOT EXISTS student (
    id INT AUTO_INCREMENT PRIMARY KEY,
    student_no VARCHAR(20) NOT NULL UNIQUE COMMENT '学号',
    name VARCHAR(50) NOT NULL COMMENT '姓名',
    gender ENUM('男', '女') NOT NULL DEFAULT '男' COMMENT '性别',
    class_name VARCHAR(50) DEFAULT '' COMMENT '班级',
    phone VARCHAR(20) DEFAULT '' COMMENT '联系电话',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS course (
    id INT AUTO_INCREMENT PRIMARY KEY,
    course_no VARCHAR(20) NOT NULL UNIQUE COMMENT '课程编号',
    name VARCHAR(100) NOT NULL COMMENT '课程名称',
    teacher VARCHAR(50) DEFAULT '' COMMENT '授课教师',
    credit DECIMAL(3,1) DEFAULT 0.0 COMMENT '学分',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS score (
    id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT NOT NULL COMMENT '学生ID',
    course_id INT NOT NULL COMMENT '课程ID',
    score DECIMAL(5,2) DEFAULT NULL COMMENT '成绩',
    exam_date DATE DEFAULT NULL COMMENT '考试日期',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (student_id) REFERENCES student(id) ON DELETE CASCADE,
    FOREIGN KEY (course_id) REFERENCES course(id) ON DELETE CASCADE,
    UNIQUE KEY uk_stu_course (student_id, course_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 插入测试数据
INSERT INTO student (student_no, name, gender, class_name) VALUES
('2024001', '张三', '男', '计算机一班'),
('2024002', '李四', '女', '计算机一班'),
('2024003', '王五', '男', '软件工程二班');

INSERT INTO course (course_no, name, teacher, credit) VALUES
('C001', 'Java程序设计', '张教授', 4.0),
('C002', '数据结构', '李教授', 3.5),
('C003', '数据库原理', '王教授', 3.0);

INSERT INTO score (student_id, course_id, score, exam_date) VALUES
(1, 1, 88.5, '2025-01-15'),
(1, 2, 92.0, '2025-01-18'),
(2, 1, 76.0, '2025-01-15'),
(2, 3, 81.5, '2025-01-20'),
(3, 2, 95.0, '2025-01-18'),
(3, 3, 67.0, '2025-01-20');
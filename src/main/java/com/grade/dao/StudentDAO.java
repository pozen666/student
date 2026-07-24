package com.grade.dao;

import com.grade.entity.Student;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

@Repository
public class StudentDAO {
    private final JdbcTemplate jdbc;

    public StudentDAO(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private final RowMapper<Student> rowMapper = (ResultSet rs, int row) -> {
        Student s = new Student();
        s.setId(rs.getInt("id"));
        s.setStudentNo(rs.getString("student_no"));
        s.setName(rs.getString("name"));
        s.setGender(rs.getString("gender"));
        s.setClassName(rs.getString("class_name"));
        s.setPhone(rs.getString("phone"));
        return s;
    };

    public int insert(Student student) {
        String sql = "INSERT INTO student (student_no, name, gender, class_name, phone) VALUES (?,?,?,?,?)";
        KeyHolder kh = new GeneratedKeyHolder();
        jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, student.getStudentNo());
            ps.setString(2, student.getName());
            ps.setString(3, student.getGender());
            ps.setString(4, student.getClassName());
            ps.setString(5, student.getPhone());
            return ps;
        }, kh);
        Number key = kh.getKey();
        return key != null ? key.intValue() : 0;
    }

    public int update(Student student) {
        String sql = "UPDATE student SET name=?, gender=?, class_name=?, phone=? WHERE student_no=?";
        return jdbc.update(sql, student.getName(), student.getGender(),
                student.getClassName(), student.getPhone(), student.getStudentNo());
    }

    public int delete(String studentNo) {
        return jdbc.update("DELETE FROM student WHERE student_no=?", studentNo);
    }

    public Student findByStudentNo(String studentNo) {
        List<Student> list = jdbc.query("SELECT * FROM student WHERE student_no=?", rowMapper, studentNo);
        return list.isEmpty() ? null : list.get(0);
    }

    public List<Student> findAll() {
        return jdbc.query("SELECT * FROM student ORDER BY student_no", rowMapper);
    }

    public List<Student> search(String keyword) {
        String like = "%" + keyword + "%";
        String sql = "SELECT * FROM student WHERE name LIKE ? OR student_no LIKE ? OR class_name LIKE ? ORDER BY student_no";
        return jdbc.query(sql, rowMapper, like, like, like);
    }
}
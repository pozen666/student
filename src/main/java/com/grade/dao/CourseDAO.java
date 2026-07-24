package com.grade.dao;

import com.grade.entity.Course;
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
public class CourseDAO {
    private final JdbcTemplate jdbc;

    public CourseDAO(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private final RowMapper<Course> rowMapper = (ResultSet rs, int row) -> {
        Course c = new Course();
        c.setId(rs.getInt("id"));
        c.setCourseNo(rs.getString("course_no"));
        c.setName(rs.getString("name"));
        c.setTeacher(rs.getString("teacher"));
        c.setCredit(rs.getDouble("credit"));
        return c;
    };

    public int insert(Course course) {
        String sql = "INSERT INTO course (course_no, name, teacher, credit) VALUES (?,?,?,?)";
        KeyHolder kh = new GeneratedKeyHolder();
        jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, course.getCourseNo());
            ps.setString(2, course.getName());
            ps.setString(3, course.getTeacher());
            ps.setDouble(4, course.getCredit());
            return ps;
        }, kh);
        Number key = kh.getKey();
        return key != null ? key.intValue() : 0;
    }

    public int update(Course course) {
        String sql = "UPDATE course SET name=?, teacher=?, credit=? WHERE course_no=?";
        return jdbc.update(sql, course.getName(), course.getTeacher(), course.getCredit(), course.getCourseNo());
    }

    public int delete(String courseNo) {
        return jdbc.update("DELETE FROM course WHERE course_no=?", courseNo);
    }

    public Course findByCourseNo(String courseNo) {
        List<Course> list = jdbc.query("SELECT * FROM course WHERE course_no=?", rowMapper, courseNo);
        return list.isEmpty() ? null : list.get(0);
    }

    public List<Course> findAll() {
        return jdbc.query("SELECT * FROM course ORDER BY course_no", rowMapper);
    }
}
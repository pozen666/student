package com.grade.dao;

import com.grade.entity.Score;
import com.grade.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ScoreDAO {

    public int insert(Score score) {
        String sql = "INSERT INTO score (student_id, course_id, score, exam_date) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, score.getStudentId());
            ps.setInt(2, score.getCourseId());
            if (score.getScore() != null) {
                ps.setDouble(3, score.getScore());
            } else {
                ps.setNull(3, Types.DECIMAL);
            }
            if (score.getExamDate() != null) {
                ps.setDate(4, Date.valueOf(score.getExamDate()));
            } else {
                ps.setNull(4, Types.DATE);
            }
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int update(Score score) {
        String sql = "UPDATE score SET score=?, exam_date=? WHERE student_id=? AND course_id=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            if (score.getScore() != null) {
                ps.setDouble(1, score.getScore());
            } else {
                ps.setNull(1, Types.DECIMAL);
            }
            if (score.getExamDate() != null) {
                ps.setDate(2, Date.valueOf(score.getExamDate()));
            } else {
                ps.setNull(2, Types.DATE);
            }
            ps.setInt(3, score.getStudentId());
            ps.setInt(4, score.getCourseId());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int delete(int studentId, int courseId) {
        String sql = "DELETE FROM score WHERE student_id=? AND course_id=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            ps.setInt(2, courseId);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Score> findByStudentNo(String studentNo) {
        List<Score> list = new ArrayList<>();
        String sql = "SELECT sc.*, s.student_no, s.name AS student_name, c.course_no, c.name AS course_name " +
                "FROM score sc " +
                "JOIN student s ON sc.student_id = s.id " +
                "JOIN course c ON sc.course_id = c.id " +
                "WHERE s.student_no = ? " +
                "ORDER BY c.course_no";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, studentNo);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Score> findByCourseNo(String courseNo) {
        List<Score> list = new ArrayList<>();
        String sql = "SELECT sc.*, s.student_no, s.name AS student_name, c.course_no, c.name AS course_name " +
                "FROM score sc " +
                "JOIN student s ON sc.student_id = s.id " +
                "JOIN course c ON sc.course_id = c.id " +
                "WHERE c.course_no = ? " +
                "ORDER BY s.student_no";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, courseNo);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Score> findAll() {
        List<Score> list = new ArrayList<>();
        String sql = "SELECT sc.*, s.student_no, s.name AS student_name, c.course_no, c.name AS course_name " +
                "FROM score sc " +
                "JOIN student s ON sc.student_id = s.id " +
                "JOIN course c ON sc.course_id = c.id " +
                "ORDER BY s.student_no, c.course_no";
        try (Connection conn = DBUtil.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private Score mapRow(ResultSet rs) throws SQLException {
        Score sc = new Score();
        sc.setId(rs.getInt("id"));
        sc.setStudentId(rs.getInt("student_id"));
        sc.setCourseId(rs.getInt("course_id"));
        double scoreVal = rs.getDouble("score");
        if (!rs.wasNull()) sc.setScore(scoreVal);
        Date examDate = rs.getDate("exam_date");
        if (examDate != null) sc.setExamDate(examDate.toLocalDate());
        sc.setStudentNo(rs.getString("student_no"));
        sc.setStudentName(rs.getString("student_name"));
        sc.setCourseNo(rs.getString("course_no"));
        sc.setCourseName(rs.getString("course_name"));
        return sc;
    }
}
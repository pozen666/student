package com.grade.dao;

import com.grade.entity.Score;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

@Repository
public class ScoreDAO {
    private final JdbcTemplate jdbc;

    public ScoreDAO(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private final RowMapper<Score> rowMapper = (ResultSet rs, int row) -> {
        Score s = new Score();
        s.setId(rs.getInt("id"));
        s.setStudentId(rs.getInt("student_id"));
        s.setCourseId(rs.getInt("course_id"));
        double v = rs.getDouble("score");
        if (!rs.wasNull()) s.setScore(v);
        Date d = rs.getDate("exam_date");
        if (d != null) s.setExamDate(d.toLocalDate());
        s.setStudentNo(rs.getString("student_no"));
        s.setStudentName(rs.getString("student_name"));
        s.setCourseNo(rs.getString("course_no"));
        s.setCourseName(rs.getString("course_name"));
        return s;
    };

    private String joinSql = "SELECT sc.*, s.student_no, s.name student_name, c.course_no, c.name course_name " +
            "FROM score sc JOIN student s ON sc.student_id = s.id JOIN course c ON sc.course_id = c.id ";

    public int insert(Score score) {
        String sql = "INSERT INTO score (student_id, course_id, score, exam_date) VALUES (?,?,?,?)";
        KeyHolder kh = new GeneratedKeyHolder();
        jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, score.getStudentId());
            ps.setInt(2, score.getCourseId());
            if (score.getScore() != null) ps.setDouble(3, score.getScore());
            else ps.setNull(3, java.sql.Types.DECIMAL);
            if (score.getExamDate() != null) ps.setDate(4, Date.valueOf(score.getExamDate()));
            else ps.setNull(4, java.sql.Types.DATE);
            return ps;
        }, kh);
        Number key = kh.getKey();
        return key != null ? key.intValue() : 0;
    }

    public int update(Score score) {
        String sql = "UPDATE score SET score=?, exam_date=? WHERE student_id=? AND course_id=?";
        Object scoreVal = score.getScore() != null ? score.getScore() : null;
        Object dateVal = score.getExamDate() != null ? Date.valueOf(score.getExamDate()) : null;
        return jdbc.update(sql, scoreVal, dateVal, score.getStudentId(), score.getCourseId());
    }

    public int delete(int studentId, int courseId) {
        return jdbc.update("DELETE FROM score WHERE student_id=? AND course_id=?", studentId, courseId);
    }

    public List<Score> findByStudentNo(String studentNo) {
        return jdbc.query(joinSql + "WHERE s.student_no=? ORDER BY c.course_no", rowMapper, studentNo);
    }

    public List<Score> findByCourseNo(String courseNo) {
        return jdbc.query(joinSql + "WHERE c.course_no=? ORDER BY s.student_no", rowMapper, courseNo);
    }

    public List<Score> findAll() {
        return jdbc.query(joinSql + "ORDER BY s.student_no, c.course_no", rowMapper);
    }
}
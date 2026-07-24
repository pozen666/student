package com.grade.service;

import com.grade.dao.ScoreDAO;
import com.grade.entity.Score;

import java.util.List;

public class ScoreService {
    private final ScoreDAO scoreDAO = new ScoreDAO();

    public boolean addScore(Score score) {
        return scoreDAO.insert(score) > 0;
    }

    public boolean updateScore(Score score) {
        return scoreDAO.update(score) > 0;
    }

    public boolean deleteScore(int studentId, int courseId) {
        return scoreDAO.delete(studentId, courseId) > 0;
    }

    public List<Score> findScoreByStudent(String studentNo) {
        return scoreDAO.findByStudentNo(studentNo);
    }

    public List<Score> findScoreByCourse(String courseNo) {
        return scoreDAO.findByCourseNo(courseNo);
    }

    public List<Score> listAllScores() {
        return scoreDAO.findAll();
    }
}
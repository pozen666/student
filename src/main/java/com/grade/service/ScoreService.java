package com.grade.service;

import com.grade.dao.ScoreDAO;
import com.grade.entity.Score;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoreService {
    private final ScoreDAO scoreDAO;

    public ScoreService(ScoreDAO scoreDAO) {
        this.scoreDAO = scoreDAO;
    }

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
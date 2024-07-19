package com.spd.quizService.service;

import com.spd.quizService.dto.QuestionDto;
import com.spd.quizService.exception.ResourceNotFoundException;
import com.spd.quizService.feign.QuizInterface;
import com.spd.quizService.model.Quiz;
import com.spd.quizService.model.Response;
import com.spd.quizService.repo.QuizRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuizService {

    @Autowired
    QuizRepo quizRepo;

    @Autowired
    QuizInterface quizInterface;

    @Transactional
    public ResponseEntity<Quiz> createQuiz(Integer id, String category, Integer numQ, String title) {

        List<Integer> questions = quizInterface.getQuestionsForQuiz(category, numQ).getBody();
        if (questions != null) {
            Quiz quiz = new Quiz();
            quiz.setId(id);
            quiz.setTitle(title);
            quiz.setQuestionIds(questions);
//        quizRepo.save((quiz));

            return new ResponseEntity<>(quizRepo.save(quiz), HttpStatus.CREATED);
        } else {
            throw new ResourceNotFoundException("there was an error while saving the quiz. Please create the quiz again with accurate details");
        }
    }

    public ResponseEntity<List<QuestionDto>> getQuizQuestions(Integer id) {
        if (quizRepo.existsById(id)) {
            Quiz quiz = quizRepo.findById(id).get();
            List<Integer> questionIds = quiz.getQuestionIds();

            ResponseEntity<List<QuestionDto>> questions = quizInterface.getQuestionsFromIds(questionIds);

            return questions;
        } else {
            throw new ResourceNotFoundException("No quiz exists by this id");
        }

    }

    public ResponseEntity<Integer> calculateResults(Integer id, List<Response> responses) {
        if (!responses.isEmpty()) {
            ResponseEntity<Integer> score = quizInterface.getScore(responses);
            return score;
        } else {
            throw new ResourceNotFoundException("List of responses is empty");
        }
    }

}

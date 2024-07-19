package com.spd.quizService.controller;

import com.spd.quizService.dto.QuestionDto;
import com.spd.quizService.dto.QuizDto;
import com.spd.quizService.model.Quiz;
import com.spd.quizService.model.Response;
import com.spd.quizService.service.QuizService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("quizapp/quiz")
public class QuizController {

    @Autowired
    QuizService quizService;

    private Logger logger = LoggerFactory.getLogger(QuizController.class);


    @PostMapping("createquiz")
    @CircuitBreaker(name = "quizQuestionBreaker", fallbackMethod = "quizQuestionFallback")
    public ResponseEntity<Quiz> createQuiz(@RequestBody QuizDto quizDto) {
//        return new ResponseEntity<>()
        return quizService.createQuiz(quizDto.getId(), quizDto.getCategory(), quizDto.getNumQ(), quizDto.getTitle());
    }

    //fallback method
    public ResponseEntity<Quiz> quizQuestionFallback(QuizDto quizDto, Exception ex) {
        logger.info("Fallback is executed because service is down :" + ex.getMessage());

        Quiz quiz = new Quiz();
        quiz.setId(123);
        quiz.setTitle("fake quiz because its a dummy object");
        quiz.setQuestionIds(null);

        return new ResponseEntity<>(quiz, HttpStatus.OK);

    }


    @GetMapping("getquiz/{id}")
    public ResponseEntity<List<QuestionDto>> getQuizQuestion(@PathVariable("id") Integer id) {
        return quizService.getQuizQuestions(id);
    }

    @PostMapping("submit/{id}")
    public ResponseEntity<Integer> submitQuiz(@Valid @PathVariable Integer id, @RequestBody List<Response> responses) {
        return quizService.calculateResults(id, responses);
    }

}
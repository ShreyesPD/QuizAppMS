package com.spd.questionService.controller;

import com.spd.questionService.dto.QuestionDto;
import com.spd.questionService.model.Question;
import com.spd.questionService.model.Response;
import com.spd.questionService.service.QuestionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("quizapp/question")
//@RestController
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @Autowired
    Environment environment;

    @GetMapping("getallquest")
    public ResponseEntity<List<Question>> getAllQuestions() {
        System.out.println("in the controller");
        return new ResponseEntity<>(questionService.getAllQuestions(), HttpStatus.OK);
    }

    @GetMapping("getquestbyid/{id}")
    public ResponseEntity<Question> getQuestionById(@PathVariable("id") Integer id) {

        return new ResponseEntity<>(questionService.getQuestionById(id), HttpStatus.OK);
    }

    @GetMapping("getquestbycategory/{category}")
    public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable("category") String category) {
        return new ResponseEntity<>(questionService.getQuestionsByCategory(category), HttpStatus.OK);
    }

    @PostMapping("createquest")
    public ResponseEntity<Question> createQuestion(@Valid @RequestBody Question question) {
        System.out.println("Question :" + question.getQuestionTitle());
        return new ResponseEntity<>(questionService.createQuestion(question), HttpStatus.CREATED);
    }

    @GetMapping("generate")
    public ResponseEntity<List<Integer>> getQuestionsForQuiz(@RequestParam String category, @RequestParam Integer numQ) {
        return new ResponseEntity<>(questionService.getQuestionsForQuiz(category, numQ), HttpStatus.OK);
    }

    @PostMapping("getquestions")
    public ResponseEntity<List<QuestionDto>> getQuestionsFromIds(@RequestBody List<Integer> questionIds) {
        System.out.println(environment.getProperty("local.server.port"));
        return new ResponseEntity<>(questionService.getQuestionsFromIds(questionIds), HttpStatus.OK);
    }

    @PostMapping("getscore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses) {
        return new ResponseEntity<>(questionService.getScore(responses), HttpStatus.OK);
    }

    //generate
    //getQuestion(questionIds)
    //getScore

}

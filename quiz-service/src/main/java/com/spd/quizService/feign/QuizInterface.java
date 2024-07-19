package com.spd.quizService.feign;

import com.spd.quizService.dto.QuestionDto;
import com.spd.quizService.model.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("QUESTION-SERVICE")
//@Service
public interface QuizInterface {
    @GetMapping("quizapp/question/generate")
    public ResponseEntity<List<Integer>> getQuestionsForQuiz(@RequestParam String category, @RequestParam Integer numQ);

    @PostMapping("quizapp/question/getquestions")
    public ResponseEntity<List<QuestionDto>> getQuestionsFromIds(@RequestBody List<Integer> questionIds);

    @PostMapping("quizapp/question/getscore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses);

}

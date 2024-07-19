package com.spd.questionService.service;

import com.spd.questionService.dto.QuestionDto;
import com.spd.questionService.exception.ResourceNotFoundException;
import com.spd.questionService.model.Question;
import com.spd.questionService.model.Response;
import com.spd.questionService.repo.QuestionRepo;
import org.aspectj.weaver.NewConstructorTypeMunger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {


    @Autowired
    private QuestionRepo questionRepo;

    public List<Question> getAllQuestions() {
        List<Question> questions = questionRepo.findAll();
        if (!(questions.isEmpty()))
            return questions;
        else
            throw new ResourceNotFoundException("questions are empty");
    }

    public Question getQuestionById(Integer id) {
        if (questionRepo.existsById(id)) {
            return questionRepo.findById(id).get();
        } else {
            throw new ResourceNotFoundException("this question does not exist");
        }
    }

    public List<Question> getQuestionsByCategory(String category) {
        List<Question> questions = questionRepo.findByCategory(category);
        if (!(questions.isEmpty()))
            return questions;
        else
            throw new ResourceNotFoundException("questions in this category are empty");
    }

    @Transactional
    public Question createQuestion(Question question) {
        return questionRepo.save(question);
    }

    public List<Integer> getQuestionsForQuiz(String category, Integer numQ) {
        List<Integer> questions = questionRepo.findRandomQuestionsByCategory(category, numQ);
        if (!(questions.isEmpty()))
            return questions;
        else
            throw new ResourceNotFoundException("questions in the quiz are empty");
    }

    public List<QuestionDto> getQuestionsFromIds(List<Integer> questionIds) {
        List<QuestionDto> questionDtos = new ArrayList<>();
        List<Question> questions = new ArrayList<>();

        if (!questionIds.isEmpty()) {

            for (Integer id : questionIds) {
                questions.add(questionRepo.findById(id).get());
            }
            if (!questions.isEmpty()) {
                for (Question question : questions) {
                    QuestionDto questionDto = new QuestionDto();
                    questionDto.setId(question.getId());
                    questionDto.setQuestionTitle(question.getQuestionTitle());
                    questionDto.setOption1(question.getOption1());
                    questionDto.setOption2(question.getOption2());
                    questionDto.setOption3(question.getOption3());
                    questionDto.setOption4(question.getOption4());
                    questionDtos.add(questionDto);
                }
                return questionDtos;
            } else {
                throw new ResourceNotFoundException(" Quiz questions are empty");
            }
        } else {
            throw new ResourceNotFoundException("Quiz question ids are empty ");
        }
    }

    public Integer getScore(List<Response> responses) {
        Integer correct = 0;

        if (!responses.isEmpty()) {
            for (Response response : responses) {
                Question question = questionRepo.findById(response.getId()).get();
                if (!question.equals(null)) {
                    if (response.getResponse().equals(question.getCorrectAnswer())) {
                        correct++;
                    }
                } else {
                    throw new ResourceNotFoundException("responseId does not match any question ");
                }
            }
            return correct;
        } else {
            throw new ResourceNotFoundException("responses submitted are empty");
        }
    }

}

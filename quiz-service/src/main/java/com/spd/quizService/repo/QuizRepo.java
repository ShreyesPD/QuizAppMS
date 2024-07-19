package com.spd.quizService.repo;


import com.spd.quizService.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepo extends JpaRepository<Quiz, Integer> {

}

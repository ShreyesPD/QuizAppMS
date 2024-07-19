package com.spd.quizService.model;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

@Entity
public class Quiz {
    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    //    @NotEmpty
//    @NotBlank
    private String title;


    @ElementCollection
//    @NotBlank
//    @NotEmpty
    private List<Integer> questionIds;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Integer> getQuestionIds() {
        return questionIds;
    }

    public void setQuestionIds(List<Integer> questionIds) {
        this.questionIds = questionIds;
    }

}

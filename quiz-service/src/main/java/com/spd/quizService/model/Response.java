package com.spd.quizService.model;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public class Response {

    @NotEmpty
    @NotBlank
    private Integer id;
    @NotEmpty
    @NotBlank
    private String response;

    public Response() {

    }

    public Response(Integer id, String response) {
        this.id = id;
        this.response = response;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}

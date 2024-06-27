package com.workintech.s17d2.model;

public class SeniorDeveloper extends Developer{
    public SeniorDeveloper(long id, String name, Double salary) {
        super((int) id, name, salary, Experience.SENIOR);
    }
}

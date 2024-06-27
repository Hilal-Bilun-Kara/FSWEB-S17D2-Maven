package com.workintech.s17d2.model;

public class JuniorDeveloper extends Developer{
    public JuniorDeveloper(long id, String name, Double salary) {
        super(Math.toIntExact(id), name, salary, Experience.JUNIOR);
    }
}

package com.workintech.s17d2.model;

public class MidDeveloper extends Developer{
    public MidDeveloper(long id, String name, Double salary) {
        super((int) id, name, salary, Experience.MID);
    }
}

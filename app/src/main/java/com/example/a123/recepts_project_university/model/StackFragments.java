package com.example.a123.recepts_project_university.model;

import java.util.ArrayList;
import java.util.List;

public class StackFragments {

    private static StackFragments instance = null;
    private static List<String> stack;

    private StackFragments(){
        stack = new ArrayList<>();
    }

    public static StackFragments getInstance(){
        if(instance == null){
            instance = new StackFragments();
        }
        return instance;
    }

    public String popBackStack(String fragment){
        String s = null;
        if(stack.contains(fragment)){
            s = stack.get(stack.indexOf(fragment));
            stack.remove(s);
        }

        return s;
    }

    public void addFragment(String fragment){
        stack.add(fragment);
    }
}

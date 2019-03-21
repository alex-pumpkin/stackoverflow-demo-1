package com.github.alexpumpkin.stackoverflowdemo1.model;

public class Resource {
    private String name;
    private String data;

    public Resource() {
    }

    public Resource(String name, String data) {
        this.name = name;
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}

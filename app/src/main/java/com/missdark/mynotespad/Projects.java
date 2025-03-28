package com.missdark.mynotespad;

import java.io.Serializable;

public class Projects implements Serializable {
    private long id;
    private String name;
    private long date;
    private String path;

    public Projects (long id, String name, long date, String path) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.path = path;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getDate() {
        return date;
    }


    public String getPath() {
        return path;
    }
}
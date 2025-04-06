package com.example.diffchecker.model;

public class ChangedFile {
    private String filename;

    public ChangedFile(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
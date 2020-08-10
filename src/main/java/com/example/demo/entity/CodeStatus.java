package com.example.demo.entity;

public enum CodeStatus {
    NOT_VERIFIED("NOT_VERIFIED"),
    VERIFIED("VERIFIED"),
    EXPIRED("EXPIRED");

    private String status;

    CodeStatus(String status) {
        this.status = status;
    }

    public String getCodeStatus() {
        return status;
    }
}

package com.example.rollcall;

public class AttendanceItem {
    private String date;
    private String presentPercentage;
    private String absentPercentage;

    public AttendanceItem(String date, String presentPercentage, String absentPercentage) {
        this.date = date;
        this.presentPercentage = presentPercentage;
        this.absentPercentage = absentPercentage;
    }

    public String getDate() {
        return date;
    }

    public String getPresentPercentage() {
        return presentPercentage;
    }

    public String getAbsentPercentage() {
        return absentPercentage;
    }
}


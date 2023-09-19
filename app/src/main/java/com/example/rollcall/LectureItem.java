package com.example.rollcall;

public class LectureItem {
    private String locationName;
    private String eventDate;

    public LectureItem(String locationName, String eventDate) {
        this.locationName = locationName;
        this.eventDate = eventDate;
    }

    public String getLocationName() {
        return locationName;
    }

    public String getEventDate() {
        return eventDate;
    }
}


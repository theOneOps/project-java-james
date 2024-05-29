package model.JobClasses;

import java.time.LocalDate;
import java.time.LocalTime;


public class WorkHourEntry {
    private String date;
    private String time;

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public WorkHourEntry(LocalDate date, LocalTime time) {
        this.date = date.toString();
        this.time = time.toString();
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    @Override
    public String toString() {
        return String.format("date -> %s | time -> %s \n", date, time);
    }
}
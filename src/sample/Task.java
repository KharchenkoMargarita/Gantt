package sample;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

public class Task {

    public static LocalDate startDateOfProject;
    public static LocalDate endDateOfProject;
    public static long durationInDays;
    public static String projectTitle;

    String name;
    LocalDate dateFrom;
    LocalDate dateTo;
    long diffInDays;
    String color;
    String performer;

    public Task(String name, LocalDate dateFrom, LocalDate dateTo, String color, String performer) {
        this.name = name;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.color = color;
        this.performer = performer;

        diffInDays = DAYS.between(dateFrom, dateTo);
    }

    public String getName() {
        return name;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public long getDiffInDays() {
        return diffInDays;
    }

    public String getColor() {
        return color;
    }

    public String getPerformer() {
        return performer;
    }
}

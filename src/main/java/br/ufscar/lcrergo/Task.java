package br.ufscar.lcrergo;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Task {
    private String name, description, place;
    private DateTimeFormatter date;
    private Category category;

    public Task(String name, String description,
            String place, DateTimeFormatter date,
            Category category) {
        this.name = name;
        this.description = description;
        this.place = place;
        this.date = date;
        this.category = category;

    }

    public Task(String name, String description,
            String place, String date,
            Category category)
            throws DateTimeParseException, NullPointerException {
        this.name = name;
        this.description = description;
        this.place = place;
        this.date = DateTimeFormatter.ofPattern(date);
        this.category = category;
    }

    public String toString() {
        return String.format("{%n name: %s%n description: %s%n place: %s%n date: " + 
                date.toString().replaceAll("'", "") +
                "%n category: %s%n}",
                name,
                description,
                place,
                category);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPlace() {
        return place;
    }

    public DateTimeFormatter getDate() {
        return date;
    }

    public Category getCategory() {
        return category;
    }
}

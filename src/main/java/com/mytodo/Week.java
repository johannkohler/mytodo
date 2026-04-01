package com.mytodo;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "week",
       uniqueConstraints = @UniqueConstraint(columnNames = {"year", "week_number"}))
public class Week {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "year", nullable = false)
    private int year;

    @Column(name = "week_number", nullable = false)
    private int weekNumber;

    @OneToMany(mappedBy = "week", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("dayDate ASC")
    private List<Day> days = new ArrayList<>();

    public Long getId() { return id; }
    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }
    public int getWeekNumber() { return weekNumber; }
    public void setWeekNumber(int weekNumber) { this.weekNumber = weekNumber; }
    public List<Day> getDays() { return days; }
}

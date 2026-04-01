package com.mytodo;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "day")
public class Day {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "week_id", nullable = false)
    private Week week;

    @Column(name = "day_date", nullable = false)
    private LocalDate dayDate;

    @OneToMany(mappedBy = "day", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @OrderBy("position ASC")
    private List<Todo> todos = new ArrayList<>();

    public Long getId() { return id; }
    public Week getWeek() { return week; }
    public void setWeek(Week week) { this.week = week; }
    public LocalDate getDayDate() { return dayDate; }
    public void setDayDate(LocalDate dayDate) { this.dayDate = dayDate; }
    public List<Todo> getTodos() { return todos; }
}

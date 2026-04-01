package com.mytodo;

import jakarta.persistence.*;

@Entity
@Table(name = "todo")
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "day_id", nullable = false)
    private Day day;

    @Column(nullable = false, length = 500)
    private String text;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TodoType type;

    @Column(nullable = false)
    private boolean important;

    @Column(nullable = false)
    private int position;

    public Long getId() { return id; }
    public Day getDay() { return day; }
    public void setDay(Day day) { this.day = day; }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public TodoType getType() { return type; }
    public void setType(TodoType type) { this.type = type; }
    public boolean isImportant() { return important; }
    public void setImportant(boolean important) { this.important = important; }
    public int getPosition() { return position; }
    public void setPosition(int position) { this.position = position; }
}

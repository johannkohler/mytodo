package com.mytodo;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class TodoService {

    private final TodoRepository todoRepository;
    private final DayRepository dayRepository;

    public TodoService(TodoRepository todoRepository, DayRepository dayRepository) {
        this.todoRepository = todoRepository;
        this.dayRepository = dayRepository;
    }

    public Todo addTodo(Long dayId, String text, TodoType type, boolean important) {
        Day day = dayRepository.findById(dayId)
                .orElseThrow(() -> new IllegalArgumentException("Day not found: " + dayId));
        int nextPosition = day.getTodos().size();
        Todo todo = new Todo();
        todo.setDay(day);
        todo.setText(text);
        todo.setType(type);
        todo.setImportant(important);
        todo.setPosition(nextPosition);
        return todoRepository.save(todo);
    }

    public void reorder(Long dayId, List<Long> orderedIds) {
        List<Todo> todos = todoRepository.findByDayIdOrderByPositionAsc(dayId);
        Map<Long, Todo> byId = todos.stream().collect(Collectors.toMap(Todo::getId, t -> t));
        for (int i = 0; i < orderedIds.size(); i++) {
            Todo t = byId.get(orderedIds.get(i));
            if (t != null) {
                t.setPosition(i);
                todoRepository.save(t);
            }
        }
    }
}

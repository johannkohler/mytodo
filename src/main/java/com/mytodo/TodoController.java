package com.mytodo;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/todo")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping("/add")
    public String addTodo(
            @RequestParam Long dayId,
            @RequestParam String text,
            @RequestParam TodoType type,
            @RequestParam(defaultValue = "false") boolean important,
            @RequestParam int year,
            @RequestParam int weekNumber) {
        todoService.addTodo(dayId, text, type, important);
        return "redirect:/week/" + year + "/" + weekNumber;
    }

    @PostMapping("/reorder")
    @ResponseBody
    public ResponseEntity<Void> reorder(@RequestBody ReorderRequest request) {
        todoService.reorder(request.getDayId(), request.getOrderedIds());
        return ResponseEntity.ok().build();
    }
}

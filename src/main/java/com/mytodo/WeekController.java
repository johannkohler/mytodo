package com.mytodo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.time.temporal.IsoFields;

@Controller
public class WeekController {

    private final WeekService weekService;

    public WeekController(WeekService weekService) {
        this.weekService = weekService;
    }

    @GetMapping("/")
    public String redirectToCurrent() {
        return "redirect:/week/" + weekService.getCurrentYear() + "/" + weekService.getCurrentWeekNumber();
    }

    @GetMapping("/week/{year}/{weekNumber}")
    public String weekView(@PathVariable int year, @PathVariable int weekNumber, Model model) {
        Week week = weekService.getOrCreateWeek(year, weekNumber);

        var monday = week.getDays().get(0).getDayDate();
        var prevMonday = monday.minusWeeks(1);
        var nextMonday = monday.plusWeeks(1);

        model.addAttribute("week", week);
        model.addAttribute("prevYear", prevMonday.get(IsoFields.WEEK_BASED_YEAR));
        model.addAttribute("prevWeek", prevMonday.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR));
        model.addAttribute("nextYear", nextMonday.get(IsoFields.WEEK_BASED_YEAR));
        model.addAttribute("nextWeek", nextMonday.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR));
        model.addAttribute("todoTypes", TodoType.values());
        model.addAttribute("currentYear", weekService.getCurrentYear());
        model.addAttribute("currentWeek", weekService.getCurrentWeekNumber());
        model.addAttribute("today", LocalDate.now());

        return "week";
    }
}

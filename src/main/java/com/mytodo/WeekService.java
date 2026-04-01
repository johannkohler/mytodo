package com.mytodo;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.IsoFields;
import java.time.temporal.TemporalAdjusters;

@Service
@Transactional
public class WeekService {

    private final WeekRepository weekRepository;
    private final DayRepository dayRepository;

    public WeekService(WeekRepository weekRepository, DayRepository dayRepository) {
        this.weekRepository = weekRepository;
        this.dayRepository = dayRepository;
    }

    public Week getOrCreateWeek(int year, int weekNumber) {
        return weekRepository.findByYearAndWeekNumber(year, weekNumber)
                .orElseGet(() -> createWeek(year, weekNumber));
    }

    private Week createWeek(int year, int weekNumber) {
        Week week = new Week();
        week.setYear(year);
        week.setWeekNumber(weekNumber);
        week = weekRepository.save(week);

        LocalDate monday = LocalDate.now()
                .with(IsoFields.WEEK_BASED_YEAR, year)
                .with(IsoFields.WEEK_OF_WEEK_BASED_YEAR, weekNumber)
                .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

        for (int i = 0; i < 5; i++) {
            Day day = new Day();
            day.setWeek(week);
            day.setDayDate(monday.plusDays(i));
            day = dayRepository.save(day);
            week.getDays().add(day);
        }

        return week;
    }

    public int getCurrentYear() {
        return LocalDate.now().get(IsoFields.WEEK_BASED_YEAR);
    }

    public int getCurrentWeekNumber() {
        return LocalDate.now().get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
    }
}

package com.financetech.capitalone.hackathon2019;

import com.financetech.capitalone.hackathon2019.exception.NotEnoughDays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.SUNDAY;

@Service
public class HolidayService {

    @Autowired
    private HolidayApiClient api;

    public List<LocalDate> find(String country, int year, int month, int start, int end, boolean includeWeekend, boolean includeHoliday) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.plusMonths(1);

        List<LocalDate> ans = new ArrayList<>();
        final int lastDayOfMonth = endDate.minusDays(1).getDayOfMonth();
        final int targetSize = end - start + 1;
        for (int i = 1; i <= lastDayOfMonth && ans.size() < targetSize; i++) {
            LocalDate date = LocalDate.of(year, month, i);
            if (!shouldInclude(date, country, includeWeekend, includeHoliday)) continue;
            ans.add(date);
        }

        if (ans.size() < targetSize) {
            throw new NotEnoughDays("only " + ans.size() + " days meet the requirement");
        }

        return ans.subList(start - 1, end);
    }


    private boolean shouldInclude(LocalDate date, String country, boolean includeWeekend, boolean includeHoliday) {
        if (!includeWeekend && isWeekend(date)) {
            return false;
        }
        if (!includeHoliday && isHoliday(date, country)) {
            return false;
        }

        return true;
    }

    private boolean isHoliday(LocalDate date, String country) {
        return api.holidays(date.getYear(), date.getMonthValue(), country).contains(date);
    }

    private boolean isWeekend(LocalDate date) {
        final DayOfWeek day = date.getDayOfWeek();
        return day.equals(SATURDAY) || day.equals(SUNDAY);
    }


}

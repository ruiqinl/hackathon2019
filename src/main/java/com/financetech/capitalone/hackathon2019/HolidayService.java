package com.financetech.capitalone.hackathon2019;

import com.financetech.capitalone.hackathon2019.exception.NotEnoughDaysException;
import com.financetech.capitalone.hackathon2019.exception.NotFoundException;
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

    @Autowired
    private BDDataSourceRepository repository;

    public List<LocalDate> findById(Long id) {
        BDDataSource entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("bddatasource with id " + id + " is not found"));

        if (entity.getMonth() != null) {
            return findWithinMonth(
                    entity.getCountryCode(),
                    entity.getYear().intValue(),
                    entity.getMonth().intValue(),
                    entity.getBusinessDayStart().intValue(),
                    entity.getBusinessDayEnd().intValue(),
                    entity.isWeekendIncluded(),
                    entity.isHolidayIncluded());
        } else {
            return findWithinYear(
                    entity.getCountryCode(),
                    entity.getYear().intValue(),
                    entity.getBusinessDayStart().intValue(),
                    entity.getBusinessDayEnd().intValue(),
                    entity.isWeekendIncluded(),
                    entity.isHolidayIncluded());
        }

    }

    public List<LocalDate> findWithinMonth(String country, int year, int month, int start,
                                           int end, boolean includeWeekend, boolean includeHoliday) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        final int totalDays = startDate.plusMonths(1).minusDays(1).getDayOfMonth();
        return find(country, start, end, includeWeekend, includeHoliday, startDate, totalDays);
    }

    public List<LocalDate> findWithinYear(String country, int year, int start,
                                           int end, boolean includeWeekend, boolean includeHoliday) {
        LocalDate startDate = LocalDate.of(year, 1, 1);
        int totalDays = startDate.plusYears(1).minusDays(1).getDayOfYear();
        return find(country, start, end, includeWeekend, includeHoliday, startDate, totalDays);
    }

    private List<LocalDate> find(String country, int start, int end, boolean includeWeekend, boolean includeHoliday, LocalDate startDate, int totalDays) {
        final int targetSize = end - start + 1;

        List<LocalDate> ans = new ArrayList<>();
        for (int i = 0; i < totalDays && ans.size() < targetSize; i++) {
            LocalDate date = startDate.plusDays(i);
            if (!shouldInclude(date, country, includeWeekend, includeHoliday)) continue;
            ans.add(date);
        }

        if (ans.size() < targetSize) {
            throw new NotEnoughDaysException("only " + ans.size() + " days meet the requirement");
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

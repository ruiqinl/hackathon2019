package com.financetech.capitalone.hackathon2019;

import com.financetech.capitalone.hackathon2019.exception.NotEnoughDays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.ServletRequest;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/holidays")
public class HolidayRestController {
    private static final Logger log = LoggerFactory.getLogger(HolidayRestController.class);

    @Autowired
    HolidayService service;

    @GetMapping(path = "/{country}/{year}/{month}/{start}/{end}")
    public List<LocalDate> get(@PathVariable("country") String country,
                               @PathVariable("year") int year,
                               @PathVariable("month") int month,
                               @PathVariable("start") int start,
                               @PathVariable("end") int end,
                               @RequestParam(value = "includeWeekend", defaultValue = "false") boolean includeWeekend,
                               @RequestParam(value = "includeHoliday", defaultValue = "false") boolean includeHoliday,
                               ServletRequest request) {

        log.info("received GET request, country:{}, year:{}, month:{}, [{},{}]", country, year, month, start, end);

        try {
            return service.find(country, year, month, start, end, includeWeekend, includeHoliday);
        } catch (NotEnoughDays e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

    }

}

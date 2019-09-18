package com.financetech.capitalone.hackathon2019;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HolidayServiceTest {

    @Autowired
    HolidayService service;

    @Test
    public void test() {

        List<LocalDate> dates = service.findWithinMonth("us", 2019, 9, 1, 20, false, false);

        System.out.println(dates);

    }


}
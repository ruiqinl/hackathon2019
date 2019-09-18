package com.financetech.capitalone.hackathon2019;

import com.jayway.jsonpath.JsonPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class HolidayApiClient {
    private static final Logger log = LoggerFactory.getLogger(HolidayApiClient.class);

    @Autowired
    private RestTemplate restTemplate;

    private static final String PATH = "https://calendarific.com/api/v2/holidays?api_key=8aab395db1ec5b2dfe2f52b932338084d1a3d7b8";


    @Cacheable("holiday")
    public Set<LocalDate> holidays(int year, int month, String country) {
        log.info("send request to holidays API, year:{}, month:{}, country:{}", year, month, country);

        URI uri = UriComponentsBuilder.fromUriString(PATH)
                .queryParam("country", country)
                .queryParam("year", year)
                .queryParam("month", month)
                .queryParam("type", "national")
                .build()
                .toUri();
        ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
        List<String> dates = JsonPath.read(response.getBody(), "$.response.holidays[*].date.iso");

        log.info("holiday API response: {}", dates);
        return dates.stream().map(LocalDate::parse).collect(Collectors.toSet());
    }

}

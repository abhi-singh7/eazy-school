package com.eazyBytes.eazySchool.controller;


import com.eazyBytes.eazySchool.model.Holiday;
import com.eazyBytes.eazySchool.repository.HolidaysRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Controller
public class HolidaysController {

    private final HolidaysRepository holidaysRepository;

    public HolidaysController(HolidaysRepository holidaysRepository) {
        this.holidaysRepository = holidaysRepository;
    }


    @GetMapping("/holidays")
    public String displayHolidays(@RequestParam (required = false) boolean festival,
                                  @RequestParam (required = false) boolean federal,
            Model model) {
        model.addAttribute("festival", festival);
        model.addAttribute("federal",federal);

        Iterable<Holiday> holidays = holidaysRepository.findAll();
        List<Holiday> holidayList = StreamSupport.stream(holidays.spliterator(),false)
                .toList();

        Holiday.Type[] types = Holiday.Type.values();
        for (Holiday.Type type : types) {
            model.addAttribute(type.toString(),
                    (holidayList.stream().filter(holiday -> holiday.getType().equals(type)).collect(Collectors.toList())));
        }
        return "holidays.html";
    }
}


package com.example.userservice.utils;

import com.example.userservice.exception.DateTimeFormatException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTimeConverter {

    public static LocalDate convertToDate(String date) {
        try {
            return null != date ? LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy")) : null;
        } catch (DateTimeParseException ex) {
            throw new DateTimeFormatException("Invalid date format. Please enter in dd-mm-yyyy format", ex);
        }
    }
}

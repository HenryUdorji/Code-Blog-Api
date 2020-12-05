package com.codemountain.codeblog.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FormatTime {

    public static String formatTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return dateTime.format(formatter);
    }
}

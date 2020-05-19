package com.example.demo.util;

import org.slf4j.helpers.MessageFormatter;

public class StringFormat {
    public static String format(String format, Object... params) {
        return MessageFormatter.arrayFormat(format, params).getMessage();
    }

}

package com.itk.kdp.entity;

import com.haulmont.chile.core.annotations.JavaClass;
import com.haulmont.chile.core.datatypes.Datatype;

import javax.annotation.Nullable;
import java.text.ParseException;
import java.util.Locale;

@JavaClass(String.class)
public class PositionPresentation implements Datatype<String> {

    public String format(@Nullable Object value) {
        if (value == null)
            return "";

        return String.format("Должность:%s", value);

    }


    public String format(@Nullable Object value, Locale locale) {
        return format(value);
    }

    @Nullable
    public String parse(@Nullable String value) throws ParseException {

        if (value.isEmpty() || value.equals("q")) {
            throw new ParseException("Invalid position", 0);
        }

        return value.replace("Должность:", "") + "qqq";
    }

    @Nullable
    public String parse(@Nullable String value, Locale locale) throws ParseException {
        return parse(value);
    }

}

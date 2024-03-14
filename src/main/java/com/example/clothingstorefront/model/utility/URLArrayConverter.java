package com.example.clothingstorefront.model.utility;

import jakarta.persistence.AttributeConverter;

import java.util.Arrays;
import java.util.List;

/**
 * We can encode a list of string URLs by combining them using a forbidden character
 * ("<" in this case). This only does something when used for the database storage.
 */
public class URLArrayConverter implements AttributeConverter<List<String>, String> {
    // "<" is disallowed in any URL context, so it makes sense to use it as a delim
    protected static final String DELIM_CHAR = "<";

    @Override
    public String convertToDatabaseColumn(List<String> inputURLs) {
        StringBuilder strBuff = new StringBuilder();
        inputURLs.forEach(curURL -> {
            strBuff.append(curURL);
            strBuff.append(DELIM_CHAR);
        });
        if(!strBuff.isEmpty())
            strBuff.deleteCharAt(strBuff.length()-1);
        return strBuff.toString();
    }

    @Override
    public List<String> convertToEntityAttribute(String urlArrString) {
        if(urlArrString.isEmpty())
            return List.of();

        return Arrays.asList(urlArrString.split(DELIM_CHAR));
    }
}

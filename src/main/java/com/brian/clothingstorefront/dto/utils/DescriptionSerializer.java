package com.brian.clothingstorefront.dto.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * This is used to shorten the strings passed in to the message and remove the formatting so that it can be visualized
 * in the message overview.
 * This will never be deserialized; so, we can leave it as is.
 */
public class DescriptionSerializer extends JsonSerializer<String> {
    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider ser) throws IOException {
        if(value.length() > 10){
            value = value.replace("\n", " ").replace("\t", " ").substring(0, 10) + "...";
        }
        gen.writeString(value);
    }
}

package com.example.clothingstorefront.model.utility;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

public class URLArrayConverterTest {
    private static URLArrayConverter con;

    // We only need one converter for all tests as it is stateless
    @BeforeAll
    public static void initConverter() {
        con = new URLArrayConverter();
    }

    /**
     * Coverage for URLArrayConverter:
     * => Size(Input) : 0, >0
     * => Output: Concat-String
     *
     * Base assumption: Inputs are valid URLs.
     */
    @Test
    public void testConversionEmpty() {
        List<String> urlList = List.of();

        String oString = con.convertToDatabaseColumn(urlList);
        List<String> retList = con.convertToEntityAttribute(oString);
        Assertions.assertTrue(oString.isEmpty(), "Empty list should generate empty string.");
        Assertions.assertTrue(retList.containsAll(urlList) && (retList.size() == urlList.size()), "List should not be transformed by converter.");
    }

    @Test
    public void testConversionNonEmpty() {
        String url1 = "https://www.google.com";
        String url2 = "s3://example-bucket/path/to/object";
        List<String> urlList = List.of(url1, url2);

        String oString = con.convertToDatabaseColumn(urlList);
        List<String> retList = con.convertToEntityAttribute(oString);
        Assertions.assertEquals(oString, url1 + URLArrayConverter.DELIM_CHAR + url2, "Improper output received.");
        Assertions.assertTrue(retList.containsAll(urlList) && (retList.size() == urlList.size()), "List should not be transformed by converter.");
    }
}

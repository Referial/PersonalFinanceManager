package org.example;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;


import java.util.HashMap;
import java.util.Map;

public class AuxiliaryСlassesTest extends TestCase {

    AuxiliaryСlasses auxiliaryСlasses;

    @Test
    public void testMaximumValueCategory() {

        auxiliaryСlasses = new AuxiliaryСlasses();

        Map<String, Integer> sranvenie = new HashMap<>();

        sranvenie.put("другое", 200);
        sranvenie.put("еда", 2500);
        sranvenie.put("финансы", 400);
        sranvenie.put("одежда", 1200);
        sranvenie.put("машина", 2421);
        sranvenie.put("дети", 4800);
        sranvenie.put("комуналка", 6324);

        String expect = "комуналка";

        String result = auxiliaryСlasses.maximumValueCategory(sranvenie);

        Assertions.assertEquals(expect, result);
    }
}
package com.yoclabo.sql.scan;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PatternPickerTest {

    @Test
    public void test01() {
        try {
            Method cartesian = PatternPicker.class.getDeclaredMethod("cartesian", List.class, List.class, boolean.class);
            cartesian.setAccessible(true);

            List<List<String>> arg1;
            List<String> arg2;
            boolean arg3;
            List<List<String>> ret;

            // Pattern 1.
            // { A, B } * { 1, 2 }
            //   =
            //     { A, B, 1 }
            //     { A, B, 2 }
            arg1 = new ArrayList<>();
            arg1.add(new ArrayList<>(Arrays.asList("A", "B")));
            arg2 = new ArrayList<>(Arrays.asList("1", "2"));
            arg3 = false;

            ret = (List<List<String>>) cartesian.invoke(new PatternPicker(), arg1, arg2, arg3);
            Assert.assertEquals(2, ret.size());
            Assert.assertEquals(3, ret.get(0).size());
            Assert.assertEquals(3, ret.get(1).size());
            Assert.assertEquals("A", ret.get(0).get(0));
            Assert.assertEquals("B", ret.get(0).get(1));
            Assert.assertEquals("1", ret.get(0).get(2));
            Assert.assertEquals("A", ret.get(1).get(0));
            Assert.assertEquals("B", ret.get(1).get(1));
            Assert.assertEquals("2", ret.get(1).get(2));

            // Pattern 2.
            // { A, B }
            // { C, D } * { 1, 2 }
            //   =
            //     { A, B, 1 }
            //     { A, B, 2 }
            //     { C, D, 1 }
            //     { C, D, 2 }
            arg1 = new ArrayList<>();
            arg1.add(new ArrayList<>(Arrays.asList("A", "B")));
            arg1.add(new ArrayList<>(Arrays.asList("C", "D")));
            arg2 = new ArrayList<>(Arrays.asList("1", "2"));
            arg3 = false;

            ret = (List<List<String>>) cartesian.invoke(new PatternPicker(), arg1, arg2, arg3);
            Assert.assertEquals(4, ret.size());
            Assert.assertEquals(3, ret.get(0).size());
            Assert.assertEquals(3, ret.get(1).size());
            Assert.assertEquals(3, ret.get(2).size());
            Assert.assertEquals(3, ret.get(3).size());
            Assert.assertEquals("A", ret.get(0).get(0));
            Assert.assertEquals("B", ret.get(0).get(1));
            Assert.assertEquals("1", ret.get(0).get(2));
            Assert.assertEquals("A", ret.get(1).get(0));
            Assert.assertEquals("B", ret.get(1).get(1));
            Assert.assertEquals("2", ret.get(1).get(2));
            Assert.assertEquals("C", ret.get(2).get(0));
            Assert.assertEquals("D", ret.get(2).get(1));
            Assert.assertEquals("1", ret.get(2).get(2));
            Assert.assertEquals("C", ret.get(3).get(0));
            Assert.assertEquals("D", ret.get(3).get(1));
            Assert.assertEquals("2", ret.get(3).get(2));

            // Pattern 3.
            // { } * { 1, 2 }
            //   =
            //     { 1 }
            //     { 2 }
            arg1 = new ArrayList<>();
            arg1.add(new ArrayList<>());
            arg2 = new ArrayList<>(Arrays.asList("1", "2"));
            arg3 = false;

            ret = (List<List<String>>) cartesian.invoke(new PatternPicker(), arg1, arg2, arg3);
            Assert.assertEquals(2, ret.size());
            Assert.assertEquals(1, ret.get(0).size());
            Assert.assertEquals(1, ret.get(1).size());
            Assert.assertEquals("1", ret.get(0).get(0));
            Assert.assertEquals("2", ret.get(1).get(0));

            // Pattern 4.
            // { A, B } * { }
            //   =
            //     { A, B }
            arg1 = new ArrayList<>();
            arg1.add(new ArrayList<>(Arrays.asList("A", "B")));
            arg2 = new ArrayList<>();
            arg3 = false;

            ret = (List<List<String>>) cartesian.invoke(new PatternPicker(), arg1, arg2, arg3);
            Assert.assertEquals(1, ret.size());
            Assert.assertEquals(2, ret.get(0).size());
            Assert.assertEquals("A", ret.get(0).get(0));
            Assert.assertEquals("B", ret.get(0).get(1));

            // Pattern 5-1. ( shift )
            // { } * { }
            //   =
            //     { "" }
            arg1 = new ArrayList<>();
            arg1.add(new ArrayList<>());
            arg2 = new ArrayList<>();
            arg3 = true;

            ret = (List<List<String>>) cartesian.invoke(new PatternPicker(), arg1, arg2, arg3);
            Assert.assertEquals(1, ret.size());
            Assert.assertEquals(1, ret.get(0).size());
            Assert.assertEquals("", ret.get(0).get(0));

            // Pattern 5-2.
            // { "" } * { A, B }
            //   =
            //     { "", A }
            //     { "", B }
            arg1 = ret;
            arg2 = new ArrayList<>(Arrays.asList("A", "B"));
            arg3 = false;

            ret = (List<List<String>>) cartesian.invoke(new PatternPicker(), arg1, arg2, arg3);
            Assert.assertEquals(2, ret.size());
            Assert.assertEquals(2, ret.get(0).size());
            Assert.assertEquals(2, ret.get(1).size());
            Assert.assertEquals("", ret.get(0).get(0));
            Assert.assertEquals("A", ret.get(0).get(1));
            Assert.assertEquals("", ret.get(1).get(0));
            Assert.assertEquals("B", ret.get(1).get(1));

            // Pattern 5-3.
            // { "", A }
            // { "", B } * { 1, 2 }
            //   =
            //     { "", A, 1 }
            //     { "", A, 2 }
            //     { "", B, 1 }
            //     { "", B, 2 }
            arg1 = ret;
            arg2 = new ArrayList<>(Arrays.asList("1", "2"));
            arg3 = false;

            ret = (List<List<String>>) cartesian.invoke(new PatternPicker(), arg1, arg2, arg3);
            Assert.assertEquals(4, ret.size());
            Assert.assertEquals(3, ret.get(0).size());
            Assert.assertEquals(3, ret.get(1).size());
            Assert.assertEquals(3, ret.get(2).size());
            Assert.assertEquals(3, ret.get(3).size());
            Assert.assertEquals("", ret.get(0).get(0));
            Assert.assertEquals("A", ret.get(0).get(1));
            Assert.assertEquals("1", ret.get(0).get(2));
            Assert.assertEquals("", ret.get(1).get(0));
            Assert.assertEquals("A", ret.get(1).get(1));
            Assert.assertEquals("2", ret.get(1).get(2));
            Assert.assertEquals("", ret.get(2).get(0));
            Assert.assertEquals("B", ret.get(2).get(1));
            Assert.assertEquals("1", ret.get(2).get(2));
            Assert.assertEquals("", ret.get(3).get(0));
            Assert.assertEquals("B", ret.get(3).get(1));
            Assert.assertEquals("2", ret.get(3).get(2));

            // Pattern 5-4.
            // { "", A, 1 }
            // { "", A, 2 }
            // { "", B, 1 }
            // { "", B, 2 } * { "" }
            //   =
            //     { "", A, 1, "" }
            //     { "", A, 2, "" }
            //     { "", B, 1, "" }
            //     { "", B, 2, "" }
            arg1 = ret;
            arg2 = new ArrayList<>(Arrays.asList(""));
            arg3 = false;

            ret = (List<List<String>>) cartesian.invoke(new PatternPicker(), arg1, arg2, arg3);
            Assert.assertEquals(4, ret.size());
            Assert.assertEquals(4, ret.get(0).size());
            Assert.assertEquals(4, ret.get(1).size());
            Assert.assertEquals(4, ret.get(2).size());
            Assert.assertEquals(4, ret.get(3).size());
            Assert.assertEquals("", ret.get(0).get(0));
            Assert.assertEquals("A", ret.get(0).get(1));
            Assert.assertEquals("1", ret.get(0).get(2));
            Assert.assertEquals("", ret.get(0).get(3));
            Assert.assertEquals("", ret.get(1).get(0));
            Assert.assertEquals("A", ret.get(1).get(1));
            Assert.assertEquals("2", ret.get(1).get(2));
            Assert.assertEquals("", ret.get(1).get(3));
            Assert.assertEquals("", ret.get(2).get(0));
            Assert.assertEquals("B", ret.get(2).get(1));
            Assert.assertEquals("1", ret.get(2).get(2));
            Assert.assertEquals("", ret.get(2).get(3));
            Assert.assertEquals("", ret.get(3).get(0));
            Assert.assertEquals("B", ret.get(3).get(1));
            Assert.assertEquals("2", ret.get(3).get(2));
            Assert.assertEquals("", ret.get(3).get(3));

            // Pattern 6-1. ( shift )
            // { } * { 1, 2 }
            //   =
            //     { "", 1 }
            //     { "", 2 }
            arg1 = new ArrayList<>();
            arg1.add(new ArrayList<>());
            arg2 = new ArrayList<>(Arrays.asList("1", "2"));
            arg3 = true;

            ret = (List<List<String>>) cartesian.invoke(new PatternPicker(), arg1, arg2, arg3);
            Assert.assertEquals(2, ret.size());
            Assert.assertEquals(2, ret.get(0).size());
            Assert.assertEquals(2, ret.get(1).size());
            Assert.assertEquals("", ret.get(0).get(0));
            Assert.assertEquals("1", ret.get(0).get(1));
            Assert.assertEquals("", ret.get(1).get(0));
            Assert.assertEquals("2", ret.get(1).get(1));

            // Pattern 6-2.
            // { } * { 1, 2 }
            //   =
            //     { 1 }
            //     { 2 }
            arg1 = new ArrayList<>();
            arg1.add(new ArrayList<>());
            arg2 = new ArrayList<>(Arrays.asList("1", "2"));
            arg3 = false;

            ret = (List<List<String>>) cartesian.invoke(new PatternPicker(), arg1, arg2, arg3);
            Assert.assertEquals(2, ret.size());
            Assert.assertEquals(1, ret.get(0).size());
            Assert.assertEquals(1, ret.get(1).size());
            Assert.assertEquals("1", ret.get(0).get(0));
            Assert.assertEquals("2", ret.get(1).get(0));

            // Pattern 7.
            // { } * { }
            //   =
            //     { }
            arg1 = new ArrayList<>();
            arg1.add(new ArrayList<>());
            arg2 = new ArrayList<>();
            arg3 = false;

            ret = (List<List<String>>) cartesian.invoke(new PatternPicker(), arg1, arg2, arg3);
            Assert.assertEquals(1, ret.size());
            Assert.assertEquals(0, ret.get(0).size());
        }
        catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void test02() {
        try {
            String enc = "UTF-8";
            String path = PatternPickerTest.class.getClassLoader().getResource("PatternPickerTest/test01.txt").getPath();
            PatternPicker p = new PatternPicker();
            p.read(path);

            Assert.assertEquals(3, p.getTriggers().size());

            final String L1T1 = "CREATE";
            final String L1T2 = "(OR REPLACE)*";
            final String L1T3 = "TRIGGER";
            final String L1T4 = "[:TRIGGER:]";
            final String L1T5 = "(BEFORE|AFTER)";
            final String L1T6 = "(UPDATE|INSERT|DELETE)";
            final String L1T7 = "ON";
            final String L1T8 = "[N](SELECT|UPDATE|INSERT|DELETE)";
            final String L1T9 = "[:TABLE:]";
            final String L2T1 = "(UPDATE|INSERT|DELETE)";
            final String L2T2 = "[N]ON";
            final String L2T3 = "[:TABLE:]";
            final String L3T1 = "(UPDATE|INSERT|DELETE)";
            final String L3T2 = "[N]ON";
            final String L3T3 = "[:TABLE:]";
            final String L3T4 = "IMMEDIATE";

            Assert.assertEquals(L1T1, p.getTriggers().get(0).get(0));
            Assert.assertEquals(L1T2, p.getTriggers().get(0).get(1));
            Assert.assertEquals(L1T3, p.getTriggers().get(0).get(2));
            Assert.assertEquals(L1T4, p.getTriggers().get(0).get(3));
            Assert.assertEquals(L1T5, p.getTriggers().get(0).get(4));
            Assert.assertEquals(L1T6, p.getTriggers().get(0).get(5));
            Assert.assertEquals(L1T7, p.getTriggers().get(0).get(6));
            Assert.assertEquals(L1T8, p.getTriggers().get(0).get(7));
            Assert.assertEquals(L1T9, p.getTriggers().get(0).get(8));
            Assert.assertEquals(L2T1, p.getTriggers().get(1).get(0));
            Assert.assertEquals(L2T2, p.getTriggers().get(1).get(1));
            Assert.assertEquals(L2T3, p.getTriggers().get(1).get(2));
            Assert.assertEquals(L3T1, p.getTriggers().get(2).get(0));
            Assert.assertEquals(L3T2, p.getTriggers().get(2).get(1));
            Assert.assertEquals(L3T3, p.getTriggers().get(2).get(2));
            Assert.assertEquals(L3T4, p.getTriggers().get(2).get(3));
        }
        catch (Exception e) {
            Assert.fail();
        }
    }
}

package com.yoclabo.sql.scan;

import org.junit.Assert;
import org.junit.Test;

public class NamePickerTest {

    @Test
    public void test01() {
        try {
            String enc = "UTF-8";
            String path = NamePickerTest.class.getClassLoader().getResource("NamePickerTest/test01.txt").getPath();
            NamePicker np = new NamePicker();
            np.read(path);

            Assert.assertEquals(8, np.getTables().size());

            final String T1 = "Account";
            final String T2 = "Bugs";
            final String T3 = "BugsProducts";
            final String T4 = "BugStatus";
            final String T5 = "Comments";
            final String T6 = "Products";
            final String T7 = "Screenshots";
            final String T8 = "Tags";
            final String TR1 = "Tr_AfterIns_Accounts";
            final String TR2 = "Tr_BeforeIns_Bugs";

            Assert.assertEquals(T1, np.getTables().get(0));
            Assert.assertEquals(T2, np.getTables().get(1));
            Assert.assertEquals(T3, np.getTables().get(2));
            Assert.assertEquals(T4, np.getTables().get(3));
            Assert.assertEquals(T5, np.getTables().get(4));
            Assert.assertEquals(T6, np.getTables().get(5));
            Assert.assertEquals(T7, np.getTables().get(6));
            Assert.assertEquals(T8, np.getTables().get(7));
            Assert.assertEquals(2, np.getTriggers().size());
            Assert.assertEquals(TR1, np.getTriggers().get(0));
            Assert.assertEquals(TR2, np.getTriggers().get(1));
        }
        catch (Exception e) {
            Assert.fail();
        }
    }
}
